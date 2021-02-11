package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.Context;

/**
 * Proxy redirecting calls.
 * @author lukflug
 */
@FunctionalInterface
public interface IContainerRendererProxy extends IContainerRenderer {
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

	/**
	 * The renderer to be redirected to.
	 * @return the renderer
	 */
	public IContainerRenderer getRenderer();
}
