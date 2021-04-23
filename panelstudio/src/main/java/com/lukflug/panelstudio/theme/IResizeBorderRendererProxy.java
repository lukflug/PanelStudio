package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.Context;

@FunctionalInterface
public interface IResizeBorderRendererProxy extends IResizeBorderRenderer {
	public default void drawBorder (Context context, boolean focus) {
		getRenderer().drawBorder(context,focus);
	}
	
	public default int getBorder() {
		return getRenderer().getBorder();
	}
	
	/**
	 * The renderer to be redirected to.
	 * @return the renderer
	 */
	public IResizeBorderRenderer getRenderer();
}
