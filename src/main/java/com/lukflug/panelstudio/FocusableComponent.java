package com.lukflug.panelstudio;

import com.lukflug.panelstudio.theme.Renderer;

/**
 * Base class for all components included in this library.
 * @author lukflug
 */
public class FocusableComponent implements Component {
	/**
	 * The caption of this component.
	 */
	protected String title;
	/**
	 * The {@link Renderer} for this component.
	 */
	protected Renderer renderer;
	/**
	 * The focus state for this component.
	 */
	private boolean focus=false;
	
	/**
	 * Constructor.
	 * @param title the caption for this component
	 * @param renderer the {@link Renderer} for this component
	 */
	public FocusableComponent (String title, Renderer renderer) {
		this.title=title;
		this.renderer=renderer;
	}
	
	/**
	 * Returns the caption of this component.
	 */
	@Override
	public String getTitle() {
		return title;
	}
	
	/**
	 * Set the height of this component to the height specified by {@link Renderer}.
	 */
	@Override
	public void render(Context context) {
		context.setHeight(renderer.getHeight());
	}

	/**
	 * Set the height of this component to the height specified by {@link Renderer}.
	 */
	@Override
	public void handleKey(Context context, int scancode) {
		context.setHeight(renderer.getHeight());
	}

	/**
	 * Updates the focus state.
	 * Set the height of this component to the height specified by {@link Renderer}.
	 */
	@Override
	public void handleButton (Context context, int button) {
		context.setHeight(renderer.getHeight());
		updateFocus(context,button);
	}

	/**
	 * Set the height of this component to the height specified by {@link Renderer}.
	 */
	@Override
	public void getHeight(Context context) {
		context.setHeight(renderer.getHeight());
	}

	/**
	 * Set the height of this component to the height specified by {@link Renderer}.
	 */
	@Override
	public void exit (Context context) {
		context.setHeight(renderer.getHeight());
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
	 * Reset focus state.
	 */
	protected void releaseFocus() {
		focus=false;
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
			handleFocus(context,focus&&context.hasFocus());
		}
	}
	
	/**
	 * Does nothing, called when the focus state changes.
	 * @param context the {@link Context} for the component
	 * @param focus the new foucs state
	 */
	protected void handleFocus (Context context, boolean focus) {
	}
}
