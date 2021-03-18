package com.lukflug.panelstudio.component;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.config.IPanelConfig;
import com.lukflug.panelstudio.popup.IPopup;
import com.lukflug.panelstudio.popup.IPopupPositioner;

/**
 * Interface representing a {@link Component} that has a fixed position
 * (i.e. the position isn't determined by the parent via {@link Context}).
 * @author lukflug
 */
public interface IFixedComponent extends IComponent,IPopup {
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
	
	@Override
	public default void setPosition (IInterface inter, Rectangle component, Rectangle panel, IPopupPositioner positioner) {
		setPosition(inter,positioner.getPosition(inter,null,component,panel));
	}
	
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
	
	public String getConfigName();
}
