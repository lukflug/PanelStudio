package com.lukflug.panelstudio.theme;

import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;

public interface ISwitchRenderer<T> extends IButtonRenderer<T> {
	public Rectangle getOnField (Context context);
	
	public Rectangle getOffField (Context context);
}
