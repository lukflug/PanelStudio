package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.Description;
import com.lukflug.panelstudio.base.IBoolean;

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
	 * Return value of {@link #lastVisible}.
	 */
	private boolean lastVisible=false;
	
	/**
	 * Constructor.
	 * @param title the caption for this component
	 * @param description the description for this component
	 * @param visible whether this component is visible
	 */
	public ComponentBase (String title, String description, IBoolean visible) {
		this.title=title;
		this.description=description;
		this.visible=visible;
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
	public void handleScroll(Context context, int diff) {
		context.setHeight(getHeight());
	}

	@Override
	public void getHeight(Context context) {
		context.setHeight(getHeight());
	}

	@Override
	public void enter() {
		lastVisible=true;
	}

	@Override
	public void exit() {
		lastVisible=false;
	}

	@Override
	public boolean isVisible() {
		return visible.isOn();
	}
	
	@Override
	public boolean lastVisible() {
		return lastVisible;
	}
	
	/**
	 * Return height of component.
	 * @return the height of the component
	 */
	protected abstract int getHeight();
}
