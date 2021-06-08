package com.lukflug.panelstudio.theme;

import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;

/**
 * Proxy redirecting calls.
 * @author lukflug
 */
@FunctionalInterface
public interface ISliderRendererProxy extends ISliderRenderer {
	@Override
	public default void renderSlider(Context context, String title, String state, boolean focus, double value) {
		getRenderer().renderSlider(context,title,state,focus,value);
	}

	@Override
	public default int getDefaultHeight() {
		return getRenderer().getDefaultHeight();
	}

	@Override
	public default Rectangle getSlideArea(Context context) {
		return getRenderer().getSlideArea(context);
	}

	/**
	 * The renderer to be redirected to.
	 * @return the renderer
	 */
	public ISliderRenderer getRenderer();
}
