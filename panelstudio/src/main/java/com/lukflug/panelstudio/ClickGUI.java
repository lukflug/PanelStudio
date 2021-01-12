package com.lukflug.panelstudio;

import java.util.ArrayList;
import java.util.List;

import com.lukflug.panelstudio.settings.Toggleable;
import com.lukflug.panelstudio.theme.DescriptionRenderer;

/**
 * Object representing the entire GUI.
 * All components should be a direct or indirect child of this objects.
 * @author lukflug
 */
public class ClickGUI implements PanelManager {
	/**
	 * List of direct child components (i.e. panels).
	 * The must all be {@link FixedComponent}.
	 */
	protected List<FixedComponent> components=new ArrayList<FixedComponent>();
	/**
	 * List of permanent components.
	 */
	protected List<FixedComponent> permanentComponents=new ArrayList<FixedComponent>();
	/**
	 * The {@link Interface} to be used by the GUI.
	 */
	protected Interface inter;
	/**
	 * The {@link DescriptionRenderer} to be used by the GUI.
	 */
	protected DescriptionRenderer descriptionRenderer;
	
	/**
	 * Constructor for the GUI.
	 * @param inter the {@link Interface} to be used by the GUI
	 * @param descriptionRenderer the {@link DescriptionRenderer} used by the GUI
	 */
	public ClickGUI (Interface inter, DescriptionRenderer descriptionRenderer) {
		this.inter=inter;
		this.descriptionRenderer=descriptionRenderer;
	}
	
	/**
	 * Get a list of panels in the GUI.
	 * @return list of all permanent panels (direct children)
	 */
	public List<FixedComponent> getComponents() {
		return permanentComponents;
	}
	
	/**
	 * Add a component to the GUI.
	 * @param component component to be added
	 */
	public void addComponent (FixedComponent component) {
		components.add(component);
		permanentComponents.add(component);
	}

	@Override
	public void showComponent(FixedComponent component) {
		if (!components.contains(component)) {
			components.add(component);
			component.enter(getContext(component,false));
		}
	}

	@Override
	public void hideComponent(FixedComponent component) {
		if (!permanentComponents.contains(component)) {
			if (components.remove(component)) component.exit(getContext(component,false));
		}
	}
	
	/**
	 * Render the GUI (lowest component first, highest component last).
	 */
	public void render() {
		List<FixedComponent> components=new ArrayList<FixedComponent>();
		for (FixedComponent component: this.components) {
			components.add(component);
		}
		Context descriptionContext=null;
		int highest=0;
		FixedComponent focusComponent=null;
		for (int i=components.size()-1;i>=0;i--) {
			FixedComponent component=components.get(i);
			Context context=getContext(component,true);
			component.getHeight(context);
			if (context.isHovered()) {
				highest=i;
				break;
			}
		}
		for (int i=0;i<components.size();i++) {
			FixedComponent component=components.get(i);
			Context context=getContext(component,i>=highest);
			component.render(context);
			if (context.foucsRequested()) focusComponent=component;
			if (context.isHovered() && context.getDescription()!=null) {
				descriptionContext=context;
			}
		}
		if (focusComponent!=null) {
			if (this.components.remove(focusComponent)) this.components.add(focusComponent);
		}
		if (descriptionContext!=null && descriptionRenderer!=null) {
			descriptionRenderer.renderDescription(descriptionContext);
		}
	}
	
	/**
	 * Handle a mouse button state change.
	 * @param button the button that changed its state
	 * @see Interface#LBUTTON
	 * @see Interface#RBUTTON
	 */
	public void handleButton (int button) {
		doComponentLoop((context,component)->component.handleButton(context,button));
	}
	
	/**
	 * Handle a key being typed.
	 * @param scancode the scancode of the key being typed
	 */
	public void handleKey (int scancode) {
		doComponentLoop((context,component)->component.handleKey(context,scancode));
	}
	
	/**
	 * Handle the mouse wheel being scrolled
	 * @param diff the amount by which the wheel was moved
	 */
	public void handleScroll (int diff) {
		doComponentLoop((context,component)->component.handleScroll(context,diff));
	}
	
	/**
	 * Handle the GUI being opened.
	 */
	public void enter() {
		doComponentLoop((context,component)->component.enter(context));
	}
	
	/**
	 * Handle the GUI being closed.
	 */
	public void exit() {
		doComponentLoop((context,component)->component.exit(context));
	}
	
	/**
	 * Store the GUI state.
	 * @param config the configuration list to be used
	 */
	public void saveConfig (ConfigList config) {
		config.begin(false);
		for (FixedComponent component: getComponents()) {
			PanelConfig cf=config.addPanel(component.getTitle());
			if (cf!=null) component.saveConfig(inter,cf);
		}
		config.end(false);
	}
	
	/**
	 * Load the GUI state.
	 * @param config the configuration list to be used
	 */
	public void loadConfig (ConfigList config) {
		config.begin(true);
		for (FixedComponent component: getComponents()) {
			PanelConfig cf=config.getPanel(component.getTitle());
			if (cf!=null) component.loadConfig(inter,cf);
		}
		config.end(true);
	}
	
	/**
	 * Create a context for a component.
	 * @param component the component
	 * @param highest whether this component is on top
	 * @return the context
	 */
	protected Context getContext (FixedComponent component, boolean highest) {
		return new Context(inter,component.getWidth(inter),component.getPosition(inter),true,highest);
	}

	@Override
	public Toggleable getComponentToggleable(FixedComponent component) {
		return new Toggleable() {
			@Override
			public void toggle() {
				if (isOn()) hideComponent(component);
				else showComponent(component);
			}

			@Override
			public boolean isOn() {
				return components.contains(component);
			}
		};
	}
	
	/**
	 * Loop through all components in reverse order and check for focus requests.
	 * @param function the function to execute in the loop
	 */
	protected void doComponentLoop (LoopFunction function) {
		List<FixedComponent> components=new ArrayList<FixedComponent>();
		for (FixedComponent component: this.components) {
			components.add(component);
		}
		boolean highest=true;
		FixedComponent focusComponent=null;
		for (int i=components.size()-1;i>=0;i--) {
			FixedComponent component=components.get(i);
			Context context=getContext(component,highest);
			function.loop(context,component);
			if (context.isHovered()) highest=false;
			if (context.foucsRequested()) focusComponent=component;
		}
		if (focusComponent!=null) {
			if (this.components.remove(focusComponent)) this.components.add(focusComponent);
		}
	}
	
	
	/**
	 * Interface used by {@link ClickGUI#doComponentLoop(LoopFunction)}.
	 * @author lukflug
	 */
	protected interface LoopFunction {
		/**
		 * Function to execute in the loop.
		 * @param context the context for the component
		 * @param component the component
		 */
		public void loop (Context context, FixedComponent component);
	}
}
