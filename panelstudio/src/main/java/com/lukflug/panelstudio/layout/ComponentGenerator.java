package com.lukflug.panelstudio.layout;

import java.util.function.IntPredicate;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.setting.IKeybindSetting;
import com.lukflug.panelstudio.setting.IStringSetting;
import com.lukflug.panelstudio.theme.ThemeTuple;
import com.lukflug.panelstudio.widget.ITextFieldKeys;
import com.lukflug.panelstudio.widget.KeybindComponent;
import com.lukflug.panelstudio.widget.TextField;

public class ComponentGenerator implements IComponentGenerator {
	protected final IntPredicate keybindKey,charFilter;
	protected final ITextFieldKeys keys;
	
	public ComponentGenerator (IntPredicate keybindKey, IntPredicate charFilter, ITextFieldKeys keys) {
		this.keybindKey=keybindKey;
		this.charFilter=charFilter;
		this.keys=keys;
	}
	
	@Override
	public IComponent getKeybindComponent (IKeybindSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
		return new KeybindComponent(setting,theme.getKeybindRenderer(isContainer)) {
			@Override
			public int transformKey (int scancode) {
				return keybindKey.test(scancode)?0:scancode;
			}
		};
	}
	
	@Override
	public IComponent getStringComponent (IStringSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
		return new TextField(setting,keys,0,new SimpleToggleable(false),theme.getTextRenderer(false,isContainer)) {
			@Override
			public boolean allowCharacter(char character) {
				return charFilter.test(character);
			}
		};
	}
}
