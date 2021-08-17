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
import com.lukflug.panelstudio.setting.ICategory;
import com.lukflug.panelstudio.setting.IClient;
import com.lukflug.panelstudio.setting.ILabeled;

/**
 * Class representing entire TabGUI.
 * @author lukflug
 */
public class TabGUI extends TabItem<TabGUI.ChildTab,Void> {
	/**
	 * The fixed component that contains the tab GUI.
	 */
	private final FixedComponent<TabGUI> fixedComponent;
	
	/**
	 * Construcotr.
	 * @param label the TabGUI label
	 * @param client the client interface for the TabGUI
	 * @param theme the theme to be used
	 * @param container the container that should contain the child tab pop-ups
	 * @param animation the animation supplier
	 * @param up the scancode predicate for moving up an item
	 * @param down the scancode predicate for moving down an item
	 * @param enter the scancode predicate for selecting an item
	 * @param exit the scancode predicate for exiting the tab
	 * @param position the initial TabGUI position
	 * @param configName the TabGUI config name
	 */
	public TabGUI (ILabeled label, IClient client, ITabGUITheme theme, IContainer<? super FixedComponent<Tab>> container, Supplier<Animation> animation, IntPredicate up, IntPredicate down, IntPredicate enter, IntPredicate exit, Point position, String configName) {
		super(label,theme.getParentRenderer(),animation.get(),up,down,enter,exit);
		AtomicInteger i=new AtomicInteger(0);
		contents=client.getCategories().map(category->new ContentItem<ChildTab,Void>(category.getDisplayName(),new ChildTab(category,theme,container,animation.get(),i.getAndIncrement()))).collect(Collectors.toList());
		fixedComponent=new FixedComponent<TabGUI>(this,position,theme.getTabWidth(),null,true,configName);
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
		exit();
	}
	
	@Override
	public void exit() {
		ChildTab tab=contents.get((int)tabState.getTarget()).content;
		if (tab.visible.isOn()) tab.visible.toggle();
	}
	
	
	/**
	 * The child tab class for the {@link ContentItem} supplier.
	 * @author lukflug
	 */
	protected class ChildTab implements Supplier<Void> {
		/**
		 * The fixed component that contains the tab pop-up.
		 */
		public final FixedComponent<Tab> tab;
		/**
		 * The visibility of the tab pop-up.
		 */
		public final IToggleable visible;
		
		/**
		 * Constructor.
		 * @param category the tab category
		 * @param theme the theme to be used
		 * @param container the container containing the pop-ups
		 * @param animation the animation for the tab state
		 * @param index the tab index
		 */
		public ChildTab (ICategory category, ITabGUITheme theme, IContainer<? super FixedComponent<Tab>> container, Animation animation, int index) {
			tab=new FixedComponent<Tab>(new Tab(category,theme.getChildRenderer(),animation,up,down,enter),new Point(0,0),theme.getTabWidth(),null,false,category.getDisplayName()) {
				@Override
				public Point getPosition (IInterface inter) {
					Rectangle rect=new Rectangle(fixedComponent.getPosition(inter),new Dimension(width,TabGUI.this.getHeight()));
					Dimension dim=new Dimension(width,component.getHeight());
					return theme.getPositioner().getPosition(inter,dim,renderer.getItemRect(inter,rect,contents.size(),index),rect);
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
