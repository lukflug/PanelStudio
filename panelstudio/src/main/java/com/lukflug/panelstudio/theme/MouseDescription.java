package com.lukflug.panelstudio.theme;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.Context;

public class MouseDescription implements DescriptionRenderer {
	protected Point offset;
	
	public MouseDescription (Point offset) {
		this.offset=offset;
	}
	
	@Override
	public void renderDescription(Context context) {
		if (context.getDescription()!=null) {
			Point pos=context.getInterface().getMouse();
			pos.translate(offset.x,offset.y);
			Rectangle r=new Rectangle(pos,new Dimension(context.getInterface().getFontWidth(context.getDescription()),context.getInterface().getFontHeight()));
			Color bgcolor=new Color(0,0,0);
			context.getInterface().fillRect(r,bgcolor,bgcolor,bgcolor,bgcolor);
			Color color=new Color(255,255,255);
			context.getInterface().drawRect(r,color,color,color,color);
			context.getInterface().drawString(pos,context.getDescription(),color);
		}
	}
}
