package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.Context;

/**
 * Proxy redirecting calls.
 * @author lukflug
 * @param <T> type representing state of button
 */
@FunctionalInterface
public interface IButtonRendererProxy<T> extends IButtonRenderer<T> {
	@Override
	public default void renderButton(Context context, String title, boolean focus, T state) {
		getRenderer().renderButton(context,title,focus,state);
	}

	@Override
	public default int getDefaultHeight() {
		return getRenderer().getDefaultHeight();
	}

	/**
	 * The renderer to be redirected to.
	 * @return the renderer
	 */
	public IButtonRenderer<T> getRenderer();
}
