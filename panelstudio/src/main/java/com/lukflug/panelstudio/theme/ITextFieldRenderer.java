package com.lukflug.panelstudio.theme;

import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;

public interface ITextFieldRenderer {
	public int renderTextField (Context context, String title, boolean focus, String content, int position, int select, int boxPosition, boolean insertMode);
	
	public int getDefaultHeight();
	
	public Rectangle getTextArea (Context context, String title);
	
	public int transformToCharPos (Context context, String content, int boxPosition);
}
