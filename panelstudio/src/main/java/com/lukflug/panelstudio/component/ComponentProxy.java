package com.lukflug.panelstudio.component;

/**
 * Implementation of {@link IComponentProxy}, redirecting to a component stored in a field.
 * @author lukflug
 * @param <T> the component type
 */
public class ComponentProxy<T extends IComponent> implements IComponentProxy<T> {
	/**
	 * The component to redirect to.
	 */
	protected final T component;
	
	/**
	 * Constructor.
	 * @param component the target component
	 */
	public ComponentProxy (T component) {
		this.component=component;
	}
	
	@Override
	public T getComponent() {
		return component;
	}
}
