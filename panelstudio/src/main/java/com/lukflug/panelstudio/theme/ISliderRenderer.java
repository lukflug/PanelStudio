package com.lukflug.panelstudio.theme;

import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;

/**
 * Interface abstracting the rendering of a slider.
 * @author lukflug
 */
public interface ISliderRenderer {
	/**
	 * Render a slider.
	 * @param context the context to be used
	 * @param title the title of the slider
	 * @param state the display state of the slider
	 * @param focus the focus state of the slider
	 * @param value the value of the slider (between 0 and 1)
	 */
	public void renderSlider (Context context, String title, String state, boolean focus, double value);
	
	/**
	 * Returns the default height.
	 * @return the default height
	 */
	public int getDefaultHeight();
	
	/**
	 * Get slidable area.
	 * @param context the context to be used
	 * @return the rectangle reprsenting the area that can be slided
	 */
	public default Rectangle getSlideArea (Context context) {
		return context.getRect();
	}
}
