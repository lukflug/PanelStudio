package com.lukflug.panelstudio.setting;

public interface IStringSetting extends ISetting<String> {
	public String getValue();
	
	public void setValue (String string);
	
	@Override
	public default String getSettingState() {
		return getValue();
	}
	
	@Override
	public default Class<String> getSettingClass() {
		return String.class;
	}
}
