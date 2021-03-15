package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.setting.ILabeled;

/**
 * Base class for all components included in this library.
 * @author lukflug
 */
public abstract class FocusableComponent extends ComponentBase {
	/**
	 * The focus state for this component.
	 */
	private boolean focus=false;

	/**
	 * Constructor.
	 * @param label the label for the component
	 */
	public FocusableComponent (ILabeled label) {
		super(label);
	}
	
	@Override
	public void handleButton (Context context, int button) {
		super.handleButton(context,button);
		updateFocus(context,button);
	}
	
	@Override
	public void releaseFocus() {
		focus=false;
	}
	
	@Override
	public void exit() {
		focus=false;
	}
	
	/**
	 * Get current focus state.
	 * @param context the {@link Context} for the component
	 * @return set to true, if the component has focus
	 */
	public boolean hasFocus (Context context) {
		return context.hasFocus()&&focus;
	}
	
	/**
	 * If the button is being pressed, update focus state to hover state and call {@link #handleFocus(Context, boolean)}.
	 * @param context the {@link Context} for the component
	 * @param button the mouse button state that changed
	 * @see Interface#LBUTTON
	 * @see Interface#RBUTTON
	 */
	protected void updateFocus (Context context, int button) {
		if (context.getInterface().getButton(button)) {
			focus=context.isHovered();
			if (focus) context.requestFocus();
			handleFocus(context,focus&&context.hasFocus());
		}
	}
	
	/**
	 * Does nothing, called when the focus state changes due to a mouse event.
	 * @param context the {@link Context} for the component
	 * @param focus the new focus state
	 */
	protected void handleFocus (Context context, boolean focus) {
	}
}
