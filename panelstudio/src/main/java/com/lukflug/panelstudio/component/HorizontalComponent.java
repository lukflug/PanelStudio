package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.base.IInterface;

/**
 * Class wrapping a generic component into a horizontal component.
 * @author lukflug
 */
public class HorizontalComponent<T extends IComponent> extends ComponentProxy<T> implements IHorizontalComponent {
	/**
	 * The minimal width of the component.
	 */
	protected int width;
	/**
	 * The weight of the component.
	 */
	protected int weight;
	
	/**
	 * Constructor.
	 * @param component component to be wrapped
	 * @param width the width of the component
	 * @param weight the weight of the component
	 */
	public HorizontalComponent (T component, int width, int weight) {
		super(component);
		this.width=width;
		this.weight=weight;
	}

	@Override
	public int getWidth(IInterface inter) {
		return width;
	}

	@Override
	public int getWeight() {
		return weight;
	}
}
