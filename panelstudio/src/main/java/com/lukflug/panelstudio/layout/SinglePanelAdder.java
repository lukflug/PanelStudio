package com.lukflug.panelstudio.layout;

import java.awt.Dimension;
import java.awt.Point;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.AnimatedToggleable;
import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.HorizontalComponent;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.component.IScrollSize;
import com.lukflug.panelstudio.container.HorizontalContainer;
import com.lukflug.panelstudio.container.IContainer;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.theme.RendererTuple;
import com.lukflug.panelstudio.theme.ThemeTuple;
import com.lukflug.panelstudio.widget.ClosableComponent;
import com.lukflug.panelstudio.widget.ResizableComponent;
import com.lukflug.panelstudio.widget.ScrollBarComponent;

public class SinglePanelAdder implements IComponentAdder,IScrollSize {
	protected IContainer<? super IFixedComponent> container;
	protected boolean resizable;
	protected IBoolean isVisible;
	protected HorizontalContainer title,content;
	
	public SinglePanelAdder (IContainer<? super IFixedComponent> container, boolean resizable, ILabeled label, ITheme theme, Point position, int width, Supplier<Animation> animation, IBoolean isVisible, String configName) {
		this.container=container;
		this.resizable=resizable;
		this.isVisible=isVisible;
		title=new HorizontalContainer(label,theme.getContainerRenderer(-1,-1,true));
		content=new HorizontalContainer(label,theme.getContainerRenderer(-1,-1,true));
		AnimatedToggleable toggle=new AnimatedToggleable(new SimpleToggleable(true),animation.get());
		RendererTuple<Void> renderer=new RendererTuple<Void>(Void.class,new ThemeTuple(theme,-1,-1));
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
		IFixedComponent draggable=ClosableComponent.createDraggableComponent(title,content,()->null,toggle,renderer,new IScrollSize(){},position,width,true,configName);
		if (resizable) {
			container.addComponent(new ResizableComponent<>(draggable,theme.getResizeRenderer(),sizeGetter,sizeSetter),isVisible);
		} else {
			container.addComponent(draggable,isVisible);
		}
	}
	
	@Override
	public <S extends IComponent,T extends IComponent> void addComponent(S title, T content, ThemeTuple theme, Point position, int width, Supplier<Animation> animation) {
		this.title.addComponent(new HorizontalComponent<S>(title,0,1));
		this.content.addComponent(new HorizontalComponent<>(new ScrollBarComponent<Void,T>(content,theme.getScrollBarRenderer(Void.class),theme.getEmptySpaceRenderer(Void.class,false),theme.getEmptySpaceRenderer(Void.class,true)) {
			@Override
			public int getScrollHeight(Context context, int componentHeight) {
				return SinglePanelAdder.this.getScrollHeight(context,componentHeight);
			}

			@Override
			public int getComponentWidth(Context context) {
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
		container.addComponent(popup,isVisible);
	}
	
	protected IScrollSize getScrollSize (Supplier<Dimension> sizeGetter, Consumer<Dimension> sizeSetter) {
		return new IScrollSize() {};
	}
	
	protected int getDefaultHeight() {
		return 0;
	}
}
