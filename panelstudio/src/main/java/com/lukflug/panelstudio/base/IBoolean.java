package com.lukflug.panelstudio.base;

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Interface representing something returning a boolean.
 * @author lukflug
 */
@FunctionalInterface
public interface IBoolean extends BooleanSupplier,Supplier<Boolean>,Predicate<Void> {
	/**
	 * Get the value of the boolean.
	 * @return a boolean value
	 */
	public boolean isOn();
	
	@Override
	public default boolean getAsBoolean() {
		return isOn();
	}
	
	@Override
	public default Boolean get() {
		return isOn();
	}
	
	@Override
	public default boolean test (Void t) {
		return isOn();
	}
}
