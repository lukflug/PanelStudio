package com.lukflug.panelstudio;

import java.awt.Point;

/**
 * Interface representing a {@link Component} that has a fixed position
 * (i.e. the position isn't determined by the parent via {@link Context}).
 * @author lukflug
 */
public interface FixedComponent extends Component,Focusable {
	/**
	 * Get the current component position.
	 * @param inter current interface
	 * @return current position
	 */
	public Point getPosition (Interface inter);
	
	/**
	 * Set the current component position.
	 * @param position new position
	 */
	public void setPosition (Point position);
}
