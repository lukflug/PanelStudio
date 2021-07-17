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
	public default void renderBackground (Context context, boolean focus) {
		getRenderer().renderBackground(context,focus);
	}
	
	@Override
	public default int getBorder() {
		return getRenderer().getBorder();
	}
	
	@Override
	public default int getLeft() {
		return getRenderer().getLeft();
	}
	
	@Override
	public default int getRight() {
		return getRenderer().getRight();
	}
	
	@Override
	public default int getTop() {
		return getRenderer().getTop();
	}
	
	@Override
	public default int getBottom() {
		return getRenderer().getBottom();
	}
	
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
