package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.Context;

/**
 * Interface abstracting the rendering of a button.
 * @author lukflug
 * @param T type representing state of button
 */
public interface IButtonRenderer<T> {
	/**
	 * Render a button without active state.
	 * @param context the context to be used
	 * @param title the title of the button
	 * @param hasFocus the focus state of the button
	 * @param state the state of the button
	 */
	public void renderButton (Context context, String title, boolean hasFocus, T state);
	
	/**
	 * Returns the default height.
	 * @return the default height
	 */
	public int getDefaultHeight();
}
