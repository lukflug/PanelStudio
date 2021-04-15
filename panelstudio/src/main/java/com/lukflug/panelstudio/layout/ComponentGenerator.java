package com.lukflug.panelstudio.layout;

import java.util.function.IntPredicate;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.setting.IKeybindSetting;
import com.lukflug.panelstudio.theme.ThemeTuple;
import com.lukflug.panelstudio.widget.KeybindComponent;

public class ComponentGenerator implements IComponentGenerator {
	protected final IntPredicate deleteKey;
	
	public ComponentGenerator (IntPredicate deleteKey) {
		this.deleteKey=deleteKey;
	}
	
	@Override
	public IComponent getKeybindComponent (IKeybindSetting setting, Supplier<Animation> animation, ThemeTuple theme, boolean isContainer) {
		return new KeybindComponent(setting,theme.getKeybindRenderer(isContainer)) {
			@Override
			public int transformKey (int scancode) {
				return deleteKey.test(scancode)?0:scancode;
			}
		};
	}
}
