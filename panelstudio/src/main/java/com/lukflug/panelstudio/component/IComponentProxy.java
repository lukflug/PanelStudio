package com.lukflug.panelstudio.component;

import java.util.function.Consumer;

import com.lukflug.panelstudio.base.Context;

/**
 * Proxy redirecting calls to other component.
 * @author lukflug
 */
@FunctionalInterface
public interface IComponentProxy<T extends IComponent> extends IComponent {
	@Override
	public default String getTitle() {
		return getComponent().getTitle();
	}

	@Override
	public default void render(Context context) {
		doOperation(context,getComponent()::render);
	}

	@Override
	public default void handleButton(Context context, int button) {
		doOperation(context,subContext->getComponent().handleButton(subContext,button));
	}

	@Override
	public default void handleKey(Context context, int scancode) {
		doOperation(context,subContext->getComponent().handleKey(subContext,scancode));
	}
	
	@Override
	public default void handleChar(Context context, char character) {
		doOperation(context,subContext->getComponent().handleChar(subContext,character));
	}

	@Override
	public default void handleScroll(Context context, int diff) {
		doOperation(context,subContext->getComponent().handleScroll(subContext,diff));
	}

	@Override
	public default void getHeight(Context context) {
		doOperation(context,getComponent()::getHeight);
	}

	@Override
	public default void enter() {
		getComponent().enter();
	}

	@Override
	public default void exit() {
		getComponent().exit();
	}

	@Override
	public default void releaseFocus() {
		getComponent().releaseFocus();
	}

	@Override
	public default boolean isVisible() {
		return getComponent().isVisible();
	}
	
	/**
	 * Returns the current component being redirected.
	 * @return the component
	 */
	public T getComponent();

	/**
	 * Perform a context-sensitive operation.
	 * @param context the context to use
	 * @param operation the operation to perform
	 */
	public default Context doOperation (Context context, Consumer<Context> operation) {
		Context subContext=getContext(context);
		operation.accept(subContext);
		if (subContext!=context) {
			if (subContext.focusReleased()) context.releaseFocus();
			else if (subContext.foucsRequested()) context.requestFocus();
			context.setHeight(getHeight(subContext.getSize().height));
			if (subContext.getDescription()!=null) context.setDescription(subContext.getDescription());
		}
		return subContext;
	}
	
	/**
	 * Function to determine visible height.
	 * @param height the component height
	 * @return the visible height
	 */
	public default int getHeight (int height) {
		return height;
	}
	
	/**
	 * Get the context for the content component.
	 * @param context the parent context
	 * @return the child context
	 */
	public default Context getContext (Context context) {
		return context;
	}
}
