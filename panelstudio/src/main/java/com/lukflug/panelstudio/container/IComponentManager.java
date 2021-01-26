package com.lukflug.panelstudio.container;

import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.component.IComponent;

/**
 * Interface used by transient components to show and hide themselves.
 * @author lukflug
 * @param <T> the type of components that are members of this container
 */
public interface IComponentManager<T extends IComponent> {
	/**
	 * Get toggleable indicating whether a component is shown or not.
	 * @param component the component in question
	 * @return the toggleable indicating whether the component is shown
	 */
	public IToggleable getComponentToggleable (T component);
	
	/**
	 * Close and dispose the component, removing it from the component list.
	 * @param component the component to dispose
	 */
	public void disposeComponent(T component);
}
