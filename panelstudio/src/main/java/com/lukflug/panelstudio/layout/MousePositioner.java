package com.lukflug.panelstudio.layout;

import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.base.IInterface;

/**
 * Pop-up that is positioned near mouse.
 * @author lukflug
 */
public class MousePositioner implements IPopupPositioner {
	/**
	 * The offset.
	 */
	protected Point offset;
	
	/**
	 * Constructor.
	 * @param offset the offset relative to the current cursor position
	 */
	public MousePositioner (Point offset) {
		this.offset=offset;
	}

	@Override
	public Point getPosition(IInterface inter, Rectangle component, Rectangle panel) {
		Point pos=inter.getMouse();
		pos.translate(offset.x,offset.y);
		return pos;
	}
}
