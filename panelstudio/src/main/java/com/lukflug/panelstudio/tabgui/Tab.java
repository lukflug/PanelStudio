package com.lukflug.panelstudio.tabgui;

import java.util.function.IntPredicate;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.setting.ICategory;

public class Tab extends TabItem<IToggleable,Boolean> {
	public Tab (ICategory category, TabGUIRenderer<Boolean> renderer, Animation animation, IntPredicate up, IntPredicate down, IntPredicate enter) {
		super(category,renderer,animation,up,down,enter,key->false);
		//contents=category.getModules().map(module->new ContentItem<IToggleable,Boolean>(module.getDisplayName(),module.isEnabled())).toArray(ContentItem<IToggleable,Boolean>[]::new);
	}

	@Override
	public void handleSelect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleExit() {
		// TODO Auto-generated method stub
		
	}
}
