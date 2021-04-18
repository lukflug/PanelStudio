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
			int start=(int)Math.round(rect.width/(double)items.length*index);
			int end=(int)Math.round(rect.width/(double)items.length*(index+1));
			return new Rectangle(rect.x+start,rect.y,end-start,rect.height);
		} else {
			int start=(int)Math.round(rect.height/(double)items.length*index);
			int end=(int)Math.round(rect.height/(double)items.length*(index+1));
			return new Rectangle(rect.x,rect.y+start,rect.width,end-start);
		}
	}
}
