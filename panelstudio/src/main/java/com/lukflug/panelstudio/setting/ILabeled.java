package com.lukflug.panelstudio.setting;

/**
 * Represent object with label and description.
 * @author lukflug
 */
public interface ILabeled {
	/**
	 * Get display name of the object.
	 * @return the display name
	 */
	public String getDisplayName();
	
	/**
	 * Get object description.
	 * @return the object description
	 */
	public String getDescription();
}
