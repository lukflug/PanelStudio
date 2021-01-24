package com.lukflug.panelstudio;

import com.lukflug.panelstudio.settings.IToggleable;

/**
 * Interface used by transient components to show and hide themselves.
 * @author lukflug
 */
public interface IPanelManager {
	/**
	 * Add a component to be visible.
	 * @param component the component to be added.
	 */
	public void showComponent (IFixedComponent component);
	
	/**
	 * Hide a component.
	 * @param component the component to be removed.
	 */
	public void hideComponent (IFixedComponent component);
	
	/**
	 * Get toggleable indicating whether a component is shown or not.
	 * @param component the component in question
	 * @return the toggleable indicating whether the component is shown
	 */
	public IToggleable getComponentToggleable (IFixedComponent component);
}
