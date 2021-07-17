package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.component.FocusableComponent;
import com.lukflug.panelstudio.setting.IKeybindSetting;
import com.lukflug.panelstudio.theme.IButtonRenderer;

/**
 * Button representing a keybind.
 * @author lukflug
 */
public class KeybindComponent extends FocusableComponent {
	/**
	 * The keybind in question.
	 */
	protected IKeybindSetting keybind;
	/**
	 * The renderer to be used.
	 */
	protected IButtonRenderer<String> renderer;
	
	/**
	 * Constructor.
	 * @param keybind the keybind in question
	 * @param renderer the renderer for this component
	 */
	public KeybindComponent (IKeybindSetting keybind, IButtonRenderer<String> renderer) {
		super(keybind);
		this.keybind=keybind;
		this.renderer=renderer;
	}
	
	@Override
	public void render (Context context) {
		super.render(context);
		renderer.renderButton(context,getTitle(),hasFocus(context),keybind.getKeyName());
	}
	
	@Override
	public void handleKey (Context context, int scancode) {
		super.handleKey(context,scancode);
		if (hasFocus(context)) {
			keybind.setKey(transformKey(scancode));
			releaseFocus();
		}
	}

	@Override
	public void exit() {
		super.exit();
		releaseFocus();
	}

	@Override
	protected int getHeight() {
		return renderer.getDefaultHeight();
	}
	
	/**
	 * Method used to transform input key on keybind assign.
	 * Returns same key by default.
	 * @param scancode the input key
	 * @return the resulting key
	 */
	protected int transformKey (int scancode) {
		return scancode;
	}
}
