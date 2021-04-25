package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.component.FocusableComponent;
import com.lukflug.panelstudio.setting.AnimatedEnum;
import com.lukflug.panelstudio.setting.IEnumSetting;
import com.lukflug.panelstudio.theme.IRadioRenderer;

public abstract class RadioButton extends FocusableComponent {
	protected IEnumSetting setting;
	protected IRadioRenderer renderer;
	protected AnimatedEnum animation;
	protected final boolean horizontal;

	public RadioButton (IEnumSetting setting, IRadioRenderer renderer, Animation animation, boolean horizontal) {
		super(setting);
		this.setting=setting;
		this.renderer=renderer;
		this.animation=new AnimatedEnum(setting,animation);
		this.horizontal=horizontal;
	}
	
	@Override
	public void render (Context context) {
		super.render(context);
		renderer.renderItem(context,setting.getAllowedValues(),hasFocus(context),(int)setting.getValueIndex(),animation.getValue(),horizontal);
	}
	
	@Override
	public void handleButton (Context context, int button) {
		super.handleButton(context,button);
		if (button==IInterface.LBUTTON && context.isClicked()) {
			for (int i=0;i<setting.getAllowedValues().length;i++) {
				if (renderer.getItemRect(context,setting.getAllowedValues(),i,horizontal).contains(context.getInterface().getMouse())) {
					setting.setValueIndex(i);
					return;
				}
			}
		}
	}
	
	@Override
	public void handleKey (Context context, int key) {
		super.handleKey(context,key);
		if (context.hasFocus()) {
			if (isUpKey(key)) setting.decrement();
			else if (isDownKey(key)) setting.increment();
		}
	}

	@Override
	protected int getHeight() {
		return renderer.getDefaultHeight(setting.getAllowedValues(),horizontal);
	}
	
	protected abstract boolean isUpKey (int key);
	
	protected abstract boolean isDownKey (int key);
}
