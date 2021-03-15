package com.lukflug.panelstudio.layout;

import java.awt.Point;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
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
	public <S extends IComponent,T extends IComponent> void addComponent(S title, T content, ITheme theme, int logicalLevel, int graphicalLevel, Point position, int width, Supplier<Animation> animation) {
		container.addComponent(ClosableComponent.createDraggableComponent(title,content,()->null,new SimpleToggleable(open),animation.get(),theme.getPanelRenderer(Void.class,logicalLevel,graphicalLevel),theme.getScrollBarRenderer(Void.class,logicalLevel,graphicalLevel),theme.getEmptySpaceRenderer(Void.class,logicalLevel,graphicalLevel),this::getScrollHeight,this::getComponentWidth,position,width,true));
	}

	@Override
	public void addPopup(IFixedComponent popup) {
		container.addComponent(popup);
	}
	
	protected int getScrollHeight (Context context, int componentHeight) {
		return componentHeight;
	}
	
	protected int getComponentWidth (Context context) {
		return context.getSize().width;
	}
}
