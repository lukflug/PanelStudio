package com.lukflug.examplemod8fabric;

import org.lwjgl.input.Keyboard;

import com.lukflug.examplemod8fabric.module.Category;
import com.lukflug.examplemod8fabric.module.ClickGUIModule;
import com.lukflug.examplemod8fabric.module.HUDEditorModule;
import com.lukflug.examplemod8fabric.module.LogoModule;
import com.lukflug.examplemod8fabric.module.TabGUIModule;
import com.lukflug.examplemod8fabric.module.WatermarkModule;

import net.fabricmc.api.ModInitializer;
import net.legacyfabric.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.legacyfabric.fabric.api.client.rendering.v1.HudRenderCallback;

public class ExampleMod implements ModInitializer {
	private static ClickGUI gui;
	private boolean inited=false;
	private final boolean keys[]=new boolean[Keyboard.KEYBOARD_SIZE];
	
	@Override
	public void onInitialize() {
		Category.init();
		Category.OTHER.modules.add(new ClickGUIModule());
		Category.OTHER.modules.add(new HUDEditorModule());
		Category.HUD.modules.add(new TabGUIModule());
		Category.HUD.modules.add(new WatermarkModule());
		Category.HUD.modules.add(new LogoModule());
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (!inited) {
				for (int i=0;i<keys.length;i++) keys[i]=Keyboard.isKeyDown(i);
				gui=new ClickGUI();
				HudRenderCallback.EVENT.register((cli,tickDelta)->gui.render());
				inited=true;
			}
			for (int i=0;i<keys.length;i++) {
				if (keys[i]!=Keyboard.isKeyDown(i)) {
					keys[i]=!keys[i];
					if (keys[i]) {
						if (i==ClickGUIModule.keybind.getKey()) gui.enterGUI();
						if (i==HUDEditorModule.keybind.getKey()) gui.enterHUDEditor();
						gui.handleKeyEvent(i);
					}
				}
			}
		});
	}
}
