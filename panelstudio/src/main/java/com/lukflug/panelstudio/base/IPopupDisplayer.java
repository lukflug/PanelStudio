package com.lukflug.panelstudio.base;

import java.awt.Rectangle;

import com.lukflug.panelstudio.layout.IPopupPositioner;

/**
 * Object that can display a pop-up.
 * @author lukflug
 */
public interface IPopupDisplayer {
	public void displayPopup (Object popup, Rectangle rect, IToggleable visible, IPopupPositioner positioner);
}
