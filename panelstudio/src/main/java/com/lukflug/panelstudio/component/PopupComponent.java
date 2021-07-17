package com.lukflug.panelstudio.component;

import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.popup.IPopup;
import com.lukflug.panelstudio.popup.IPopupPositioner;

public class PopupComponent<T extends IComponent> extends FixedComponent<T> implements IPopup {
	protected Rectangle component,panel;
	protected IPopupPositioner positioner;
	
	public PopupComponent (T component, int width) {
		super(component,new Point(0,0),width,null,false,"");
	}

	@Override
	public Point getPosition(IInterface inter) {
		Context temp=new Context(inter,width,position,true,true);
		getHeight(temp);
		return positioner.getPosition(inter,temp.getSize(),component,panel);
	}

	@Override
	public void setPosition (IInterface inter, Rectangle component, Rectangle panel, IPopupPositioner positioner) {
		this.component=component;
		this.panel=panel;
		this.positioner=positioner;
	}
}
