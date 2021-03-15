package com.lukflug.panelstudio.layout;

import java.awt.Point;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.container.IContainer;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.widget.Button;
import com.lukflug.panelstudio.widget.ClosableComponent;

public class StackedPanelAdder implements IComponentAdder {
	protected final IContainer<? super IFixedComponent> container;
	protected VerticalContainer content;
	protected final ChildUtil util;
	
	public StackedPanelAdder (IContainer<? super IFixedComponent> container, ILabeled label, ITheme theme, Point position, int width, Supplier<Animation> animation, IPopupPositioner popupPos) {
		this.container=container;
		content=new VerticalContainer(label,theme.getContainerRenderer(-1,-1,true));
		container.addComponent(ClosableComponent.createDraggableComponent(new Button(label,theme.getButtonRenderer(Void.class,-1,-1,true)),content,()->null,new SimpleToggleable(true),animation.get(),theme.getPanelRenderer(Void.class,-1,-1),theme.getScrollBarRenderer(Void.class,-1,-1),theme.getEmptySpaceRenderer(Void.class,-1,-1),this::getScrollHeight,this::getComponentWidth,position,width,false));
		util=new ChildUtil(width,animation,popupPos,this::getScrollHeight);
	}
	
	@Override
	public <S extends IComponent,T extends IComponent> void addComponent(S title, T content, ITheme theme, int logicalLevel, int graphicalLevel, Point position, int width, Supplier<Animation> animation) {
		this.content.addComponent(new ClosableComponent<S,T>(title,content,()->null,new SimpleToggleable(false),animation.get(),theme.getPanelRenderer(Void.class,logicalLevel,graphicalLevel)));
		//util.addContainer(title, title, null, null, null, null, null, theme, logicalLevel, graphicalLevel, null);
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
