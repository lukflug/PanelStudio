package com.lukflug.panelstudio.component;

import java.awt.Point;

import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.config.IPanelConfig;

/**
 * Class wrapping a generic component into a fixed component.
 * @author lukflug
 */
public class FixedComponent<T extends IComponent> extends ComponentProxy<T> implements IFixedComponent {
	/**
	 * The position of the fixed component.
	 */
	protected Point position;
	/**
	 * The width of the fixed component.
	 */
	protected int width;
	/**
	 * The state boolean of the fixed component.
	 */
	protected IToggleable state;
	/**
	 * Whether the components state is stored.
	 */
	protected boolean savesState;
	protected String configName;
	
	/**
	 * Constructor.
	 * @param component component to be wrapped
	 * @param position the position of the component
	 * @param width the width of the component
	 * @param boolean to save as state
	 * @param boolean whether state is saved
	 */
	public FixedComponent (T component, Point position, int width, IToggleable state, boolean savesState, String configName) {
		super(component);
		this.position=position;
		this.width=width;
		this.state=state;
		this.savesState=savesState;
		this.configName=configName;
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
		return width;
	}

	@Override
	public boolean savesState() {
		return savesState;
	}

	@Override
	public void saveConfig(IInterface inter, IPanelConfig config) {
		config.savePositon(position);
		if (state!=null) config.saveState(state.isOn());
	}

	@Override
	public void loadConfig(IInterface inter, IPanelConfig config) {
		position=config.loadPosition();
		if (state!=null) {
			if (state.isOn()!=config.loadState()) state.toggle();
		}
	}
	
	@Override
	public String getConfigName() {
		return configName;
	}
}
