package com.lukflug.examplemod20.module;

import java.awt.Color;
import java.awt.Point;

import com.lukflug.examplemod20.setting.BooleanSetting;
import com.lukflug.examplemod20.setting.ColorSetting;
import com.lukflug.examplemod20.setting.StringSetting;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.hud.HUDList;
import com.lukflug.panelstudio.hud.ListComponent;

public class WatermarkModule extends Module {
	private static WatermarkModule instance;
	private static final ColorSetting color=new ColorSetting("Text Color","color","The color of the displayed text.",()->true,false,true,new Color(0,0,255),false);
	private static final BooleanSetting sortUp=new BooleanSetting("Sort Up","sortUp","Whether to align the text from the bottom up.",()->true,false);
	private static final BooleanSetting sortRight=new BooleanSetting("Sort Right","sortRight","Whether to align the text from right to left.",()->true,false);
	private static final StringSetting line1=new StringSetting("First Line","line1","The first line of text.",()->true,"PanelStudio");
	private static final StringSetting line2=new StringSetting("Second Line","line2","The second line of text.",()->true,"Example Mod");
	private static final StringSetting line3=new StringSetting("Third Line","line3","The third line of text.",()->true,"made by lukflug");
	
	public WatermarkModule() {
		super("Watermark","Module that displays text on HUD.",()->true,true);
		instance=this;
		settings.add(color);
		settings.add(sortUp);
		settings.add(sortRight);
		settings.add(line1);
		settings.add(line2);
		settings.add(line3);
	}

	public static IFixedComponent getComponent() {
		return new ListComponent(()->"Watermark",new Point(300,10),"watermark",new HUDList() {
			@Override
			public int getSize() {
				return 3;
			}

			@Override
			public String getItem(int index) {
				if (index==0) return line1.getValue();
				else if (index==1) return line2.getValue();
				else return line3.getValue();
			}

			@Override
			public Color getItemColor(int index) {
				return color.getValue();
			}

			@Override
			public boolean sortUp() {
				return sortUp.getValue();
			}

			@Override
			public boolean sortRight() {
				return sortRight.getValue();
			}
		},9,2);
	}
	
	public static IToggleable getToggle() {
		return instance.isEnabled();
	}
}
