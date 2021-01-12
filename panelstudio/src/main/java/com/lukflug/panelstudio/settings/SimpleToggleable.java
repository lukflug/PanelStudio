package com.lukflug.panelstudio.settings;

/**
 * Basic implementation of {@link Toggleable}, where the boolean is a field.
 * @author lukflug
 */
public class SimpleToggleable implements Toggleable {
	/**
	 * Field storing the state of the {@link Toggleable}.
	 */
	private boolean value;
	
	/**
	 * Constructor.
	 * @param value intial sate
	 */
	public SimpleToggleable (boolean value) {
		this.value=value;
	}

	/**
	 * Invert the boolean.
	 */
	@Override
	public void toggle() {
		value=!value;
	}

	/**
	 * Returns the boolean.
	 */
	@Override
	public boolean isOn() {
		return value;
	}

}
