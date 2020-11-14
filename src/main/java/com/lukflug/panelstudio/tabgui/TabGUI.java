package com.lukflug.panelstudio.tabgui;

import java.awt.Point;

import com.lukflug.panelstudio.FixedComponent;
import com.lukflug.panelstudio.Interface;

/**
 * A {@link TabGUIContainer} that is also a {@link FixedComponent}.
 * Should be used as root element.
 * @author lukflug
 */
public class TabGUI extends TabGUIContainer implements FixedComponent {
	/**
	 * Current position of the TabGUI.
	 */
	protected Point position;
	
	/**
	 * Constructor.
	 * @param title caption for the TabGUI
	 * @param renderer the renderer for the TabGUI
	 * @param position the intitial position for the TabGUI
	 */
	public TabGUI(String title, TabGUIRenderer renderer, Point position) {
		super(title, renderer);
		this.position=position;
	}

	/**
	 * Get the current position.
	 */
	@Override
	public Point getPosition(Interface inter) {
		return position;
	}

	/**
	 * Update the position.
	 */
	@Override
	public void setPosition(Interface inter, Point position) {
		this.position=position;
	}

}
