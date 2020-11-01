package com.lukflug.panelstudio.settings;

/**
 * Interface representing a keybind.
 * @author lukflug
 */
public interface KeybindSetting {
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
}
