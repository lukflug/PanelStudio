package com.lukflug.panelstudio.container;

import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.component.IComponent;

/**
 * Interface representing container of components.
 * @author lukflug
 * @param <T> the type of components that are members of this container
 */
public interface IContainer<T extends IComponent> {
	/**
	 * Add component to GUI.
	 * @param component the component to be added
	 * @return whether the component was added
	 */
	public boolean addComponent (T component);
	
	/**
	 * Add component to GUI.
	 * @param component the component to be added
	 * @param visible the external visibility for the component
	 * @return whether the component was added
	 */
	public boolean addComponent (T component, IBoolean visible);
	
	/**
	 * Remove component from GUI.
	 * @param component the component to be removed
	 * @return whether the component was removed
	 */
	public boolean removeComponent (T component);
}
