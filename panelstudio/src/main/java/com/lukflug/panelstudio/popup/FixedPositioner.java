package com.lukflug.panelstudio.popup;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.base.IInterface;

/**
 * Pop-up with a fixed position.
 * @author lukflug
 */
public class FixedPositioner implements IPopupPositioner {
	/**
	 * The position the pop-up should appear.
	 */
	protected Point pos;
	
	/**
	 * Constructor.
	 * @param pos the position of the pop-up.
	 */
	public FixedPositioner (Point pos) {
		this.pos=pos;
	}
	
	@Override
	public Point getPosition(IInterface inter, Dimension popup, Rectangle component, Rectangle panel) {
		return new Point(pos);
	}
}
