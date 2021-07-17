package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.Context;

/**
 * Interface to describe the appearance of a container.
 * @author lukflug
 */
public interface IContainerRenderer {
	/**
	 * Render the container background.
	 * @param context the context of the container
	 * @param focus whether this container has focus
	 */
	public default void renderBackground (Context context, boolean focus) {
	}
	
	/**
	 * Get the border between two components.
	 * @return the border
	 */
	public default int getBorder() {
		return 0;
	}
	
	/**
	 * Get left border.
	 * @return the left border
	 */
	public default int getLeft() {
		return 0;
	}
	
	/**
	 * Get right border.
	 * @return the right border
	 */
	public default int getRight() {
		return 0;
	}
	
	/**
	 * Get top border.
	 * @return the top border
	 */
	public default int getTop() {
		return 0;
	}
	
	/**
	 * Get bottom border.
	 * @return the bottom border
	 */
	public default int getBottom() {
		return 0;
	}
}
