package com.lukflug.panelstudio;

import java.awt.Point;

/**
 * Interface representing a single panel configuration state.
 * @author lukflug
 */
public interface PanelConfig {
	/**
	 * Store the position of the panel.
	 * @param position the current position of the panel
	 */
	public void savePositon (Point position);
	
	/**
	 * Load the position of the point.
	 * @return the stored position
	 */
	public Point loadPosition();
	
	/**
	 * Store the state of the panel.
	 * @param state whether the panel is open
	 */
	public void saveState (boolean state);
	
	/**
	 * Load the state of the panel.
	 * @return the stored panel state
	 */
	public boolean loadState();
}
