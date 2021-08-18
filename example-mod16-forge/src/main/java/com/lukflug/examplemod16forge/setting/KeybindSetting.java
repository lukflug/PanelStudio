package com.lukflug.examplemod16forge.setting;

import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.setting.IKeybindSetting;

import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.TranslationTextComponent;

public class KeybindSetting extends Setting<Integer> implements IKeybindSetting {
	public KeybindSetting (String displayName, String configName, String description, IBoolean visible, Integer value) {
		super(displayName,configName,description,visible,value);
	}

	@Override
	public int getKey() {
		return getValue();
	}

	@Override
	public void setKey (int key) {
		setValue(key);
	}

	@Override
	public String getKeyName() {
		String translationKey=InputMappings.Type.KEYSYM.getOrMakeInput(getValue()).getTranslationKey();
		String translation=new TranslationTextComponent(translationKey).getString();
		if (!translation.equals(translationKey)) return translation;
		return InputMappings.Type.KEYSYM.getOrMakeInput(getValue()).func_237520_d_().getString();
	}
}
