package com.lukflug.panelstudio.layout;

import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.base.IInterface;

/**
 * Pop-up positioner, that positions the pop-up on the side of the panel.
 * @author lukflug
 */
public class PanelPositioner implements IPopupPositioner {
	/**
	 * The offset.
	 */
	protected Point offset;
	
	/**
	 * Constructor.
	 * @param offset the offset relative to the current cursor position
	 */
	public PanelPositioner (Point offset) {
		this.offset=offset;
	}

	@Override
	public Point getPosition(IInterface inter, Rectangle component, Rectangle panel) {
		return new Point(panel.x+panel.width+offset.x,component.y+offset.y);
	}
}
