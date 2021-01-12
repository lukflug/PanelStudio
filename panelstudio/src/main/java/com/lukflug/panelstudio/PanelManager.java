package com.lukflug.panelstudio;

import com.lukflug.panelstudio.settings.Toggleable;

/**
 * Interface used by transient components to show and hide themselves.
 * @author lukflug
 */
public interface PanelManager {
	/**
	 * Add a component to be visible.
	 * @param component the component to be added.
	 */
	public void showComponent (FixedComponent component);
	
	/**
	 * Hide a component.
	 * @param component the component to be removed.
	 */
	public void hideComponent (FixedComponent component);
	
	/**
	 * Get toggleable indicating whether a component is shown or not.
	 * @param component the component in question
	 * @return the toggleable indicating whether the component is shown
	 */
	public Toggleable getComponentToggleable (FixedComponent component);
}
