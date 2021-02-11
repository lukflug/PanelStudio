package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.Context;

/**
 * Proxy redirecting calls.
 * @author lukflug
 */
@FunctionalInterface
public interface IEmptySpaceRendererProxy extends IEmptySpaceRenderer {
	@Override
	public default void renderSpace(Context context, boolean focus) {
		getRenderer().renderSpace(context,focus);
	}

	/**
	 * The renderer to be redirected to.
	 * @return the renderer
	 */
	public IEmptySpaceRenderer getRenderer();
}
