package com.lukflug.panelstudio.tabgui;

import com.lukflug.panelstudio.popup.IPopupPositioner;

/**
 * Interface for rendering a TabGUI.
 * @author lukflug
 */
public interface ITabGUITheme {
	/**
	 * Get the default tab width.
	 * @return the tab width
	 */
	public int getTabWidth();
	
	/**
	 * Get the pop-up positioner for child tabs.
	 * @return the pop-up positioner
	 */
	public IPopupPositioner getPositioner();
	
	/**
	 * Get the tab renderer for the root tab.
	 * @return the parent renderer
	 */
	public ITabGUIRenderer<Void> getParentRenderer();
	
	/**
	 * Get the tab renderer for the child tab.
	 * @return the child renderer
	 */
	public ITabGUIRenderer<Boolean> getChildRenderer();
}
