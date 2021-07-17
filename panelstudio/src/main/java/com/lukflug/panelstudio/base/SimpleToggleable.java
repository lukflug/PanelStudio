package com.lukflug.panelstudio.base;

/**
 * Basic implementation of {@link IToggleable}, where the boolean is a field.
 * @author lukflug
 */
public class SimpleToggleable extends ConstantToggleable {
	/**
	 * Constructor.
	 * @param value initial sate
	 */
	public SimpleToggleable (boolean value) {
		super(value);
	}

	@Override
	public void toggle() {
		value=!value;
	}
}
