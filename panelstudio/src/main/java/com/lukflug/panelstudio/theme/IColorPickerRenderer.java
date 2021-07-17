package com.lukflug.panelstudio.theme;

import java.awt.Color;
import java.awt.Point;

import com.lukflug.panelstudio.base.Context;

public interface IColorPickerRenderer {
	public void renderPicker (Context context, boolean focus, Color color);
	
	public Color transformPoint (Context context, Color color, Point point);
	
	public int getDefaultHeight (int width);
}
