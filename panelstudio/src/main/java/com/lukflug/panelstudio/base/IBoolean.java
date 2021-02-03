package com.lukflug.panelstudio.base;

/**
 * Interface representing something returning a boolean.
 * @author lukflug
 */
@FunctionalInterface
public interface IBoolean {
	/**
	 * Get the value of the boolean.
	 * @return a boolean value
	 */
	public boolean isOn();
}
