package com.lukflug.panelstudio.layout;

import java.awt.Point;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.container.IContainer;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.widget.ClosableComponent;

public class PanelAdder implements IComponentAdder {
	protected final IContainer<? super IFixedComponent> container;
	protected final boolean open;
	
	public PanelAdder (IContainer<? super IFixedComponent> container, boolean open) {
		this.container=container;
		this.open=open;
	}
	
	@Override
	public <S extends IComponent,T extends IComponent> void addComponent(S title, T content, ITheme theme, int level, Point position, int width, Supplier<Animation> animation) {
		container.addComponent(ClosableComponent.createDraggableComponent(title,content,()->null,new SimpleToggleable(open),animation.get(),theme.getPanelRenderer(Void.class,level),theme.getScrollBarRenderer(Void.class,level),theme.getEmptySpaceRenderer(Void.class,level),this::getScrollHeight,this::getComponentWidth,position,width,true));
	}
	
	protected int getScrollHeight (int componentHeight) {
		return componentHeight;
	}
	
	protected int getComponentWidth (int scrollWidth) {
		return scrollWidth;
	}
}
