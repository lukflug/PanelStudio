package com.lukflug.panelstudio.component;

import java.util.function.Consumer;

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
		doOperation(context,component::render);
	}

	@Override
	public void handleButton(Context context, int button) {
		doOperation(context,subContext->component.handleButton(subContext,button));
	}

	@Override
	public void handleKey(Context context, int scancode) {
		doOperation(context,subContext->component.handleKey(subContext,scancode));
	}

	@Override
	public void handleScroll(Context context, int diff) {
		doOperation(context,subContext->component.handleScroll(subContext,diff));
	}

	@Override
	public void getHeight(Context context) {
		doOperation(context,component::getHeight);
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

	/**
	 * Perform a context-sensitive operation.
	 * @param context the context to use
	 * @param operation the operation to perform
	 */
	protected Context doOperation (Context context, Consumer<Context> operation) {
		Context subContext=getContext(context);
		operation.accept(subContext);
		if (subContext!=context) {
			if (subContext.focusReleased()) context.releaseFocus();
			else if (subContext.foucsRequested()) context.requestFocus();
			context.setHeight(subContext.getSize().height);
		}
		return subContext;
	}
	
	/**
	 * Get the context for the content component.
	 * @param context the parent context
	 * @return the child context
	 */
	protected Context getContext (Context context) {
		return context;
	}
}
