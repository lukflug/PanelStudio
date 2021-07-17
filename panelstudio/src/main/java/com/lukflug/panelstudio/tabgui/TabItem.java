package com.lukflug.panelstudio.tabgui;

import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.component.ComponentBase;
import com.lukflug.panelstudio.setting.ILabeled;

public abstract class TabItem<S extends Supplier<T>,T> extends ComponentBase {
	protected ITabGUIRenderer<T> renderer;
	protected List<ContentItem<S,T>> contents;
	protected Animation tabState;
	protected final IntPredicate up,down,enter,exit;
	
	public TabItem (ILabeled label, ITabGUIRenderer<T> renderer, Animation animation, IntPredicate up, IntPredicate down, IntPredicate enter, IntPredicate exit) {
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
		renderer.renderTab(context,contents.size(),tabState.getValue());
		for (int i=0;i<contents.size();i++) {
			renderer.renderItem(context,contents.size(),tabState.getValue(),i,contents.get(i).name,contents.get(i).content.get());
		}
	}
	
	@Override
	public void handleKey (Context context, int key) {
		super.handleKey(context,key);
		if (!hasChildren()) {
			if (up.test(key)) {
				int nextState=(int)tabState.getTarget()-1;
				if (nextState<0) nextState=contents.size()-1;
				tabState.setValue(nextState);
			} else if (down.test(key)) {
				int nextState=(int)tabState.getTarget()+1;
				if (nextState>=contents.size()) nextState=0;
				tabState.setValue(nextState);
			} else if (enter.test(key)) handleSelect(context);
		}
		if (exit.test(key)) handleExit(context);
	}

	@Override
	public void releaseFocus() {
	}

	@Override
	protected int getHeight() {
		return renderer.getTabHeight(contents.size());
	}
	
	protected boolean hasChildren() {
		return false;
	}
	
	protected abstract void handleSelect (Context context);
	
	protected abstract void handleExit (Context context);
	
	
	protected static final class ContentItem<S extends Supplier<T>,T> {
		public final String name;
		public final S content;
		
		public ContentItem (String name, S content) {
			this.name=name;
			this.content=content;
		}
	}
}
