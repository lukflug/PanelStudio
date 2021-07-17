package com.lukflug.panelstudio.setting;

/**
 * Interface representing a keybind.
 * @author lukflug
 */
public interface IKeybindSetting extends ISetting<String> {
	/**
	 * Get the value of the keybind.
	 * @return the scancode of the key
	 */
	public int getKey();
	
	/**
	 * Set the value of the keybind.
	 * @param key the scancode of the key
	 */
    public void setKey(int key);
    
    /**
     * Get the name of the key that is binded.
     * @return name of the key
     */
	public String getKeyName();
	
	@Override
	public default String getSettingState() {
		return getKeyName();
	}
	
	@Override
	public default Class<String> getSettingClass() {
		return String.class;
	}
}
