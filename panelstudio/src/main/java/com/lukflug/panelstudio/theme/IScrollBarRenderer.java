package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.Context;

/**
 * Interface abstracting the rendering of a scroll bar.
 * @author lukflug
 */
public interface IScrollBarRenderer {
	/**
	 * Render a scroll bar.
	 * @param context the context to be used
	 * @param focus the focus state of the scroll bar
	 * @param active the active state of the scroll bar
	 * @param horizontal whether the scroll bar is horizontal
	 * @param height the height of the scroll bar
	 * @param position the scroll position
	 * @return the new scroll position based on mouse input
	 */
	public int renderScrollBar (Context context, boolean focus, boolean active, boolean horizontal, int height, int position);
	
	/**
	 * Get default scroll bar thickness.
	 * @return the default size
	 */
	public int getThickness();
}
