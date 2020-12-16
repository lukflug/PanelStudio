package com.lukflug.panelstudio;

import java.util.ArrayList;
import java.util.List;

/**
 * Object representing the entire GUI.
 * All components should be a direct or indirect child of this objects.
 * @author lukflug
 */
public class ClickGUI {
	/**
	 * List of direct child components (i.e. panels).
	 * The must all be {@link FixedComponent}.
	 */
	protected List<FixedComponent> components;
	/**
	 * The {@link Interface} to be used by the GUI.
	 */
	protected Interface inter;
	
	/**
	 * Constructor for the GUI.
	 * @param inter the {@link Interface} to be used by the GUI
	 */
	public ClickGUI (Interface inter) {
		this.inter=inter;
		components=new ArrayList<FixedComponent>();
	}
	
	/**
	 * Get a list of panels in the GUI.
	 * @return list of all panels (direct children)
	 */
	public List<FixedComponent> getComponents() {
		return components;
	}
	
	/**
	 * Add a component to the GUI.
	 * @param component component to be added
	 */
	public void addComponent (FixedComponent component) {
		components.add(component);
	}
	
	/**
	 * Render the GUI (lowest component first, highest component last).
	 */
	public void render() {
		int highest=0;
		FixedComponent focusComponent=null;
		for (int i=components.size()-1;i>=0;i--) {
			FixedComponent component=components.get(i);
			Context context=new Context(inter,component.getWidth(inter),component.getPosition(inter),true,true);
			component.getHeight(context);
			if (context.isHovered()) {
				highest=i;
				break;
			}
		}
		for (int i=0;i<components.size();i++) {
			FixedComponent component=components.get(i);
			Context context=new Context(inter,component.getWidth(inter),component.getPosition(inter),true,i>=highest);
			component.render(context);
			if (context.foucsRequested()) focusComponent=component;
		}
		if (focusComponent!=null) {
			components.remove(focusComponent);
			components.add(focusComponent);
		}
	}
	
	/**
	 * Handle a mouse button state change.
	 * @param button the button that changed its state
	 * @see Interface#LBUTTON
	 * @see Interface#RBUTTON
	 */
	public void handleButton (int button) {
		boolean highest=true;
		FixedComponent focusComponent=null;
		for (int i=components.size()-1;i>=0;i--) {
			FixedComponent component=components.get(i);
			Context context=new Context(inter,component.getWidth(inter),component.getPosition(inter),true,highest);
			component.handleButton(context,button);
			if (context.isHovered()) highest=false;
			if (context.foucsRequested()) focusComponent=component;
		}
		if (focusComponent!=null) {
			components.remove(focusComponent);
			components.add(focusComponent);
		}
	}
	
	/**
	 * Handle a key being typed.
	 * @param scancode the scancode of the key being typed
	 */
	public void handleKey (int scancode) {
		boolean highest=true;
		FixedComponent focusComponent=null;
		for (FixedComponent component: components) {
			Context context=new Context(inter,component.getWidth(inter),component.getPosition(inter),true,highest);
			component.handleKey(context,scancode);
			if (context.isHovered()) highest=false;
			if (context.foucsRequested()) focusComponent=component;
		}
		if (focusComponent!=null) {
			components.remove(focusComponent);
			components.add(focusComponent);
		}
	}
	
	/**
	 * Handle the mouse wheel being scrolled
	 * @param diff the amount by which the wheel was moved
	 */
	public void handleScroll (int diff) {
		boolean highest=true;
		FixedComponent focusComponent=null;
		for (FixedComponent component: components) {
			Context context=new Context(inter,component.getWidth(inter),component.getPosition(inter),true,highest);
			component.handleScroll(context,diff);
			if (context.isHovered()) highest=false;
			if (context.foucsRequested()) focusComponent=component;
		}
		if (focusComponent!=null) {
			components.remove(focusComponent);
			components.add(focusComponent);
		}
	}
	
	/**
	 * Handle the GUI being closed.
	 */
	public void exit() {
		boolean highest=true;
		FixedComponent focusComponent=null;
		for (FixedComponent component: components) {
			Context context=new Context(inter,component.getWidth(inter),component.getPosition(inter),true,highest);
			component.exit(context);
			if (context.isHovered()) highest=false;
			if (context.foucsRequested()) focusComponent=component;
		}
		if (focusComponent!=null) {
			components.remove(focusComponent);
			components.add(focusComponent);
		}
	}
	
	/**
	 * Store the GUI state.
	 * @param config the configuration list to be used
	 */
	public void saveConfig (ConfigList config) {
		config.begin(false);
		for (FixedComponent component: getComponents()) {
			PanelConfig cf=config.getPanel(component.getTitle());
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
}
