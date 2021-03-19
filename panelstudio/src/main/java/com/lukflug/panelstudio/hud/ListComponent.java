package com.lukflug.panelstudio.hud;

import java.awt.Dimension;
import java.awt.Point;

import com.lukflug.panelstudio.setting.ILabeled;

public class ListComponent extends HUDComponent {
	protected HUDList list;
	
	public ListComponent (ILabeled label, Point position, Dimension size, String configName, HUDList list) {
		super(label,position,size,configName);
		this.list=list;
	}
}
