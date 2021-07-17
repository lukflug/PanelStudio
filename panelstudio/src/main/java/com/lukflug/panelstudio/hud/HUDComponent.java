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

public abstract class HUDComponent implements IFixedComponent {
	protected String title;
	protected IBoolean visible;
	protected String description;
	protected Point position;
	protected String configName;
	
	public HUDComponent (ILabeled label, Point position, String configName) {
		this.title=label.getDisplayName();
		this.position=position;
		this.description=label.getDescription();
		this.configName=configName;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void render(Context context) {
		context.setHeight(getSize(context.getInterface()).height);
		if (description!=null) context.setDescription(new Description(context.getRect(),description));
	}

	@Override
	public void handleButton(Context context, int button) {
		context.setHeight(getSize(context.getInterface()).height);
	}

	@Override
	public void handleKey(Context context, int scancode) {
		context.setHeight(getSize(context.getInterface()).height);
	}
	
	@Override
	public void handleChar(Context context, char character) {
		context.setHeight(getSize(context.getInterface()).height);
	}

	@Override
	public void handleScroll(Context context, int diff) {
		context.setHeight(getSize(context.getInterface()).height);
	}

	@Override
	public void getHeight(Context context) {
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
		return getSize(inter).width;
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
	
	public abstract Dimension getSize (IInterface inter);
}
