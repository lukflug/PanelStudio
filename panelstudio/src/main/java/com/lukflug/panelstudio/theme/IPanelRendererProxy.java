package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.Context;

/**
 * Proxy redirecting calls.
 * @author lukflug
 * @param <T> type representing state of the panel
 */
@FunctionalInterface
public interface IPanelRendererProxy<T> extends IPanelRenderer<T> {
	@Override
	public default void renderPanelOverlay(Context context, boolean focus, T state, boolean open) {
		getRenderer().renderPanelOverlay(context,focus,state,open);
	}
	
	@Override
	public default void renderTitleOverlay (Context context, boolean focus, T state, boolean open) {
		getRenderer().renderTitleOverlay(context,focus,state,open);
	}

	/**
	 * The renderer to be redirected to.
	 * @return the renderer
	 */
	public IPanelRenderer<T> getRenderer();
}
