package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.FocusableComponent;
import com.lukflug.panelstudio.setting.IBooleanSetting;
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
	protected IButtonRenderer<IBoolean> renderer;
	
	/**
	 * Constructor.
	 * @param title the caption for this component
	 * @param description the description for this component
	 * @param visible whether this component is visible
	 * @param toggle the toggle
	 * @param renderer the renderer for this component
	 */
	public ToggleButton (String title, String description, IBoolean visible, IToggleable toggle, IButtonRenderer<IBoolean> renderer) {
		super(title,description,visible);
		this.toggle=toggle;
		this.renderer=renderer;
		if (this.toggle==null) this.toggle=new SimpleToggleable(false);
	}
	
	/**
	 * Constructor using boolean setting.
	 * @param setting the setting in question
	 * @param renderer the renderer for this component
	 */
	public ToggleButton (IBooleanSetting setting, IButtonRenderer<IBoolean> renderer) {
		this(setting.getDescription(),setting.getDescription(),setting.isVisible(),setting,renderer);
	}
	
	@Override
	public void render (Context context) {
		super.render(context);
		renderer.renderButton(context,getTitle(),hasFocus(context),toggle);
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
