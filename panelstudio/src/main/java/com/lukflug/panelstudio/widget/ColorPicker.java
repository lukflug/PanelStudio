package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.component.FocusableComponent;
import com.lukflug.panelstudio.setting.IColorSetting;
import com.lukflug.panelstudio.theme.IColorPickerRenderer;

public class ColorPicker extends FocusableComponent {
	protected IColorSetting setting;
	protected IColorPickerRenderer renderer;
	protected boolean dragging=false;

	public ColorPicker (IColorSetting setting, IColorPickerRenderer renderer) {
		super(setting);
		this.setting=setting;
		this.renderer=renderer;
	}
	
	@Override
	public void render (Context context) {
		super.render(context);
		if (dragging && context.getInterface().getButton(IInterface.LBUTTON)) setting.setValue(renderer.transformPoint(context,setting.getColor(),context.getInterface().getMouse()));
		renderer.renderPicker(context,hasFocus(context),setting.getColor());
	}
	
	@Override
	public void handleButton (Context context, int button) {
		super.handleButton(context,button);
		if (button==IInterface.LBUTTON && context.isClicked(button)) dragging=true;
		else if (!context.getInterface().getButton(IInterface.LBUTTON)) dragging=false;
	}

	@Override
	public void getHeight (Context context) {
		context.setHeight(renderer.getDefaultHeight(context.getSize().width));
	}
	
	@Override
	public void exit() {
		super.exit();
		dragging=false;
	}

	@Override
	protected int getHeight() {
		return 0;
	}
}
