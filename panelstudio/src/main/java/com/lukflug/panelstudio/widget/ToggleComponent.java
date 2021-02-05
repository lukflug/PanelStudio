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
 * Component representing a boolean-valued setting.
 * @author lukflug
 */
public class ToggleComponent extends FocusableComponent {
	/**
	 * Setting to be toggled by left click.
	 */
	protected IToggleable left;
	/**
	 * Setting to be toggled by right click.
	 */
	protected IToggleable right;
	/**
	 * Renderer for this component.
	 */
	protected IButtonRenderer<IBoolean> renderer;
	
	/**
	 * Constructor.
	 * @param title the caption for this component
	 * @param description the description for this component
	 * @param visible whether this component is visible
	 * @param left the left toggle
	 * @param right the right toggle
	 * @param renderer the renderer for this component
	 */
	public ToggleComponent(String title, String description, IBoolean visible, IToggleable left, IToggleable right, IButtonRenderer<IBoolean> renderer) {
		super(title,description,visible);
		this.left=left;
		this.right=right;
		this.renderer=renderer;
		if (this.left==null) this.left=new SimpleToggleable(false);
		if (this.right==null) this.right=new SimpleToggleable(false);
	}
	
	/**
	 * Constructor using boolean setting.
	 * @param setting the setting in question
	 * @param renderer the renderer for this component
	 */
	public ToggleComponent (IBooleanSetting setting, IButtonRenderer<IBoolean> renderer) {
		this(setting.getDescription(),setting.getDescription(),setting.isVisible(),setting,null,renderer);
	}
	
	@Override
	public void render (Context context) {
		super.render(context);
		renderer.renderButton(context,title,hasFocus(context),left);
	}
	
	@Override
	public void handleButton (Context context, int button) {
		super.handleButton(context,button);
		if (button==IInterface.LBUTTON && context.isClicked()) {
			left.toggle();
		} else if (button==IInterface.RBUTTON && context.getInterface().getButton(IInterface.RBUTTON) && context.isHovered()) {
			right.toggle();
		}
	}

	@Override
	protected int getHeight() {
		return renderer.getDefaultHeight();
	}
}
