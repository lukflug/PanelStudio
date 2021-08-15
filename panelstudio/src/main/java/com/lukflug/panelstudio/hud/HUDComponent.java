package com.lukflug.panelstudio.hud;

import java.awt.Dimension;
import java.awt.Point;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.Description;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.config.IPanelConfig;
import com.lukflug.panelstudio.setting.ILabeled;

/**
 * Base class for HUD components.
 * @author lukflug
 */
public abstract class HUDComponent implements IFixedComponent {
	/**
	 * The component label.
	 */
	protected ILabeled label;
	/**
	 * The current component position.
	 */
	protected Point position;
	/**
	 * The component config name.
	 */
	protected String configName;
	
	/**
	 * Constructor.
	 * @param label the label for the component
	 * @param position the initial position
	 * @param configName the config name for the component
	 */
	public HUDComponent (ILabeled label, Point position, String configName) {
		this.label=label;
		this.position=position;
		this.configName=configName;
	}

	@Override
	public String getTitle() {
		return label.getDisplayName();
	}

	@Override
	public void render (Context context) {
		context.setHeight(getSize(context.getInterface()).height);
		if (label.getDescription()!=null) context.setDescription(new Description(context.getRect(),label.getDescription()));
	}

	@Override
	public void handleButton (Context context, int button) {
		context.setHeight(getSize(context.getInterface()).height);
	}

	@Override
	public void handleKey (Context context, int scancode) {
		context.setHeight(getSize(context.getInterface()).height);
	}
	
	@Override
	public void handleChar (Context context, char character) {
		context.setHeight(getSize(context.getInterface()).height);
	}

	@Override
	public void handleScroll (Context context, int diff) {
		context.setHeight(getSize(context.getInterface()).height);
	}

	@Override
	public void getHeight (Context context) {
		context.setHeight(getSize(context.getInterface()).height);
	}

	@Override
	public void enter() {
	}

	@Override
	public void exit() {
	}

	@Override
	public void releaseFocus() {
	}

	@Override
	public boolean isVisible() {
		return label.isVisible().isOn();
	}

	@Override
	public Point getPosition (IInterface inter) {
		return new Point(position);
	}

	@Override
	public void setPosition (IInterface inter, Point position) {
		this.position=new Point(position);
	}

	@Override
	public int getWidth (IInterface inter) {
		return getSize(inter).width;
	}

	@Override
	public boolean savesState() {
		return true;
	}

	@Override
	public void saveConfig (IInterface inter, IPanelConfig config) {
		config.savePositon(position);
	}

	@Override
	public void loadConfig (IInterface inter, IPanelConfig config) {
		position=config.loadPosition();
	}

	@Override
	public String getConfigName() {
		return configName;
	}
	
	/**
	 * Returns the size of the HUD component.
	 * @param inter the current interface
	 * @return the component size
	 */
	public abstract Dimension getSize (IInterface inter);
}
