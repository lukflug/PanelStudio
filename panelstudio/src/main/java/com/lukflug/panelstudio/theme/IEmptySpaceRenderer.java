package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.Context;

/**
 * Interface to abstract the rendering of a blank space.
 * @author lukflug
 */
@FunctionalInterface
public interface IEmptySpaceRenderer {
	/**
	 * This function does
	 * @param context
	 * @param focus
	 */
	public void renderSpace (Context context, boolean focus);
}
