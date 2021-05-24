package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.Description;
import com.lukflug.panelstudio.setting.ILabeled;

/**
 * Base class for components.
 * @author lukflug
 */
public abstract class ComponentBase implements IComponent {
	/**
	 * The label of the component.
	 */
	protected final ILabeled label;
	
	/**
	 * Constructor.
	 * @param label the label for the component
	 */
	public ComponentBase (ILabeled label) {
		this.label=label;
	}

	@Override
	public String getTitle() {
		return label.getDisplayName();
	}

	@Override
	public void render(Context context) {
		getHeight(context);
		if (context.isHovered() && label.getDescription()!=null) context.setDescription(new Description(context.getRect(),label.getDescription()));
	}

	@Override
	public void handleButton(Context context, int button) {
		getHeight(context);
	}

	@Override
	public void handleKey(Context context, int scancode) {
		getHeight(context);
	}
	
	@Override
	public void handleChar(Context context, char character) {
		getHeight(context);
	}

	@Override
	public void handleScroll(Context context, int diff) {
		getHeight(context);
	}

	@Override
	public void getHeight(Context context) {
		context.setHeight(getHeight());
	}
	
	@Override
	public void enter() {
	}
	
	@Override
	public void exit() {
	}

	@Override
	public boolean isVisible() {
		return label.isVisible().isOn();
	}
	
	/**
	 * Return height of component.
	 * @return the height of the component
	 */
	protected abstract int getHeight();
}
