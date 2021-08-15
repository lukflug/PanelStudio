package com.lukflug.panelstudio.theme;

import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.setting.ILabeled;

/**
 * Proxy redirecting calls
 * @author lukflug
 */
@FunctionalInterface
public interface IRadioRendererProxy extends IRadioRenderer {
	@Override
	public default void renderItem (Context context, ILabeled[] items, boolean focus, int target, double state, boolean horizontal) {
		getRenderer().renderItem(context,items,focus,target,state,horizontal);
	}
	
	@Override
	public default int getDefaultHeight (ILabeled[] items, boolean horizontal) {
		return getRenderer().getDefaultHeight(items,horizontal);
	}
	
	@Override
	public default Rectangle getItemRect (Context context, ILabeled[] items, int index, boolean horizontal) {
		return getRenderer().getItemRect(context,items,index,horizontal);
	}
	
	/**
	 * The renderer to be redirected to.
	 * @return the renderer
	 */
	public IRadioRenderer getRenderer();
}
