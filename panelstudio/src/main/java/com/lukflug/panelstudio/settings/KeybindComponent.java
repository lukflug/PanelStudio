package com.lukflug.panelstudio.settings;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.FocusableComponent;
import com.lukflug.panelstudio.theme.IRenderer;

/**
 * Component representing a keybind.
 * @author lukflug
 */
public class KeybindComponent extends FocusableComponent {
	/**
	 * The keybind in question.
	 */
	protected IKeybindSetting keybind;
	
	/**
	 * Constructor.
	 * @param renderer the {@link IRenderer} for the component
	 * @param keybind the keybind in question
	 */
	public KeybindComponent(IRenderer renderer, IKeybindSetting keybind) {
		super("Keybind: \u00A77",null,renderer);
		this.keybind=keybind;
	}
	
	/**
	 * Renders the keybind component.
	 */
	@Override
	public void render (Context context) {
		super.render(context);
		String text=title+keybind.getKeyName();
		if (hasFocus(context)) text=title+"...";
		renderer.renderTitle(context,text,hasFocus(context));
	}
	
	/**
	 * The keybind is reset, if focus is lost.
	 */
	@Override
	public void handleButton (Context context, int button) {
		super.handleButton(context,button);
		context.setHeight(renderer.getHeight(false));
		boolean isSelected=hasFocus(context);
		super.handleButton(context,button);
		if (isSelected && !hasFocus(context)) keybind.setKey(0);
	}
	
	/**
	 * If the keybind has focus and a key is pressed, the keybind is set and foucs is released.
	 */
	@Override
	public void handleKey (Context context, int scancode) {
		super.handleKey(context,scancode);
		if (hasFocus(context)) {
			keybind.setKey(scancode);
			releaseFocus();
		}
	}

	/**
	 * Reset the keybind, if the GUI is closed.
	 */
	@Override
	public void exit (Context context) {
		super.exit(context);
		if (hasFocus(context)) {
			keybind.setKey(0);
			releaseFocus();
		}
	}
}
