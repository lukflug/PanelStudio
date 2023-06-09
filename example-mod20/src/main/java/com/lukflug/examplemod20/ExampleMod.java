package com.lukflug.examplemod20;

import org.lwjgl.glfw.GLFW;

import com.lukflug.examplemod20.module.Category;
import com.lukflug.examplemod20.module.ClickGUIModule;
import com.lukflug.examplemod20.module.HUDEditorModule;
import com.lukflug.examplemod20.module.LogoModule;
import com.lukflug.examplemod20.module.TabGUIModule;
import com.lukflug.examplemod20.module.WatermarkModule;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;

public class ExampleMod implements ModInitializer {
	private static ClickGUI gui;
	private boolean inited=false;
	private final boolean keys[]=new boolean[266];
	
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
				for (int i=32;i<keys.length;i++) keys[i]=GLFW.glfwGetKey(MinecraftClient.getInstance().getWindow().getHandle(),i)==GLFW.GLFW_PRESS;
				gui=new ClickGUI();
				HudRenderCallback.EVENT.register((cli,tickDelta)->gui.render());
				inited=true;
			}
			for (int i=32;i<keys.length;i++) {
				if (keys[i]!=(GLFW.glfwGetKey(MinecraftClient.getInstance().getWindow().getHandle(),i)==GLFW.GLFW_PRESS)) {
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
