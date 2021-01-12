package com.lukflug.panelstudio;

import java.awt.Point;

/**
 * Interface representing a {@link Component} that has a fixed position
 * (i.e. the position isn't determined by the parent via {@link Context}).
 * @author lukflug
 */
public interface FixedComponent extends Component {
	/**
	 * Get the current component position.
	 * @param inter current interface
	 * @return current position
	 */
	public Point getPosition (Interface inter);
	
	/**
	 * Set the current component position.
	 * @param inter current interface
	 * @param position new position
	 */
	public void setPosition (Interface inter, Point position);
	
	/**
	 * Get the component width.
	 * @param inter current interface
	 * @return component width
	 */
	public int getWidth (Interface inter);
	
	/**
	 * Saves the component state
	 * @param inter current interface
	 * @param config configuration to use
	 */
	public void saveConfig (Interface inter, PanelConfig config);
	
	/**
	 * Loads the component state
	 * @param inter current interface
	 * @param config configuration to use
	 */
	public void loadConfig (Interface inter, PanelConfig config);
}
