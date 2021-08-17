package com.lukflug.examplemod8forge.module;

import org.lwjgl.input.Keyboard;

import com.lukflug.examplemod8forge.setting.EnumSetting;
import com.lukflug.examplemod8forge.setting.IntegerSetting;
import com.lukflug.examplemod8forge.setting.KeybindSetting;
import com.lukflug.panelstudio.base.ConstantToggleable;

public class ClickGUIModule extends Module {
	public static final EnumSetting<ColorModel> colorModel=new EnumSetting<ColorModel>("Color Model","colorModel","Whether to use RGB or HSB.",new ConstantToggleable(true),ColorModel.RGB,ColorModel.class);
	public static final IntegerSetting rainbowSpeed=new IntegerSetting("Rainbow Speed","rainbowSpeed","The speed of the color hue cycling.",new ConstantToggleable(true),1,100,32);
	public static final IntegerSetting scrollSpeed=new IntegerSetting("Scroll Speed","scrollSpeed","The speed of scrolling.",new ConstantToggleable(true),0,20,10);
	public static final IntegerSetting animationSpeed=new IntegerSetting("Animation Speed","animationSpeed","The speed of GUI animations.",new ConstantToggleable(true),0,1000,200);
	public static final EnumSetting<Theme> theme=new EnumSetting<Theme>("Theme","theme","What theme to use.",new ConstantToggleable(true),Theme.GameSense,Theme.class);
	public static final EnumSetting<Layout> layout=new EnumSetting<Layout>("Layout","layout","What layout to use.",new ConstantToggleable(true),Layout.ClassicPanel,Layout.class);
	public static final KeybindSetting keybind=new KeybindSetting("Keybind","keybind","The key to toggle the module.",new ConstantToggleable(true),Keyboard.KEY_O);
	
	public ClickGUIModule() {
		super("ClickGUI","Module containing ClickGUI settings.",new ConstantToggleable(true),false);
		settings.add(colorModel);
		settings.add(rainbowSpeed);
		settings.add(scrollSpeed);
		settings.add(animationSpeed);
		settings.add(theme);
		settings.add(layout);
		settings.add(keybind);
	}
	
	public enum ColorModel {
		RGB,HSB;
	}
	
	public enum Theme {
		Clear,GameSense,Rainbow,Windows31,Impact;
	}
	
	public enum Layout {
		ClassicPanel,PopupPanel,DraggablePanel,SinglePanel,PanelMenu,ColorPanel,CSGOHorizontal,CSGOVertical,CSGOCategory,SearchableCSGO;
	}
}
