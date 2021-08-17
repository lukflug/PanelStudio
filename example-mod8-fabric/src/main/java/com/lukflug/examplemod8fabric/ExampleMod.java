package com.lukflug.examplemod8fabric;

import com.lukflug.examplemod8fabric.module.Category;
import com.lukflug.examplemod8fabric.module.ClickGUIModule;
import com.lukflug.examplemod8fabric.module.HUDEditorModule;
import com.lukflug.examplemod8fabric.module.LogoModule;
import com.lukflug.examplemod8fabric.module.TabGUIModule;
import com.lukflug.examplemod8fabric.module.WatermarkModule;

import net.fabricmc.api.ModInitializer;
import net.legacyfabric.fabric.api.client.rendering.v1.HudRenderCallback;

public class ExampleMod implements ModInitializer {
	private static ClickGUI gui;
	
	@Override
	public void onInitialize() {
		Category.init();
		Category.OTHER.modules.add(new ClickGUIModule());
		Category.OTHER.modules.add(new HUDEditorModule());
		Category.HUD.modules.add(new TabGUIModule());
		Category.HUD.modules.add(new WatermarkModule());
		Category.HUD.modules.add(new LogoModule());
		gui=new ClickGUI();
		HudRenderCallback.EVENT.register((client,tickDelta)->gui.render());
	}
	
	/*@SubscribeEvent
	public void onKeyInput (KeyInputEvent event) {
		if (Keyboard.isKeyDown(ClickGUIModule.keybind.getKey())) gui.enterGUI();
		if (Keyboard.isKeyDown(HUDEditorModule.keybind.getKey())) gui.enterHUDEditor();
		if (Keyboard.getEventKeyState()) gui.handleKeyEvent(Keyboard.getEventKey());
	}*/
}
