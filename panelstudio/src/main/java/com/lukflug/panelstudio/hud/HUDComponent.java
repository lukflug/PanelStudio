package com.lukflug.panelstudio.hud;

import java.awt.Point;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.config.IPanelConfig;
import com.lukflug.panelstudio.theme.IRenderer;

/**
 * Base class for HUD elements.
 * @author lukflug
 */
public abstract class HUDComponent implements IFixedComponent {
	/**
	 * The caption of this component.
	 */
	protected String title;
	/**
	 * The {@link IRenderer} for this component.
	 */
	protected IRenderer renderer;
	/**
	 * Current position;
	 */
	protected Point position;
	
	public HUDComponent (String title, IRenderer renderer, Point position) {
		this.title=title;
		this.renderer=renderer;
		this.position=position;
	}
	
	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * Set component height.
	 */
	@Override
	public void render(Context context) {
		getHeight(context);
	}

	/**
	 * Do nothing.
	 */
	@Override
	public void handleButton(Context context, int button) {
		getHeight(context);
	}

	/**
	 * Do nothing.
	 */
	@Override
	public void handleKey(Context context, int scancode) {
		getHeight(context);
	}

	/**
	 * Do nothing.
	 */
	@Override
	public void handleScroll(Context context, int diff) {
		getHeight(context);
	}

	/**
	 * Do nothing.
	 */
	@Override
	public void enter(Context context) {
		getHeight(context);
	}

	/**
	 * Do nothing.
	 */
	@Override
	public void exit(Context context) {
		getHeight(context);
	}

	/**
	 * Do nothing.
	 */
	@Override
	public void releaseFocus() {
	}

	/**
	 * Get the component position.
	 */
	@Override
	public Point getPosition(IInterface inter) {
		return new Point(position);
	}

	/**
	 * Set the component position.
	 */
	@Override
	public void setPosition(IInterface inter, Point position) {
		this.position=position;
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
