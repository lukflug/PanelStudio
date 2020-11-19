package com.lukflug.panelstudio.tabgui;

import java.awt.Point;

import com.lukflug.panelstudio.Animation;
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
	 * The witdh of the TabGUI.
	 */
	protected int width;
	
	/**
	 * Constructor.
	 * @param title caption for the TabGUI
	 * @param renderer the renderer for the TabGUI
	 * @param animation the animation for the TabGUI
	 * @param position the initial position for the TabGUI
	 */
	public TabGUI(String title, TabGUIRenderer renderer, Animation animation, Point position, int width) {
		super(title, renderer,animation);
		this.position=position;
		this.width=width;
	}

	/**
	 * Get the current position.
	 */
	@Override
	public Point getPosition(Interface inter) {
		return new Point(position);
	}

	/**
	 * Update the position.
	 */
	@Override
	public void setPosition(Interface inter, Point position) {
		this.position=position;
	}

	@Override
	public int getWidth() {
		return width;
	}
}
