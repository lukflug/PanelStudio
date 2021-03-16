package com.lukflug.panelstudio.layout;

import java.awt.Point;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.HorizontalComponent;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.container.HorizontalContainer;
import com.lukflug.panelstudio.container.IContainer;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.widget.ClosableComponent;
import com.lukflug.panelstudio.widget.ScrollBarComponent;

public class SinglePanelAdder implements IComponentAdder {
	protected final IContainer<? super IFixedComponent> container;
	protected final HorizontalContainer title,content;
	
	public SinglePanelAdder (IContainer<? super IFixedComponent> container, ILabeled label, ITheme theme, Point position, int width, Supplier<Animation> animation) {
		this.container=container;
		title=new HorizontalContainer(label,theme.getContainerRenderer(-1,-1,true));
		content=new HorizontalContainer(label,theme.getContainerRenderer(-1,-1,true));
		container.addComponent(ClosableComponent.createDraggableComponent(title,content,()->null,new SimpleToggleable(true),animation.get(),theme.getPanelRenderer(Void.class,-1,-1),theme.getScrollBarRenderer(Void.class,-1,-1),theme.getEmptySpaceRenderer(Void.class,-1,-1),(context,height)->height,context->context.getSize().width,position,width,false));
	}
	
	@Override
	public <S extends IComponent,T extends IComponent> void addComponent(S title, T content, ITheme theme, int logicalLevel, int graphicalLevel, Point position, int width, Supplier<Animation> animation) {
		this.title.addComponent(new HorizontalComponent<S>(title,0,1));
		this.content.addComponent(new HorizontalComponent<ScrollBarComponent<Void,T>>(new ScrollBarComponent<Void,T>(content,theme.getScrollBarRenderer(Void.class,logicalLevel,graphicalLevel),theme.getEmptySpaceRenderer(Void.class,logicalLevel,graphicalLevel)) {
			@Override
			protected int getScrollHeight(Context context, int componentHeight) {
				return SinglePanelAdder.this.getScrollHeight(context,componentHeight);
			}

			@Override
			protected int getComponentWidth(Context context) {
				return SinglePanelAdder.this.getComponentWidth(context);
			}

			@Override
			protected Void getState() {
				return null;
			}
		},0,1));
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
