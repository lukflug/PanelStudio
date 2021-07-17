package com.lukflug.panelstudio.tabgui;

import java.util.function.IntPredicate;
import java.util.stream.Collectors;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.setting.ICategory;

public class Tab extends TabItem<IToggleable,Boolean> {
	public Tab (ICategory category, ITabGUIRenderer<Boolean> renderer, Animation animation, IntPredicate up, IntPredicate down, IntPredicate enter) {
		super(category,renderer,animation,up,down,enter,key->false);
		contents=category.getModules().map(module->new ContentItem<IToggleable,Boolean>(module.getDisplayName(),module.isEnabled())).collect(Collectors.toList());
	}

	@Override
	protected void handleSelect (Context context) {
		contents.get((int)tabState.getTarget()).content.toggle();
	}

	@Override
	protected void handleExit (Context context) {
	}
}
