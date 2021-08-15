package com.lukflug.panelstudio.component;

import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.popup.IPopupPositioner;

/**
 * A {@link FixedComponent} that uses dynamic {@link IPopupPositioner} for positioning. 
 * @author lukflug
 * @param <T> the component type
 */
public class PopupComponent<T extends IComponent> extends FixedComponent<T> {
	/**
	 * The displaying component location.
	 */
	protected Rectangle component;
	/**
	 * The panel location.
	 */
	protected Rectangle panel;
	/**
	 * The positioner to be used.
	 */
	protected IPopupPositioner positioner;
	
	/**
	 * Constructor.
	 * @param component the component to be wrapped
	 * @param width the width of the component
	 */
	public PopupComponent (T component, int width) {
		super(component,new Point(0,0),width,null,false,"");
	}

	@Override
	public Point getPosition (IInterface inter) {
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
