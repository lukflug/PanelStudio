package com.lukflug.panelstudio.component;

import java.util.function.Consumer;

import com.lukflug.panelstudio.base.Context;

/**
 * Component proxy that manages focus.
 * @author lukflug
 * @param <T> the component type
 */
public abstract class FocusableComponentProxy<T extends IComponent> implements IComponentProxy<T> {
	/**
	 * The focus state this component should have after becoming visible.
	 */
	private final boolean initFocus;
	/**
	 * The focus state for this component.
	 */
	private boolean focus;
	/**
	 * Flag to indicate that focus has to be requested.
	 */
	private boolean requestFocus=false;
	
	/**
	 * Constructor.
	 * @param focus initial focus state
	 */
	public FocusableComponentProxy (boolean focus) {
		initFocus=focus;
		this.focus=focus;
	}
	
	@Override
	public void handleButton (Context context, int button) {
		IComponentProxy.super.handleButton(context,button);
		if (context.getInterface().getButton(button)) {
			focus=context.isHovered();
			if (focus) context.requestFocus();
		}
	}
	
	@Override
	public Context doOperation (Context context, Consumer<Context> operation) {
		if (requestFocus) context.requestFocus();
		else if (!context.hasFocus()) focus=false;
		requestFocus=false;
		return IComponentProxy.super.doOperation(context,operation);
	}
	
	@Override
	public void releaseFocus() {
		focus=false;
		IComponentProxy.super.releaseFocus();
	}
	
	@Override
	public void enter() {
		focus=initFocus;
		if (focus) requestFocus=true;
		IComponentProxy.super.enter();
	}
	
	@Override
	public void exit() {
		focus=initFocus;
		IComponentProxy.super.exit();
	}
	
	/**
	 * Get current focus state.
	 * @param context the {@link Context} for the component
	 * @return set to true, if the component has focus
	 */
	public boolean hasFocus (Context context) {
		return context.hasFocus()&&focus;
	}
}
