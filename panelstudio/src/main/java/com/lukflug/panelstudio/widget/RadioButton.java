package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.component.FocusableComponent;
import com.lukflug.panelstudio.setting.AnimatedEnum;
import com.lukflug.panelstudio.setting.IEnumSetting;
import com.lukflug.panelstudio.setting.ILabeled;
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
		ILabeled[] values=IEnumSetting.getVisibleValues(setting);
		String compare=setting.getValueName();
		int value=-1;
		for (int i=0;i<values.length;i++) {
			if (values[i].getDisplayName().equals(compare)) {
				value=i;
				break;
			}
		}
		renderer.renderItem(context,values,hasFocus(context),value,animation.getValue(),horizontal);
	}
	
	@Override
	public void handleButton (Context context, int button) {
		super.handleButton(context,button);
		if (button==IInterface.LBUTTON && context.isClicked(button)) {
			int index=0;
			ILabeled[] values=setting.getAllowedValues();
			ILabeled[] visibleValues=IEnumSetting.getVisibleValues(setting);
			for (int i=0;i<values.length;i++) {
				if (values[i].isVisible().isOn()) {
					if (renderer.getItemRect(context,visibleValues,index,horizontal).contains(context.getInterface().getMouse())) {
						setting.setValueIndex(i);
						return;
					}
					index++;
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
		return renderer.getDefaultHeight(IEnumSetting.getVisibleValues(setting),horizontal);
	}
	
	protected abstract boolean isUpKey (int key);
	
	protected abstract boolean isDownKey (int key);
}
