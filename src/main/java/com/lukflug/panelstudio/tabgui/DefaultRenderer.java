package com.lukflug.panelstudio.tabgui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.theme.ColorScheme;

/**
 * Standard TabGUI look.
 * @author lukflug
 */
public class DefaultRenderer implements TabGUIRenderer {
	protected ColorScheme scheme;
	protected int height,border;
	protected int up,down,left,right,enter;
	
	public DefaultRenderer (ColorScheme scheme, int height, int border, int up, int down, int left, int right, int enter) {
		this.scheme=scheme;
		this.border=border;
		this.height=height;
		this.up=up;
		this.down=down;
		this.left=left;
		this.right=right;
		this.enter=enter;
	}
	
	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getBorder() {
		return border;
	}

	@Override
	public void renderBackground(Context context, int offset, int height) {
		Color bgcolor=scheme.getBackgroundColor();
		bgcolor=new Color(bgcolor.getRed(),bgcolor.getGreen(),bgcolor.getBlue(),scheme.getOpacity());
		Color border=scheme.getOutlineColor();
		Color active=scheme.getActiveColor();
		context.getInterface().fillRect(context.getRect(),bgcolor,bgcolor,bgcolor,bgcolor);
		context.getInterface().drawRect(context.getRect(),border,border,border,border);
		Point p=context.getPos();
		p.translate(0,offset);
		Rectangle rect=new Rectangle(p,new Dimension(context.getSize().width,height));
		context.getInterface().fillRect(rect,active,active,active,active);
		context.getInterface().drawRect(rect,border,border,border,border);
	}

	@Override
	public void renderCaption(Context context, String caption, int index, int height, boolean active) {
		Color color;
		if (active) color=scheme.getActiveColor();
		else color=scheme.getFontColor();
		Point p=context.getPos();
		p.translate(0,index*height);
		context.getInterface().drawString(p,caption,color);
	}

	@Override
	public ColorScheme getColorScheme() {
		return scheme;
	}

	@Override
	public boolean isUpKey(int key) {
		return key==up;
	}

	@Override
	public boolean isDownKey(int key) {
		return key==down;
	}

	@Override
	public boolean isSelectKey(int key) {
		return key==right || key==enter;
	}

	@Override
	public boolean isEscapeKey(int key) {
		return key==left;
	}
}
