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
	public void renderBackground (Context context, boolean focus);
	
	/**
	 * Get the border between two components.
	 * @return the border
	 */
	public int getBorder();
	
	/**
	 * Get left border.
	 * @return the left border
	 */
	public int getLeft();
	
	/**
	 * Get right border.
	 * @return the right border
	 */
	public int getRight();
	
	/**
	 * Get top border.
	 * @return the top border
	 */
	public int getTop();
	
	/**
	 * Get bottom border.
	 * @return the bottom border
	 */
	public int getBottom();
}
