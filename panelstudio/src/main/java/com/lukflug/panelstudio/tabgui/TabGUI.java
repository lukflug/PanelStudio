package com.lukflug.panelstudio.tabgui;

import java.awt.Point;
import java.util.function.IntPredicate;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.FixedComponent;
import com.lukflug.panelstudio.container.IContainer;
import com.lukflug.panelstudio.popup.IPopupPositioner;
import com.lukflug.panelstudio.setting.ICategory;
import com.lukflug.panelstudio.setting.IClient;
import com.lukflug.panelstudio.setting.ILabeled;

public class TabGUI extends TabItem<TabGUI.ChildTab,Void> {
	protected int width;
	protected IContainer<? super FixedComponent<Tab>> container;
	protected IPopupPositioner positioner;
	protected ITabGUIRenderer<Boolean> childRenderer;
	
	public TabGUI(IClient client, ILabeled label, int width, IContainer<? super FixedComponent<Tab>> container, IPopupPositioner positioner, ITabGUITheme theme, Supplier<Animation> animation, IntPredicate up, IntPredicate down, IntPredicate enter, IntPredicate exit) {
		super(label,theme.getParentRenderer(),animation.get(),up,down,enter,exit);
		this.width=width;
		this.container=container;
		this.positioner=positioner;
		childRenderer=theme.getChildRenderer();
	}
	
	public FixedComponent<TabGUI> getWrappedComponent (Point position) {
		return new FixedComponent<TabGUI>(this,position,width,null,false,description);
	}

	@Override
	public void handleSelect (Context context) {
		ChildTab tab=contents.get((int)tabState.getTarget()).content;
		tab.tab.setPosition(context.getInterface(),null,context.getRect(),null);
		if (!tab.visible.isOn()) tab.visible.toggle();
	}

	@Override
	public void handleExit (Context context) {
		ChildTab tab=contents.get((int)tabState.getTarget()).content;
		if (tab.visible.isOn()) tab.visible.toggle();
	}
	
	
	protected class ChildTab implements Supplier<Void> {
		public final FixedComponent<Tab> tab;
		public final IToggleable visible;
		
		public ChildTab (ICategory category) {
			tab=new FixedComponent<Tab>(new Tab(category,childRenderer,tabState,up,down,enter),new Point(0,0),width,null,false,category.getDisplayName());
			visible=new SimpleToggleable(false);
			container.addComponent(tab,visible);
		}
		
		@Override
		public Void get() {
			return null;
		}
	}
}
