package com.lukflug.panelstudio.tabgui;

import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.component.ComponentBase;
import com.lukflug.panelstudio.setting.ILabeled;

/**
 * Class representing a tab in the TabGUI.
 * @author lukflug
 * @param <S> the item state supplier type
 * @param <T> the item state type
 */
public abstract class TabItem<S extends Supplier<T>,T> extends ComponentBase {
	/**
	 * The renderer to be used.
	 */
	protected ITabGUIRenderer<T> renderer;
	/**
	 * The content of the tab.
	 */
	protected List<ContentItem<S,T>> contents;
	/**
	 * The animation for the tab state.
	 */
	protected Animation tabState;
	/**
	 * The scancode predicate for moving up an item.
	 */
	protected final IntPredicate up;
	/**
	 * The scancode predicate for moving down an item.
	 */
	protected final IntPredicate down;
	/**
	 * The scancode predicate for selecting an item.
	 */
	protected final IntPredicate enter;
	/**
	 * The scancode predicate for exiting the tab.
	 */
	protected final IntPredicate exit;
	
	/**
	 * Constructor.
	 * @param label the label for this component
	 * @param renderer the renderer for this componet
	 * @param animation the animation for the tab state
	 * @param up the scancode predicate for moving up an item
	 * @param down the scancode predicate for moving down an item
	 * @param enter the scancode predicate for selecting an item
	 * @param exit the scancode predicate for exiting the tab
	 */
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
	
	/**
	 * Whether a tab has any active child components.
	 * @return true if any children are open
	 */
	protected boolean hasChildren() {
		return false;
	}
	
	/**
	 * Handle an item being selected.
	 * @param context the current context
	 */
	protected abstract void handleSelect (Context context);
	
	/**
	 * Handle a child being exited.
	 * @param context the current context
	 */
	protected abstract void handleExit (Context context);
	
	
	/**
	 * Class representing a tab child item.
	 * @author lukflug
	 * @param <S> the item state supplier type
	 * @param <T> the item state type
	 */
	protected static final class ContentItem<S extends Supplier<T>,T> {
		/**
		 * The item name.
		 */
		public final String name;
		/**
		 * The item state supplier.
		 */
		public final S content;
		
		/**
		 * Constructor.
		 * @param name the item name
		 * @param content the item state supplier
		 */
		public ContentItem (String name, S content) {
			this.name=name;
			this.content=content;
		}
	}
}
