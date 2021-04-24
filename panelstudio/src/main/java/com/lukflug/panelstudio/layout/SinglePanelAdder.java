package com.lukflug.panelstudio.layout;

import java.awt.Point;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.AnimatedToggleable;
import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.HorizontalComponent;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.component.IResizable;
import com.lukflug.panelstudio.component.IScrollSize;
import com.lukflug.panelstudio.container.HorizontalContainer;
import com.lukflug.panelstudio.container.IContainer;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.theme.RendererTuple;
import com.lukflug.panelstudio.theme.ThemeTuple;
import com.lukflug.panelstudio.widget.ResizableComponent;
import com.lukflug.panelstudio.widget.ScrollBarComponent;

public class SinglePanelAdder implements IComponentAdder {
	protected IContainer<? super IFixedComponent> container;
	protected IBoolean isVisible;
	protected HorizontalContainer title,content;
	protected final IScrollSize size;
	
	public SinglePanelAdder (IContainer<? super IFixedComponent> container, ILabeled label, ITheme theme, Point position, int width, Supplier<Animation> animation, IBoolean isVisible, String configName) {
		this.container=container;
		this.isVisible=isVisible;
		title=new HorizontalContainer(label,theme.getContainerRenderer(-1,-1,true));
		content=new HorizontalContainer(label,theme.getContainerRenderer(-1,-1,true));
		AnimatedToggleable toggle=new AnimatedToggleable(new SimpleToggleable(true),animation.get());
		RendererTuple<Void> renderer=new RendererTuple<Void>(Void.class,new ThemeTuple(theme,-1,-1));
		IResizable size=getResizable(width);
		this.size=getScrollSize(size);
		container.addComponent(ResizableComponent.createResizableComponent(title,content,()->null,toggle,renderer,theme.getResizeRenderer(),size,new IScrollSize() {
			@Override
			public int getComponentWidth(Context context) {
				return SinglePanelAdder.this.size.getComponentWidth(context);
			}
		},position,width,true,configName),isVisible);
	}
	
	@Override
	public <S extends IComponent,T extends IComponent> void addComponent(S title, T content, ThemeTuple theme, Point position, int width, Supplier<Animation> animation) {
		this.title.addComponent(new HorizontalComponent<S>(title,0,1));
		this.content.addComponent(new HorizontalComponent<>(new ScrollBarComponent<Void,T>(content,theme.getScrollBarRenderer(Void.class),theme.getEmptySpaceRenderer(Void.class,false),theme.getEmptySpaceRenderer(Void.class,true)) {
			@Override
			public int getScrollHeight(Context context, int componentHeight) {
				return size.getScrollHeight(context,componentHeight);
			}

			@Override
			protected Void getState() {
				return null;
			}
		},0,1));
	}

	@Override
	public void addPopup(IFixedComponent popup) {
		container.addComponent(popup,isVisible);
	}
	
	protected IResizable getResizable (int width) {
		return null;
	}
	
	protected IScrollSize getScrollSize (IResizable size) {
		return new IScrollSize(){};
	}
}
