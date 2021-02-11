package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.Context;

/**
 * Proxy redirecting calls.
 * @author lukflug
 */
@FunctionalInterface
public interface IPanelRendererProxy extends IPanelRenderer {
	@Override
	public default void renderPanelOverlay(Context context, boolean focus, boolean active) {
		getRenderer().renderPanelOverlay(context,focus,active);
	}

	/**
	 * The renderer to be redirected to.
	 * @return the renderer
	 */
	public IPanelRenderer getRenderer();
}
