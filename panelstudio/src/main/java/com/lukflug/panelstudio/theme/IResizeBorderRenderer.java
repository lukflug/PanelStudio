package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.Context;

public interface IResizeBorderRenderer {
	public void drawBorder (Context context, boolean focus);
	
	public int getBorder();
}
