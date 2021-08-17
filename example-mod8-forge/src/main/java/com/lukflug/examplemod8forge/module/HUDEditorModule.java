package com.lukflug.examplemod8forge.module;

import org.lwjgl.input.Keyboard;

import com.lukflug.examplemod8forge.setting.BooleanSetting;
import com.lukflug.examplemod8forge.setting.KeybindSetting;

public class HUDEditorModule extends Module {
	public static final BooleanSetting showHUD=new BooleanSetting("Show HUD Panels","showHUD","Whether to show the HUD panels in the ClickGUI.",()->true,true);
	public static final KeybindSetting keybind=new KeybindSetting("Keybind","keybind","The key to toggle the module.",()->true,Keyboard.KEY_P);
	
	public HUDEditorModule() {
		super("HUDEditor","Module containing HUDEditor settings.",()->true,false);
		settings.add(showHUD);
		settings.add(keybind);
	}
}
