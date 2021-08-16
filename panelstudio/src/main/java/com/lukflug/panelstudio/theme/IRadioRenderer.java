package com.lukflug.panelstudio.theme;

import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.setting.ILabeled;

/**
 * Interface abstracting the rendering of a radio button list.
 * @author lukflug
 */
public interface IRadioRenderer {
	/**
	 * Render the radio button list.
	 * @param context the context to be used
	 * @param items a list of all items
	 * @param focus the current focus state
	 * @param target the currently selected option
	 * @param state the animated transition state
	 * @param horizontal whether the list is horizontal or vertical
	 */
	public void renderItem (Context context, ILabeled[] items, boolean focus, int target, double state, boolean horizontal);
	
	/**
	 * Returns the default height
	 * @param items a list of all items
	 * @param horizontal whether the list is horizontal or vertical
	 * @return the default height
	 */
	public int getDefaultHeight (ILabeled[] items, boolean horizontal);
	
	/**
	 * Returns the rectangle for a given item
	 * @param context the context to be used
	 * @param items a list of all items
	 * @param index the index of the item in question
	 * @param horizontal whether the list is horizontal or vertical
	 * @return the rectangle for that item
	 */
	public default Rectangle getItemRect (Context context, ILabeled[] items, int index, boolean horizontal) {
		Rectangle rect=context.getRect();
		if (horizontal) {
			int start=(int)Math.round(rect.width/(double)items.length*index);
			int end=(int)Math.round(rect.width/(double)items.length*(index+1));
			return new Rectangle(rect.x+start,rect.y,end-start,rect.height);
		} else {
			int start=(int)Math.round(rect.height/(double)items.length*index);
			int end=(int)Math.round(rect.height/(double)items.length*(index+1));
			return new Rectangle(rect.x,rect.y+start,rect.width,end-start);
		}
	}
}
