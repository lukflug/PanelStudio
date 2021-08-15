package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.Context;

/**
 * Interface to render the border of resizable components.
 * @author lukflug
 */
public interface IResizeBorderRenderer {
	/**
	 * Draw a border of a resizable component.
	 * @param context the context to be used
	 * @param focus the focus state of the component
	 */
	public void drawBorder (Context context, boolean focus);
	
	/**
	 * Returns the border size
	 * @return the border size
	 */
	public int getBorder();
}
