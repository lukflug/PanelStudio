package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.Description;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.setting.ILabeled;

/**
 * Base class for components.
 * @author lukflug
 */
public abstract class ComponentBase implements IComponent {
	/**
	 * The title of the component.
	 */
	protected String title;
	/**
	 * Get description of the component.
	 */
	protected String description;
	/**
	 * Boolean indicating visibility of component.
	 */
	protected IBoolean visible;
	
	/**
	 * Constructor.
	 * @param label the label for the component
	 */
	public ComponentBase (ILabeled label) {
		this.title=label.getDisplayName();
		this.description=label.getDescription();
		this.visible=label.isVisible();
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void render(Context context) {
		context.setHeight(getHeight());
		if (context.isHovered() && description!=null) context.setDescription(new Description(context.getRect(),description));
	}

	@Override
	public void handleButton(Context context, int button) {
		context.setHeight(getHeight());
	}

	@Override
	public void handleKey(Context context, int scancode) {
		context.setHeight(getHeight());
	}
	
	@Override
	public void handleChar(Context context, char character) {
		context.setHeight(getHeight());
	}

	@Override
	public void handleScroll(Context context, int diff) {
		context.setHeight(getHeight());
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
		return visible.isOn();
	}
	
	/**
	 * Return height of component.
	 * @return the height of the component
	 */
	protected abstract int getHeight();
}
