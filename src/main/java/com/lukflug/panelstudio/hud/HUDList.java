package com.lukflug.panelstudio.hud;

import java.awt.Color;

/**
 * Interface representing a list for {@link ListComponent}
 * @author lukas_ghdqwsr
 */
public interface HUDList {
	/**
	 * Get the number of elements in the list
	 * @return list size
	 */
	public int getSize();
	/**
	 * Get the item at the given index.
	 * @param index index of item
	 * @return the item corresponding to the index
	 */
	public String getItem (int index);
	/**
	 * Get the color the string should have.
	 * @param index index of item
	 * @return the color of the item
	 */
	public Color getItemColor (int index);
	/**
	 * Whether to align the list downwards.
	 * @return align down
	 */
	public boolean sortUp();
	/**
	 * Whether to align the list to the right.
	 * @return align right
	 */
	public boolean sortRight();
}
