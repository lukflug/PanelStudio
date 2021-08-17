package com.lukflug.examplemod8forge.setting;

import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.setting.IStringSetting;

public class StringSetting extends Setting<String> implements IStringSetting {
	public StringSetting (String displayName, String configName, String description, IBoolean visible, String value) {
		super(displayName,configName,description,visible,value);
	}
}
