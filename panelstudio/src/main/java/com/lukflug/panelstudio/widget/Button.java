package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.component.FocusableComponent;
import com.lukflug.panelstudio.theme.IButtonRenderer;

/**
 * Button widget class.
 * @author lukflug
 */
public class Button extends FocusableComponent {
	/**
	 * Renderer for this component.
	 */
	protected IButtonRenderer<Void> renderer;

	/**
	 * Constructor.
	 * @param title the caption for this component
	 * @param description the description for this component
	 * @param visible whether this component is visible
	 * @param renderer the renderer for this component
	 */
	public Button(String title, String description, IBoolean visible, IButtonRenderer<Void> renderer) {
		super(title,description,visible);
		this.renderer=renderer;
	}
	
	@Override
	public void render (Context context) {
		super.render(context);
		renderer.renderButton(context,title,hasFocus(context),null);
	}

	@Override
	protected int getHeight() {
		return renderer.getDefaultHeight();
	}
}
