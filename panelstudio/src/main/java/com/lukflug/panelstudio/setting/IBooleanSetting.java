package com.lukflug.panelstudio.setting;

import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IToggleable;

/**
 * Interface representing boolean setting.
 * @author lukflug
 */
public interface IBooleanSetting extends ISetting<IBoolean>,IToggleable {
	@Override
	public default IBoolean getSettingState() {
		return this;
	}
	
	@Override
	public default Class<IBoolean> getSettingClass() {
		return IBoolean.class;
	}
}
