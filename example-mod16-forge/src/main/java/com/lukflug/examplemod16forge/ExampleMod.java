package com.lukflug.examplemod16forge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import com.lukflug.examplemod16forge.module.Category;
import com.lukflug.examplemod16forge.module.ClickGUIModule;
import com.lukflug.examplemod16forge.module.HUDEditorModule;
import com.lukflug.examplemod16forge.module.LogoModule;
import com.lukflug.examplemod16forge.module.TabGUIModule;
import com.lukflug.examplemod16forge.module.WatermarkModule;

import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("example-mod16-forge")
public class ExampleMod {
	public static Logger logger=LogManager.getLogger();
	private static ClickGUI gui;
	private boolean inited=false;
	
	public ExampleMod() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onRender (RenderGameOverlayEvent.Post event) {
		if (inited) {
			if (event.getType()==RenderGameOverlayEvent.ElementType.HOTBAR) gui.render();
		} else {
			Category.init();
			Category.OTHER.modules.add(new ClickGUIModule());
			Category.OTHER.modules.add(new HUDEditorModule());
			Category.HUD.modules.add(new TabGUIModule());
			Category.HUD.modules.add(new WatermarkModule());
			Category.HUD.modules.add(new LogoModule());
			gui=new ClickGUI();
			inited=true;
		}
	}
	
	@SubscribeEvent
	public void onKeyInput (KeyInputEvent event) {
		if (inited && event.getAction()==GLFW.GLFW_PRESS) {
			if (event.getKey()==ClickGUIModule.keybind.getKey()) gui.enterGUI();
			if (event.getKey()==HUDEditorModule.keybind.getKey()) gui.enterHUDEditor();
			gui.handleKeyEvent(event.getKey());
		}
	}
}
