package com.lukflug.panelstudio.layout;

import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.theme.ITheme;

public class PanelAdder implements IComponentAdder {
	/*protected final IContainer<? super DraggableContainer> container;
	protected final IToggleable toggleable;
	protected final Animation animation;
	
	public PanelAdder (IContainer<? super DraggableContainer> container, IToggleable toggleable, Animation animation) {
		this.container=container;
		this.toggleable=toggleable;
		this.animation=animation;
	}*/
	
	@Override
	public void addComponent(IComponent title, IComponent content, ITheme theme) {
		//container.addComponent(new DraggableContainer(title,(VerticalContainer)content,()->null,toggleable,animation,theme.getPanelRenderer(Void.class,0),theme.getScrollBarRenderer(Void.class,0),theme.getEmptySpaceRenderer(Void.class,0),height->height,width->width,null,0,false));
	}
}
