package com.lukflug.panelstudio.widget;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.AnimatedToggleable;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.component.IFixedComponentProxy;
import com.lukflug.panelstudio.component.IScrollSize;
import com.lukflug.panelstudio.theme.IResizeBorderRenderer;
import com.lukflug.panelstudio.theme.RendererTuple;

public class ResizableComponent<T extends IFixedComponent> implements IFixedComponentProxy<T> {
	protected T component;
	protected IResizeBorderRenderer renderer;
	protected Supplier<Dimension> sizeGetter;
	protected Consumer<Dimension> sizeSetter;
	protected boolean resizing[]={false,false,false,false};
	protected Point attachPoint;
	protected Rectangle attachRect;
	
	public ResizableComponent (T component, IResizeBorderRenderer renderer, Supplier<Dimension> sizeGetter, Consumer<Dimension> sizeSetter) {
		this.component=component;
		this.renderer=renderer;
		this.sizeGetter=sizeGetter;
		this.sizeSetter=sizeSetter;
	}
	
	@Override
	public void render (Context context) {
		IFixedComponentProxy.super.render(context);
		renderer.drawBorder(context,context.hasFocus());
	}
	
	@Override
	public void handleButton (Context context, int button) {
		IFixedComponentProxy.super.handleButton(context,button);
		if (context.isClicked()) {
			attachPoint=context.getInterface().getMouse();
			attachRect=new Rectangle(getComponent().getPosition(context.getInterface()),sizeGetter.get());
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
		return sizeGetter.get().height+2*renderer.getBorder();
	}
	
	@Override
	public Context getContext (Context context) {
		if (resizing[0]) {
			Point p=getComponent().getPosition(context.getInterface());
			getComponent().setPosition(context.getInterface(),new Point(p.x,p.y+context.getInterface().getMouse().y-attachPoint.y));
			sizeSetter.accept(new Dimension(attachRect.width,attachRect.height-context.getInterface().getMouse().y+attachPoint.y));
		} else if (resizing[1]) {
			sizeSetter.accept(new Dimension(attachRect.width,attachRect.height+context.getInterface().getMouse().y-attachPoint.y));
		}
		if (resizing[2]) {
			Point p=getComponent().getPosition(context.getInterface());
			getComponent().setPosition(context.getInterface(),new Point(p.x+context.getInterface().getMouse().x-attachPoint.x,p.y));
			sizeSetter.accept(new Dimension(attachRect.width-context.getInterface().getMouse().x+attachPoint.x,attachRect.height));
		} else if (resizing[3]) {
			sizeSetter.accept(new Dimension(attachRect.width+context.getInterface().getMouse().x-attachPoint.x,attachRect.height));
		}
		Point p=context.getPos();
		p.translate(renderer.getBorder(),renderer.getBorder());
		return new Context(context,context.getSize().width-2*renderer.getBorder(),p,true,true);
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
		return sizeGetter.get().width+2*renderer.getBorder();
	}
	
	@Override
	public T getComponent() {
		return component;
	}
	
	public static <S extends IComponent,T extends IComponent,U> IFixedComponent createResizableComponent (S title, T content, Supplier<U> state, AnimatedToggleable open, RendererTuple<U> renderer, IResizeBorderRenderer resizeRenderer, BiFunction<Supplier<Dimension>,Consumer<Dimension>,IScrollSize> scrollSize, Point position, int width, int height, boolean savesState, String configName, boolean resizable) {
		Supplier<Dimension> sizeGetter=null;
		Consumer<Dimension> sizeSetter=null;
		if (resizable) {
			Dimension dimension=new Dimension(width,height);
			sizeGetter=()->new Dimension(dimension);
			sizeSetter=newDim->{
				dimension.width=newDim.width;
				dimension.height=newDim.height;
			};
		}
		IFixedComponent draggable=ClosableComponent.createDraggableComponent(title,content,state,open,renderer,scrollSize.apply(sizeGetter,sizeSetter),position,width,true,configName);
		if (resizable) {
			return new ResizableComponent<>(draggable,resizeRenderer,sizeGetter,sizeSetter);
		} else {
			return draggable;
		}
	}
}
