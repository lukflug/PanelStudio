package com.lukflug.panelstudio.theme;

import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;

/**
 * Interface abstracting the rendering of a switch.
 * @author lukflug
 * @param <T> type representing state of switch
 */
public interface ISwitchRenderer<T> extends IButtonRenderer<T> {
	/**
	 * Get the field for clicking the "on" state.
	 * @param context the context to be used
	 * @return the location of the on-field
	 */
	public Rectangle getOnField (Context context);
	
	/**
	 * Get the field for clicking the "off" state.
	 * @param context the context to be used
	 * @return the location of the off-field
	 */
	public Rectangle getOffField (Context context);
}
