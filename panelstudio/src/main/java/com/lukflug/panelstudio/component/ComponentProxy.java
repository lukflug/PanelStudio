package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.base.Context;

/**
 * Proxy redirecting calls to other component.
 * @author lukflug
 */
public class ComponentProxy implements IComponent {
	/**
	 * The component to be redirected to.
	 */
	protected IComponent component;
	
	/**
	 * Constructor.
	 * @param component component to be redirected to
	 */
	public ComponentProxy (IComponent component) {
		this.component=component;
	}

	@Override
	public String getTitle() {
		return component.getTitle();
	}

	@Override
	public void render(Context context) {
		component.render(context);
	}

	@Override
	public void handleButton(Context context, int button) {
		component.handleButton(context,button);
	}

	@Override
	public void handleKey(Context context, int scancode) {
		component.handleKey(context,scancode);
	}

	@Override
	public void handleScroll(Context context, int diff) {
		component.handleScroll(context,diff);
	}

	@Override
	public void getHeight(Context context) {
		component.getHeight(context);
	}

	@Override
	public void enter() {
		component.enter();
	}

	@Override
	public void exit() {
		component.exit();
	}

	@Override
	public void releaseFocus() {
		component.releaseFocus();
	}

	@Override
	public boolean isVisible() {
		return component.isVisible();
	}

	@Override
	public boolean lastVisible() {
		return component.lastVisible();
	}

}
