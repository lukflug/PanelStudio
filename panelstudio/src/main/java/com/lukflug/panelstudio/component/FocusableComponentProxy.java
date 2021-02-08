package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.base.Context;

public class FocusableComponentProxy extends ComponentProxy {
	/**
	 * The focus state for this component.
	 */
	private boolean focus=false;

	public FocusableComponentProxy(IComponent component) {
		super(component);
	}
	
	@Override
	public void handleButton (Context context, int button) {
		super.handleButton(context,button);
		if (context.getInterface().getButton(button)) focus=context.isHovered();
	}
	
	@Override
	public void releaseFocus() {
		focus=false;
		super.releaseFocus();
	}
	
	@Override
	public void exit() {
		focus=false;
		super.exit();
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
