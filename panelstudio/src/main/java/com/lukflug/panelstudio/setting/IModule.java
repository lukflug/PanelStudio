package com.lukflug.panelstudio.setting;

import java.util.stream.Stream;

import com.lukflug.panelstudio.base.IToggleable;

/**
 * Interface representing a module.
 * @author lukflug
 */
public interface IModule extends ILabeled {
	/**
	 * Returns a toggleable indicating whether the module is enabled, which may be null.
	 * @return whether the module is enabled
	 */
	public IToggleable isEnabled();
	
	/**
	 * Get list of settings in module.
	 * @return stream of settings
	 */
	public Stream<ISetting<?>> getSettings();
}
