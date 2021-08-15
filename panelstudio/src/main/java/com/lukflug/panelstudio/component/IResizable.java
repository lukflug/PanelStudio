package com.lukflug.panelstudio.component;

import java.awt.Dimension;

/**
 * Interface representing resize behavior.
 * @author lukflug
 */
public interface IResizable {
	/**
	 * Get current size.
	 * @return the current size
	 */
	public Dimension getSize();
	
	/**
	 * Set size due to resizing.
	 * @param size the resized size
	 */
	public void setSize (Dimension size);
}
