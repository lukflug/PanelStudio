package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.FocusableComponent;
import com.lukflug.panelstudio.setting.IBooleanSetting;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.theme.IButtonRenderer;

/**
 * Button with two values that can be toggled by the left mouse button.
 * @author lukflug
 */
public class ToggleButton extends FocusableComponent {
	/**
	 * Setting to be toggled by left click.
	 */
	protected IToggleable toggle;
	/**
	 * Renderer for this component.
	 */
	protected IButtonRenderer<Boolean> renderer;
	
	/**
	 * Constructor.
	 * @param label the label for the component
	 * @param toggle the toggle
	 * @param renderer the renderer for this component
	 */
	public ToggleButton (ILabeled label, IToggleable toggle, IButtonRenderer<Boolean> renderer) {
		super(label);
		this.toggle=toggle;
		this.renderer=renderer;
		if (this.toggle==null) this.toggle=new SimpleToggleable(false);
	}
	
	/**
	 * Constructor using boolean setting.
	 * @param setting the setting in question
	 * @param renderer the renderer for this component
	 */
	public ToggleButton (IBooleanSetting setting, IButtonRenderer<Boolean> renderer) {
		this(setting,setting,renderer);
	}
	
	@Override
	public void render (Context context) {
		super.render(context);
		renderer.renderButton(context,getTitle(),hasFocus(context),toggle.isOn());
	}
	
	@Override
	public void handleButton (Context context, int button) {
		super.handleButton(context,button);
		if (button==IInterface.LBUTTON && context.isClicked()) {
			toggle.toggle();
		}
	}

	@Override
	protected int getHeight() {
		return renderer.getDefaultHeight();
	}
}
