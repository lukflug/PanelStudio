package com.lukflug.panelstudio.base;

import java.awt.Rectangle;

/**
 * Class representing a tooltip description
 * @author lukflug
 */
public final class Description {
	/**
	 * The position of the component causing the tooltip.
	 */
	private final Rectangle componentPos;
	/**
	 * The position of the panel the component is in.
	 */
	private final Rectangle panelPos;
	/**
	 * The content of the description.
	 */
	private final String content;
	
	/**
	 * Constructor.
	 * @param position the position causing the description
	 * @param content the content of the description
	 */
	public Description (Rectangle position, String content) {
		this.componentPos=position;
		this.panelPos=position;
		this.content=content;
	}
	
	/**
	 * Create description from another description.
	 * @param description the base description
	 * @param position the new panel position
	 */
	public Description (Description description, Rectangle position) {
		this.componentPos=description.componentPos;
		this.panelPos=position;
		this.content=description.content;
	}
	
	/**
	 * Get component position.
	 * @return the position of the component causing this description
	 */
	public Rectangle getComponentPos() {
		return componentPos;
	}
	
	/**
	 * Get the panel position.
	 * @return the position of the panel containing the component
	 */
	public Rectangle getPanelPos() {
		return panelPos;
	}
	
	/**
	 * Get the content.
	 * @return the content of this description
	 */
	public String getContent() {
		return content;
	}
}
