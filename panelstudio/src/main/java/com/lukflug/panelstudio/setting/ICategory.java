package com.lukflug.panelstudio.setting;

import java.util.stream.Stream;

/**
 * Interface representing a module category.
 * @author lukflug
 */
public interface ICategory extends ILabeled {
	/**
	 * Get modules in category.
	 * @return stream of modules
	 */
	public Stream<IModule> getModules();
}
