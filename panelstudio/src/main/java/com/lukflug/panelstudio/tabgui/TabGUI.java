package com.lukflug.panelstudio.tabgui;

import java.awt.Point;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.config.IPanelConfig;

/**
 * A {@link TabGUIContainer} that is also a {@link IFixedComponent}.
 * Should be used as root element.
 * @author lukflug
 */
public class TabGUI extends TabGUIContainer implements IFixedComponent {
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
	 * @param width the width of the TabGUI
	 */
	public TabGUI(String title, ITabGUIRenderer renderer, Animation animation, Point position, int width) {
		super(title, renderer,animation);
		this.position=position;
		this.width=width;
	}

	/**
	 * Get the current position.
	 */
	@Override
	public Point getPosition(IInterface inter) {
		return new Point(position);
	}

	/**
	 * Update the position.
	 */
	@Override
	public void setPosition(IInterface inter, Point position) {
		this.position=position;
	}

	@Override
	public int getWidth (IInterface inter) {
		return width;
	}

	@Override
	public void saveConfig(IInterface inter, IPanelConfig config) {
		config.savePositon(position);
	}

	@Override
	public void loadConfig(IInterface inter, IPanelConfig config) {
		Point pos=config.loadPosition();
		if (pos!=null) position=pos;
	}
}
