package com.lukflug.panelstudio.component;

import java.awt.Component;
import java.awt.Point;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.config.IPanelConfig;

/**
 * Interface representing a {@link Component} that has a fixed position
 * (i.e. the position isn't determined by the parent via {@link Context}).
 * @author lukflug
 */
public interface IFixedComponent extends IComponent {
	/**
	 * Get the current component position.
	 * @param inter current interface
	 * @return current position
	 */
	public Point getPosition (IInterface inter);
	
	/**
	 * Set the current component position.
	 * @param inter current interface
	 * @param position new position
	 */
	public void setPosition (IInterface inter, Point position);
	
	/**
	 * Get the component width.
	 * @param inter current interface
	 * @return component width
	 */
	public int getWidth (IInterface inter);
	
	/**
	 * Returns whether this component allows its state to be saved.
	 * @return true, if this component saves its state
	 */
	public boolean savesState();
	
	/**
	 * Saves the component state
	 * @param inter current interface
	 * @param config configuration to use
	 */
	public void saveConfig (IInterface inter, IPanelConfig config);
	
	/**
	 * Loads the component state
	 * @param inter current interface
	 * @param config configuration to use
	 */
	public void loadConfig (IInterface inter, IPanelConfig config);
}
