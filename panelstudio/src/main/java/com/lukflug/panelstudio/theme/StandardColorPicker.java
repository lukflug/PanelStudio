package com.lukflug.panelstudio.theme;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;

public abstract class StandardColorPicker implements IColorPickerRenderer {
	@Override
	public void renderPicker(Context context, boolean focus, Color color) {
		float[] hsb=Color.RGBtoHSB(color.getRed(),color.getGreen(),color.getBlue(),null);
		Color colorA=Color.getHSBColor(hsb[0],0,1),colorB=Color.getHSBColor(hsb[0],1,1);
		context.getInterface().fillRect(context.getRect(),colorA,colorB,colorB,colorA);
		Color colorC=new Color(0,0,0,0),colorD=new Color(0,0,0);
		context.getInterface().fillRect(context.getRect(),colorC,colorC,colorD,colorD);
		Point p=new Point((int)Math.round(context.getPos().x+hsb[1]*(context.getSize().width-1)),(int)Math.round(context.getPos().y+(1-hsb[2])*(context.getSize().height-1)));
		renderCursor(context,p,color);
	}

	@Override
	public Color transformPoint(Context context, Color color, Point point) {
		float hue=Color.RGBtoHSB(color.getRed(),color.getGreen(),color.getBlue(),null)[0];
		float saturation=(point.x-context.getPos().x)/(float)(context.getSize().width-1);
		float brightness=1+(context.getPos().y-point.y)/(float)(context.getSize().height-1);
		if (saturation>1) saturation=1;
		else if (saturation<0) saturation=0;
		if (brightness>1) brightness=1;
		else if (brightness<0) brightness=0;
		Color value=Color.getHSBColor(hue,saturation,brightness);
		return ITheme.combineColors(value,color);
	}

	@Override
	public int getDefaultHeight(int width) {
		return Math.min(width,8*getBaseHeight());
	}
	
	protected void renderCursor (Context context, Point p, Color color) {
		Color fontColor=new Color(255-color.getRed(),255-color.getGreen(),255-color.getBlue());
		context.getInterface().fillRect(new Rectangle(p.x,p.y-getPadding(),1,2*getPadding()+1),fontColor,fontColor,fontColor,fontColor);
		context.getInterface().fillRect(new Rectangle(p.x-getPadding(),p.y,2*getPadding()+1,1),fontColor,fontColor,fontColor,fontColor);
	}
	
	public abstract int getPadding();
	
	public abstract int getBaseHeight();
}
