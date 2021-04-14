package com.lukflug.panelstudio.theme;

import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.setting.ILabeled;

public interface IRadioRenderer {
	public void renderItem (Context context, ILabeled[] items, boolean focus, int target, double state, boolean horizontal);
	
	public int getDefaultHeight (ILabeled[] items, boolean horizontal);
	
	public default Rectangle getItemRect (Context context, ILabeled[] items, int index, boolean horizontal) {
		Rectangle rect=context.getRect();
		if (horizontal) {
			return new Rectangle(rect.x+rect.width/items.length*index,rect.y,rect.width/items.length,rect.height);
		} else {
			return new Rectangle(rect.x,rect.y+rect.height/items.length*index,rect.width,rect.height/items.length);
		}
	}
}
