package com.lukflug.panelstudio.theme;

import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;

@FunctionalInterface
public interface ISwitchRendererProxy<T> extends ISwitchRenderer<T>,IButtonRendererProxy<T> {
	@Override
	public default Rectangle getOnField (Context context) {
		return getRenderer().getOnField(context);
	}
	
	@Override
	public default Rectangle getOffField (Context context) {
		return getRenderer().getOffField(context);
	}
	
	@Override
	public ISwitchRenderer<T> getRenderer();
}
