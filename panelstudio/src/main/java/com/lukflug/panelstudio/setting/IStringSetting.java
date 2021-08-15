package com.lukflug.panelstudio.setting;

/**
 * Setting representing a text.
 * @author lukflug
 */
public interface IStringSetting extends ISetting<String> {
	/**
	 * Get current string value.
	 * @return the current text
	 */
	public String getValue();
	
	/**
	 * Set the string value.
	 * @param string new text
	 */
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
