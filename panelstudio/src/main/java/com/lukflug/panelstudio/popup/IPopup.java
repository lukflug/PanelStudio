package com.lukflug.panelstudio.popup;

import java.awt.Rectangle;

import com.lukflug.panelstudio.base.IInterface;

@FunctionalInterface
public interface IPopup {
	public void setPosition (IInterface inter, Rectangle component, Rectangle panel, IPopupPositioner positioner);
}
