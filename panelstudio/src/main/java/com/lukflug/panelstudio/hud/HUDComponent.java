package com.lukflug.panelstudio.hud;

import java.awt.Dimension;
import java.awt.Point;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.Description;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.config.IPanelConfig;
import com.lukflug.panelstudio.setting.ILabeled;

public class HUDComponent implements IFixedComponent {
	protected String title;
	protected IBoolean visible;
	protected String description;
	protected Point position;
	protected Dimension size;
	protected String configName;
	
	public HUDComponent (ILabeled label, Point position, Dimension size, String configName) {
		this.title=label.getDisplayName();
		this.position=position;
		this.description=label.getDescription();
		this.size=size;
		this.configName=configName;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void render(Context context) {
		context.setHeight(size.height);
		context.setDescription(new Description(context.getRect(),description));
	}

	@Override
	public void handleButton(Context context, int button) {
		context.setHeight(size.height);
	}

	@Override
	public void handleKey(Context context, int scancode) {
		context.setHeight(size.height);
	}

	@Override
	public void handleScroll(Context context, int diff) {
		context.setHeight(size.height);
	}

	@Override
	public void getHeight(Context context) {
		context.setHeight(size.height);
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
		return true;
	}

	@Override
	public Point getPosition(IInterface inter) {
		return new Point(position);
	}

	@Override
	public void setPosition(IInterface inter, Point position) {
		this.position=new Point(position);
	}

	@Override
	public int getWidth(IInterface inter) {
		return size.width;
	}

	@Override
	public boolean savesState() {
		return true;
	}

	@Override
	public void saveConfig(IInterface inter, IPanelConfig config) {
		config.savePositon(position);
	}

	@Override
	public void loadConfig(IInterface inter, IPanelConfig config) {
		position=config.loadPosition();
	}

	@Override
	public String getConfigName() {
		return configName;
	}

}
