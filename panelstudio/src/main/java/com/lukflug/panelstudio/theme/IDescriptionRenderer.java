package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.Context;

/**
 * Interface used to render descriptions.
 * @author lukflug
 */
public interface IDescriptionRenderer {
	/**
	 * Render a description
	 * @param context the context for the panel creating the description
	 */
	public void renderDescription (Context context);
}
