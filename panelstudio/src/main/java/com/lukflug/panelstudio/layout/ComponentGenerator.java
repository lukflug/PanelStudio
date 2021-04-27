package com.lukflug.panelstudio.layout;

import java.util.function.IntPredicate;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.setting.IKeybindSetting;
import com.lukflug.panelstudio.setting.IStringSetting;
import com.lukflug.panelstudio.theme.ThemeTuple;
import com.lukflug.panelstudio.widget.KeybindComponent;
import com.lukflug.panelstudio.widget.TextField;

public class ComponentGenerator implements IComponentGenerator {
	protected final IntPredicate keybindKey,charFilter,backspaceKey,deleteKey,insertKey,leftKey,rightKey,homeKey,endKey;
	
	public ComponentGenerator (IntPredicate keybindKey, IntPredicate charFilter, IntPredicate backspaceKey, IntPredicate deleteKey, IntPredicate insertKey, IntPredicate leftKey, IntPredicate rightKey, IntPredicate homeKey, IntPredicate endKey) {
		this.keybindKey=keybindKey;
		this.charFilter=charFilter;
		this.backspaceKey=backspaceKey;
		this.deleteKey=deleteKey;
		this.insertKey=insertKey;
		this.leftKey=leftKey;
		this.rightKey=rightKey;
		this.homeKey=homeKey;
		this.endKey=endKey;
	}
	
	@Override
	public IComponent getKeybindComponent (IKeybindSetting setting, Supplier<Animation> animation, ThemeTuple theme, int colorLevel, boolean isContainer) {
		return new KeybindComponent(setting,theme.getKeybindRenderer(isContainer)) {
			@Override
			public int transformKey (int scancode) {
				return keybindKey.test(scancode)?0:scancode;
			}
		};
	}
	
	@Override
	public IComponent getStringComponent (IStringSetting setting, Supplier<Animation> animation, ThemeTuple theme, int colorLevel, boolean isContainer) {
		return new TextField(setting,0,new SimpleToggleable(false),theme.getTextRenderer(isContainer)) {
			@Override
			public boolean allowCharacter(char character) {
				return charFilter.test(character);
			}

			@Override
			public boolean isBackspaceKey(int scancode) {
				return backspaceKey.test(scancode);
			}

			@Override
			public boolean isDeleteKey(int scancode) {
				return deleteKey.test(scancode);
			}

			@Override
			public boolean isInsertKey(int scancode) {
				return insertKey.test(scancode);
			}

			@Override
			public boolean isLeftKey(int scancode) {
				return leftKey.test(scancode);
			}

			@Override
			public boolean isRightKey(int scancode) {
				return rightKey.test(scancode);
			}

			@Override
			public boolean isHomeKey(int scancode) {
				return homeKey.test(scancode);
			}

			@Override
			public boolean isEndKey(int scancode) {
				return endKey.test(scancode);
			}
		};
	}
}
