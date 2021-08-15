package com.lukflug.panelstudio.tabgui;

import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;

/**
 * Interface for rendering TabGUI tabs.
 * @author lukflug
 * @param <T> the item state type
 */
public interface ITabGUIRenderer<T> {
	/**
	 * Render the tab base.
	 * @param context the current context
	 * @param amount the amount of items
	 * @param tabState currently highlighted tab index
	 */
	public void renderTab (Context context, int amount, double tabState);
	
	/**
	 * Renders item.
	 * @param context the current context
	 * @param amount the amount of items
	 * @param tabState the currently highlighted tab index
	 * @param index the item index
	 * @param title the item title
	 * @param itemState the item render state
	 */
	public void renderItem (Context context, int amount, double tabState, int index, String title, T itemState);
	
	/**
	 * Get the tab height.
	 * @param amount the amount of items
	 * @return the tab height
	 */
	public int getTabHeight (int amount);
	
	/**
	 * Get item highlight rectangle.
	 * @param inter the current interface
	 * @param rect the tab location
	 * @param amount the amount of items
	 * @param tabState currently highlighted tab index
	 * @return the rectangle for the highlight
	 */
	public Rectangle getItemRect (IInterface inter, Rectangle rect, int amount, double tabState);
}
