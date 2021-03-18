package com.lukflug.panelstudio.popup;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.IInterface;

public class CenteredPositioner implements IPopupPositioner {
	protected Supplier<Rectangle> rect;	
	
	public CenteredPositioner (Supplier<Rectangle> rect) {
		this.rect=rect;
	}
	
	@Override
	public Point getPosition(IInterface inter, Dimension popup, Rectangle component, Rectangle panel) {
		Rectangle rect=this.rect.get();
		return new Point(rect.x+rect.width/2-popup.width/2,rect.y+rect.height/2-popup.height/2);
	}
}
