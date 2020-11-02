package com.lukflug.panelstudio;

import java.util.ArrayList;
import java.util.List;

/**
 * Object representing the entire GUI.
 * All components should be a direct or indirect child of this objects.
 * @author lukflug
 */
public class ClickGUI implements FocusManager {
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
	 * The width of the panels.
	 */
	protected final int width;
	
	/**
	 * Constructor for the GUI.
	 * @param inter the {@link Interface} to be used by the GUI
	 * @param width the width of the panels.
	 */
	public ClickGUI (Interface inter, int width) {
		this.inter=inter;
		this.width=width;
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
		for (int i=components.size()-1;i>=0;i--) {
			FixedComponent component=components.get(i);
			Context context=new Context(inter,this,width,component.getPosition(inter),true,true);
			component.getHeight(context);
			if (context.isHovered()) {
				highest=i;
				break;
			}
		}
		for (int i=0;i<components.size();i++) {
			FixedComponent component=components.get(i);
			Context context=new Context(inter,this,width,component.getPosition(inter),true,i>=highest);
			component.render(context);
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
		for (int i=components.size()-1;i>=0;i--) {
			FixedComponent component=components.get(i);
			Context context=new Context(inter,this,width,component.getPosition(inter),true,highest);
			component.handleButton(context,button);
			if (context.isHovered()) highest=false;
		}
	}
	
	/**
	 * Handle a key being typed.
	 * @param scancode the scancode of the key being typed
	 */
	public void handleKey (int scancode) {
		boolean highest=true;
		for (FixedComponent component: components) {
			Context context=new Context(inter,this,width,component.getPosition(inter),true,highest);
			component.handleKey(context,scancode);
			if (context.isHovered()) highest=false;
		}
	}
	
	/**
	 * Handle the GUI being closed.
	 */
	public void exit() {
		boolean highest=true;
		for (FixedComponent component: components) {
			Context context=new Context(inter,this,width,component.getPosition(inter),true,highest);
			component.exit(context);
			if (context.isHovered()) highest=false;
		}
	}
	
	/**
	 * Method to be called by a panel that requests focus within the GUI.
	 * Moves the panel requesting focus to the top.
	 */
	@Override
	public void requestFocus (Focusable component) {
		components.remove(component);
		components.add((FixedComponent)component);
	}
}
