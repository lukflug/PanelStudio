package com.lukflug.panelstudio.widget;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.AnimatedToggleable;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.component.IFixedComponentProxy;
import com.lukflug.panelstudio.component.IResizable;
import com.lukflug.panelstudio.component.IScrollSize;
import com.lukflug.panelstudio.theme.IResizeBorderRenderer;
import com.lukflug.panelstudio.theme.RendererTuple;

public class ResizableComponent<T extends IFixedComponent> implements IFixedComponentProxy<T> {
	protected T component;
	protected IResizeBorderRenderer renderer;
	protected IResizable size;
	protected boolean resizing[]={false,false,false,false};
	protected Point attachPoint=null;
	protected Rectangle attachRect=null;
	
	public ResizableComponent (T component, IResizeBorderRenderer renderer, IResizable size) {
		this.component=component;
		this.renderer=renderer;
		this.size=size;
	}
	
	@Override
	public void render (Context context) {
		IFixedComponentProxy.super.render(context);
		renderer.drawBorder(context,context.hasFocus());
	}
	
	@Override
	public void handleButton (Context context, int button) {
		IFixedComponentProxy.super.handleButton(context,button);
		if (button==IInterface.LBUTTON && context.isClicked(button)) {
			attachPoint=context.getInterface().getMouse();
			attachRect=new Rectangle(getComponent().getPosition(context.getInterface()),size.getSize());
			Rectangle r=context.getRect();
			if (new Rectangle(r.x,r.y,r.width,renderer.getBorder()).contains(attachPoint)) {
				resizing[0]=true;
			} else if (new Rectangle(r.x,r.y+r.height-renderer.getBorder(),r.width,renderer.getBorder()).contains(attachPoint)) {
				resizing[1]=true;
			}
			if (new Rectangle(r.x,r.y,renderer.getBorder(),r.height).contains(attachPoint)) {
				resizing[2]=true;
			} else if (new Rectangle(r.x+r.width-renderer.getBorder(),r.y,renderer.getBorder(),r.height).contains(attachPoint)) {
				resizing[3]=true;
			}
		} else if (!context.getInterface().getButton(IInterface.LBUTTON)) {
			resizing[0]=false;
			resizing[1]=false;
			resizing[2]=false;
			resizing[3]=false;
		}
	}
	
	@Override
	public int getHeight (int height) {
		return height+2*renderer.getBorder();
	}
	
	@Override
	public Context getContext (Context context) {
		if (resizing[0]) {
			getComponent().setPosition(context.getInterface(),new Point(getComponent().getPosition(context.getInterface()).x,attachRect.y+context.getInterface().getMouse().y-attachPoint.y));
			size.setSize(new Dimension(size.getSize().width,attachRect.height-context.getInterface().getMouse().y+attachPoint.y));
		} else if (resizing[1]) {
			size.setSize(new Dimension(size.getSize().width,attachRect.height+context.getInterface().getMouse().y-attachPoint.y));
		}
		if (resizing[2]) {
			getComponent().setPosition(context.getInterface(),new Point(attachRect.x+context.getInterface().getMouse().x-attachPoint.x,getComponent().getPosition(context.getInterface()).y));
			size.setSize(new Dimension(attachRect.width-context.getInterface().getMouse().x+attachPoint.x,size.getSize().height));
		} else if (resizing[3]) {
			size.setSize(new Dimension(attachRect.width+context.getInterface().getMouse().x-attachPoint.x,size.getSize().height));
		}
		return new Context(context,context.getSize().width-2*renderer.getBorder(),new Point(renderer.getBorder(),renderer.getBorder()),true,true);
	}
	
	@Override
	public Point getPosition (IInterface inter) {
		Point p=getComponent().getPosition(inter);
		p.translate(-renderer.getBorder(),-renderer.getBorder());
		return p;
	}
	
	@Override
	public void setPosition (IInterface inter, Point position) {
		position.translate(renderer.getBorder(),renderer.getBorder());
		getComponent().setPosition(inter,position);
	}
	
	@Override
	public int getWidth (IInterface inter) {
		return size.getSize().width+2*renderer.getBorder();
	}
	
	@Override
	public T getComponent() {
		return component;
	}
	
	public static <S extends IComponent,T extends IComponent,U> IFixedComponent createResizableComponent (S title, T content, Supplier<U> state, AnimatedToggleable open, RendererTuple<U> renderer, IResizeBorderRenderer resizeRenderer, IResizable size, IScrollSize scrollSize, Point position, int width, boolean savesState, String configName) {
		IFixedComponent draggable=ClosableComponent.createDraggableComponent(title,content,state,open,renderer,scrollSize,position,width,savesState,configName);
		if (size!=null) {
			return new ResizableComponent<>(draggable,resizeRenderer,size);
		} else {
			return draggable;
		}
	}
}
