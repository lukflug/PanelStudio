package com.lukflug.panelstudio.setting;

import java.util.stream.Stream;

/**
 * Interface representing all modules within a client.
 * @author lukflug
 */
@FunctionalInterface
public interface IClient {
	/**
	 * Get module categories of client.
	 * @return stream of categories
	 */
	public Stream<ICategory> getCategories();
}
