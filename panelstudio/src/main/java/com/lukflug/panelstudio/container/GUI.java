package com.lukflug.panelstudio.container;

import java.awt.Point;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.config.IConfigList;
import com.lukflug.panelstudio.popup.IPopupPositioner;
import com.lukflug.panelstudio.theme.IDescriptionRenderer;

/**
 * Object representing the entire GUI.
 * All components should be a direct or indirect child of this object.
 * @author lukflug
 */
public class GUI implements IContainer<IFixedComponent> {
	/**
	 * Container containing all components.
	 */
	protected FixedContainer container;
	/**
	 * The {@link Interface} to be used by the GUI.
	 */
	protected IInterface inter;
	/**
	 * The {@link DescriptionRenderer} to be used by the GUI.
	 */
	protected IDescriptionRenderer descriptionRenderer;
	/**
	 * The {@link IPopupPositioner} to be used to position the descriptions.
	 */
	protected IPopupPositioner descriptionPosition;
	
	/**
	 * Constructor for the GUI.
	 * @param inter the {@link Interface} to be used by the GUI
	 * @param descriptionRenderer the {@link DescriptionRenderer} used by the GUI
	 * @param descriptionPosition the {@link IPopupPositioner} to be used to position the descriptions
	 */
	public GUI (IInterface inter, IDescriptionRenderer descriptionRenderer, IPopupPositioner descriptionPosition) {
		this.inter=inter;
		this.descriptionRenderer=descriptionRenderer;
		this.descriptionPosition=descriptionPosition;
		container=new FixedContainer(()->"GUI",null,false);
	}
	
	@Override
	public boolean addComponent (IFixedComponent component) {
		return container.addComponent(component);
	}

	@Override
	public boolean addComponent(IFixedComponent component, IBoolean visible) {
		return container.addComponent(component,visible);
	}

	@Override
	public boolean removeComponent(IFixedComponent component) {
		return container.removeComponent(component);
	}
	
	/**
	 * Render the GUI (lowest component first, highest component last).
	 */
	public void render() {
		Context context=getContext();
		container.render(context);
		if (context.getDescription()!=null) {
			Point pos=descriptionPosition.getPosition(inter,null,context.getDescription().getComponentPos(),context.getDescription().getPanelPos());
			descriptionRenderer.renderDescription(inter,pos,context.getDescription().getContent());
		}
	}
	
	/**
	 * Handle a mouse button state change.
	 * @param button the button that changed its state
	 * @see Interface#LBUTTON
	 * @see Interface#RBUTTON
	 */
	public void handleButton (int button) {
		container.handleButton(getContext(),button);
	}
	
	/**
	 * Handle a key being typed.
	 * @param scancode the scancode of the key being typed
	 */
	public void handleKey (int scancode) {
		container.handleKey(getContext(),scancode);
	}
	
	public void handleChar (char character) {
		container.handleChar(getContext(),character);
	}
	
	/**
	 * Handle the mouse wheel being scrolled
	 * @param diff the amount by which the wheel was moved
	 */
	public void handleScroll (int diff) {
		container.handleScroll(getContext(),diff);
	}
	
	/**
	 * Handle the GUI being opened.
	 */
	public void enter() {
		container.enter();
	}
	
	/**
	 * Handle the GUI being closed.
	 */
	public void exit() {
		container.exit();
	}
	
	/**
	 * Store the GUI state.
	 * @param config the configuration list to be used
	 */
	public void saveConfig (IConfigList config) {
		container.saveConfig(inter,config);
	}
	
	/**
	 * Load the GUI state.
	 * @param config the configuration list to be used
	 */
	public void loadConfig (IConfigList config) {
		container.loadConfig(inter,config);
	}
	
	/**
	 * Create a context for the container.
	 * @return the context
	 */
	protected Context getContext() {
		return new Context(inter,0,new Point(0,0),true,true);
	}
}
