package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.component.FocusableComponent;
import com.lukflug.panelstudio.setting.ILabeled;
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
	 * @param label the label for the component
	 * @param renderer the renderer for this component
	 */
	public Button(ILabeled label, IButtonRenderer<Void> renderer) {
		super(label);
		this.renderer=renderer;
	}
	
	@Override
	public void render (Context context) {
		super.render(context);
		renderer.renderButton(context,getTitle(),hasFocus(context),null);
		System.err.println(context.getSize().height);
	}

	@Override
	protected int getHeight() {
		return renderer.getDefaultHeight();
	}
}
