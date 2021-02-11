package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.Context;

/**
 * Proxy redirecting calls.
 * @author lukflug
 */
@FunctionalInterface
public interface IScrollBarRendererProxy extends IScrollBarRenderer {
	@Override
	public default int renderScrollBar(Context context, boolean focus, boolean active, boolean horizontal, int height, int position) {
		return getRenderer().renderScrollBar(context,focus,active,horizontal,height,position);
	}

	@Override
	public default int getThickness() {
		return getRenderer().getThickness();
	}

	/**
	 * The renderer to be redirected to.
	 * @return the renderer
	 */
	public IScrollBarRenderer getRenderer();
}
