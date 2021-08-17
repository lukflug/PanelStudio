package com.lukflug.examplemod8forge.module;

import org.lwjgl.input.Keyboard;

import com.lukflug.examplemod8forge.setting.BooleanSetting;
import com.lukflug.examplemod8forge.setting.KeybindSetting;
import com.lukflug.panelstudio.base.ConstantToggleable;

public class HUDEditorModule extends Module {
	public static final BooleanSetting showHUD=new BooleanSetting("Show HUD Panels","showHUD","Whether to show the HUD panels in the ClickGUI.",new ConstantToggleable(true),true);
	public static final KeybindSetting keybind=new KeybindSetting("Keybind","keybind","The key to toggle the module.",new ConstantToggleable(true),Keyboard.KEY_P);
	
	public HUDEditorModule() {
		super("HUDEditor","Module containing HUDEditor settings.",new ConstantToggleable(true),false);
		settings.add(showHUD);
		settings.add(keybind);
	}
}
