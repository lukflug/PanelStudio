package com.lukflug.panelstudio.tabgui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntPredicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.FixedComponent;
import com.lukflug.panelstudio.container.IContainer;
import com.lukflug.panelstudio.popup.IPopupPositioner;
import com.lukflug.panelstudio.setting.ICategory;
import com.lukflug.panelstudio.setting.IClient;
import com.lukflug.panelstudio.setting.ILabeled;

public class TabGUI extends TabItem<TabGUI.ChildTab,Void> {
	private final FixedComponent<TabGUI> fixedComponent;
	protected int width;
	protected IContainer<? super FixedComponent<Tab>> container;
	protected IPopupPositioner positioner;
	protected ITabGUIRenderer<Boolean> childRenderer;
	
	public TabGUI (ILabeled label, IClient client, ITabGUITheme theme, IContainer<? super FixedComponent<Tab>> container, Supplier<Animation> animation, IntPredicate up, IntPredicate down, IntPredicate enter, IntPredicate exit, Point position, String configName) {
		super(label,theme.getParentRenderer(),animation.get(),up,down,enter,exit);
		this.width=theme.getTabWidth();
		this.container=container;
		this.positioner=theme.getPositioner();
		childRenderer=theme.getChildRenderer();
		AtomicInteger i=new AtomicInteger(0);
		contents=client.getCategories().map(category->new ContentItem<ChildTab,Void>(category.getDisplayName(),new ChildTab(category,animation.get(),i.getAndIncrement()))).collect(Collectors.toList());
		fixedComponent=new FixedComponent<TabGUI>(this,position,width,null,true,configName);
	}
	
	public FixedComponent<TabGUI> getWrappedComponent() {
		return fixedComponent;
	}
	
	@Override
	protected boolean hasChildren() {
		for (ContentItem<ChildTab,Void> tab: contents) {
			if (tab.content.visible.isOn()) return true;
		}
		return false;
	}

	@Override
	protected void handleSelect (Context context) {
		ChildTab tab=contents.get((int)tabState.getTarget()).content;
		if (!tab.visible.isOn()) tab.visible.toggle();
	}

	@Override
	protected void handleExit (Context context) {
		ChildTab tab=contents.get((int)tabState.getTarget()).content;
		if (tab.visible.isOn()) tab.visible.toggle();
	}
	
	
	protected class ChildTab implements Supplier<Void> {
		public final FixedComponent<Tab> tab;
		public final IToggleable visible;
		
		public ChildTab (ICategory category, Animation animation, int index) {
			tab=new FixedComponent<Tab>(new Tab(category,childRenderer,animation,up,down,enter),new Point(0,0),width,null,false,category.getDisplayName()) {
				@Override
				public Point getPosition (IInterface inter) {
					Rectangle rect=new Rectangle(fixedComponent.getPosition(inter),new Dimension(width,TabGUI.this.getHeight()));
					Dimension dim=new Dimension(width,component.getHeight());
					return positioner.getPosition(inter,dim,renderer.getItemRect(inter,rect,contents.size(),index),rect);
				}
			};
			visible=new SimpleToggleable(false);
			container.addComponent(tab,visible);
		}
		
		@Override
		public Void get() {
			return null;
		}
	}
}
