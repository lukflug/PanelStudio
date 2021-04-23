package com.lukflug.panelstudio.layout;

import java.awt.Dimension;
import java.awt.Point;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import com.lukflug.panelstudio.base.AnimatedToggleable;
import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.component.IScrollSize;
import com.lukflug.panelstudio.container.IContainer;
import com.lukflug.panelstudio.theme.RendererTuple;
import com.lukflug.panelstudio.theme.ThemeTuple;
import com.lukflug.panelstudio.widget.ClosableComponent;
import com.lukflug.panelstudio.widget.ResizableComponent;

public class PanelAdder implements IComponentAdder {
	protected IContainer<? super IFixedComponent> container;
	protected boolean resizable,open;
	protected IBoolean isVisible;
	protected UnaryOperator<String> configName;
	
	public PanelAdder (IContainer<? super IFixedComponent> container, boolean resizable, boolean open, IBoolean isVisible, UnaryOperator<String> configName) {
		this.container=container;
		this.resizable=resizable;
		this.open=open;
		this.isVisible=isVisible;
		this.configName=configName;
	}
	
	@Override
	public <S extends IComponent,T extends IComponent> void addComponent(S title, T content, ThemeTuple theme, Point position, int width, Supplier<Animation> animation) {
		AnimatedToggleable toggle=new AnimatedToggleable(new SimpleToggleable(open),animation.get());
		RendererTuple<Void> renderer=new RendererTuple<Void>(Void.class,theme);
		Supplier<Dimension> sizeGetter=null;
		Consumer<Dimension> sizeSetter=null;
		if (resizable) {
			Dimension dimension=new Dimension(width,getDefaultHeight());
			sizeGetter=()->new Dimension(dimension);
			sizeSetter=newDim->{
				dimension.width=newDim.width;
				dimension.height=newDim.height;
			};
		}
		IFixedComponent draggable=ClosableComponent.createDraggableComponent(title,content,()->null,toggle,renderer,getScrollSize(sizeGetter,sizeSetter),position,width,true,configName.apply(content.getTitle()));
		if (resizable) {
			container.addComponent(new ResizableComponent<>(draggable,theme.theme.getResizeRenderer(),sizeGetter,sizeSetter),isVisible);
		} else {
			container.addComponent(draggable,isVisible);
		}
	}

	@Override
	public void addPopup(IFixedComponent popup) {
		container.addComponent(popup,isVisible);
	}
	
	protected IScrollSize getScrollSize (Supplier<Dimension> sizeGetter, Consumer<Dimension> sizeSetter) {
		return new IScrollSize() {};
	}
	
	protected int getDefaultHeight() {
		return 0;
	}
}
