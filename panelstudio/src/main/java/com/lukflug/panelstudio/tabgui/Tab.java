package com.lukflug.panelstudio.tabgui;

import java.util.function.IntPredicate;
import java.util.stream.Collectors;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.setting.ICategory;

/**
 * A category tab.
 * No children, boolean items representing module that can be enabled or disabled.
 * @author lukflug
 */
public class Tab extends TabItem<IToggleable,Boolean> {
	/**
	 * Constructor.
	 * @param category the category to be used
	 * @param renderer the renderer for this componet
	 * @param animation the animation for the tab state
	 * @param up the scancode predicate for moving up an item
	 * @param down the scancode predicate for moving down an item
	 * @param enter the scancode predicate for selecting an item
	 */
	public Tab (ICategory category, ITabGUIRenderer<Boolean> renderer, Animation animation, IntPredicate up, IntPredicate down, IntPredicate enter) {
		super(category,renderer,animation,up,down,enter,key->false);
		contents=category.getModules().filter(module->module.isEnabled()!=null).map(module->new ContentItem<IToggleable,Boolean>(module.getDisplayName(),module.isEnabled())).collect(Collectors.toList());
	}

	@Override
	protected void handleSelect (Context context) {
		contents.get((int)tabState.getTarget()).content.toggle();
	}

	@Override
	protected void handleExit (Context context) {
	}
}
