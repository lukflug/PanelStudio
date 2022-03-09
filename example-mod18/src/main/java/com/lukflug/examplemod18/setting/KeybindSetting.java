package com.lukflug.examplemod18.setting;

import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.setting.IKeybindSetting;

import net.minecraft.client.util.InputUtil;
import net.minecraft.text.TranslatableText;

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
		String translationKey=InputUtil.Type.KEYSYM.createFromCode(getKey()).getTranslationKey();
		String translation=new TranslatableText(translationKey).getString();
		if (!translation.equals(translationKey)) return translation;
		return InputUtil.Type.KEYSYM.createFromCode(getKey()).getLocalizedText().getString();
	}
}
