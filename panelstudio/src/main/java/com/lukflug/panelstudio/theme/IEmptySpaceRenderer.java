package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.Context;

/**
 * Interface to abstract the rendering of a blank space.
 * @author lukflug
 * @param <T> type representing state of scroll bar
 */
@FunctionalInterface
public interface IEmptySpaceRenderer<T> {
	/**
	 * This function renders the scroll bar corner
	 * @param context the context to be used
	 * @param focus the current focus state
	 * @param state the state of the scroll bar
	 */
	public void renderSpace (Context context, boolean focus, T state);
}
