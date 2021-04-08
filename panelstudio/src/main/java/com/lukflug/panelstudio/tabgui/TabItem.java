package com.lukflug.panelstudio.tabgui;

import java.util.function.IntPredicate;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.component.ComponentBase;
import com.lukflug.panelstudio.setting.ILabeled;

public abstract class TabItem<S extends Supplier<T>,T> extends ComponentBase {
	protected TabGUIRenderer<T> renderer;
	protected ContentItem<S,T>[] contents;
	protected Animation tabState;
	protected IntPredicate up,down,enter,exit;
	
	public TabItem (ILabeled label, TabGUIRenderer<T> renderer, Animation animation, IntPredicate up, IntPredicate down, IntPredicate enter, IntPredicate exit) {
		super(label);
		this.renderer=renderer;
		tabState=animation;
		this.up=up;
		this.down=down;
		this.enter=enter;
		this.exit=exit;
	}
	
	@Override
	public void render (Context context) {
		super.render(context);
		renderer.renderTab(context,contents.length,tabState.getValue());
		for (int i=0;i<contents.length;i++) renderer.renderItem(context,contents.length,tabState.getValue(),i,contents[i].name,contents[i].content.get());
	}
	
	@Override
	public void handleKey (Context context, int key) {
		super.handleKey(context,key);
		if (up.test(key)) {
			int nextState=(int)tabState.getTarget()+1;
			if (nextState>=contents.length) nextState=0;
			tabState.setValue(nextState);
		} else if (down.test(key)) {
			int nextState=(int)tabState.getTarget()-1;
			if (nextState<contents.length) nextState=contents.length-1;
			tabState.setValue(nextState);
		} else if (enter.test(key)) handleSelect();
		else if (exit.test(key)) handleExit();
	}

	@Override
	public void releaseFocus() {
	}

	@Override
	protected int getHeight() {
		return renderer.getTabHeight(contents.length);
	}
	
	public abstract void handleSelect();
	
	public abstract void handleExit();
	
	
	protected static final class ContentItem<S extends Supplier<T>,T> {
		public final String name;
		public final S content;
		
		public ContentItem (String name, S content) {
			this.name=name;
			this.content=content;
		}
	}
}
