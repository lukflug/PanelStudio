package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.Context;

/**
 * Interface abstracting the rendering of a panel.
 * @author lukflug
 * @param <T> type representing state of the panel
 */
public interface IPanelRenderer<T> extends IContainerRenderer {
	/**
	 * Render the outline of a panel.
	 * @param context the context to be used
	 * @param focus the focus state of the panel
	 * @param active the state of the panel
	 */
	public void renderPanelOverlay (Context context, boolean focus, T state, boolean open);
	
	public void renderTitleOverlay (Context context, boolean focus, T state, boolean open);
}
