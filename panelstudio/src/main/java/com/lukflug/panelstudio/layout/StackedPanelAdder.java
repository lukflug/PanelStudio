package com.lukflug.panelstudio.layout;

import java.awt.Point;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.AnimatedToggleable;
import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.component.IResizable;
import com.lukflug.panelstudio.component.IScrollSize;
import com.lukflug.panelstudio.container.IContainer;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.layout.ChildUtil.ChildMode;
import com.lukflug.panelstudio.popup.IPopupPositioner;
import com.lukflug.panelstudio.popup.PopupTuple;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.setting.Labeled;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.theme.RendererTuple;
import com.lukflug.panelstudio.theme.ThemeTuple;
import com.lukflug.panelstudio.widget.Button;
import com.lukflug.panelstudio.widget.ResizableComponent;

public class StackedPanelAdder implements IComponentAdder,IScrollSize {
	protected IContainer<? super IFixedComponent> container;
	protected ChildMode mode;
	protected VerticalContainer content;
	protected ChildUtil util;
	protected IBoolean isVisible;
	
	public StackedPanelAdder (IContainer<? super IFixedComponent> container, ILabeled label, ITheme theme, Point position, int width, Supplier<Animation> animation, ChildMode mode, IPopupPositioner popupPos, IBoolean isVisible, String configName) {
		this.container=container;
		this.mode=mode;
		this.isVisible=isVisible;
		content=new VerticalContainer(label,theme.getContainerRenderer(-1,-1,true));
		IResizable size=getResizable(width);
		IScrollSize scrollSize=getScrollSize(size);
		container.addComponent(ResizableComponent.createResizableComponent(new Button<Void>(label,()->null,theme.getButtonRenderer(Void.class,-1,-1,true)),content,()->null,new AnimatedToggleable(new SimpleToggleable(true),animation.get()),new RendererTuple<Void>(Void.class,new ThemeTuple(theme,-1,-1)),theme.getResizeRenderer(),size,scrollSize,position,width,true,configName),isVisible);
		util=new ChildUtil(width,animation,new PopupTuple(popupPos,false,this));
	}
	
	@Override
	public <S extends IComponent,T extends IComponent> void addComponent(S title, T content, ThemeTuple theme, Point position, int width, Supplier<Animation> animation) {
		util.addContainer(new Labeled(content.getTitle(),null,()->content.isVisible()),title,content,()->null,Void.class,this.content,this,theme,mode);
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
