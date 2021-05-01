package com.lukflug.panelstudio.theme;

import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;

@FunctionalInterface
public interface ITextFieldRendererProxy extends ITextFieldRenderer {
	@Override
	public default int renderTextField (Context context, String title, boolean focus, String content, int position, int select, int boxPosition, boolean insertMode) {
		return getRenderer().renderTextField(context,title,focus,content,position,select,boxPosition,insertMode);
	}
	
	@Override
	public default int getDefaultHeight() {
		return getRenderer().getDefaultHeight();
	}
	
	@Override
	public default Rectangle getTextArea (Context context, String title) {
		return getRenderer().getTextArea(context,title);
	}
	
	@Override
	public default int transformToCharPos (Context context, String content, int boxPosition) {
		return getRenderer().transformToCharPos(context,content,boxPosition);
	}

	/**
	 * The renderer to be redirected to.
	 * @return the renderer
	 */
	public ITextFieldRenderer getRenderer();
}
