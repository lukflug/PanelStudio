package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.base.Context;

public abstract class FocusableComponentProxy<T extends IComponent> implements IComponentProxy<T> {
	/**
	 * The focus state for this component.
	 */
	private boolean focus=false;
	
	@Override
	public void handleButton (Context context, int button) {
		IComponentProxy.super.handleButton(context,button);
		if (context.getInterface().getButton(button)) focus=context.isHovered();
	}
	
	@Override
	public void releaseFocus() {
		focus=false;
		IComponentProxy.super.releaseFocus();
	}
	
	@Override
	public void exit() {
		focus=false;
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
