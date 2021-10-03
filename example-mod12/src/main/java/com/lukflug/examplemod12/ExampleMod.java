package com.lukflug.examplemod12;

import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import com.lukflug.examplemod12.module.Category;
import com.lukflug.examplemod12.module.ClickGUIModule;
import com.lukflug.examplemod12.module.HUDEditorModule;
import com.lukflug.examplemod12.module.LogoModule;
import com.lukflug.examplemod12.module.TabGUIModule;
import com.lukflug.examplemod12.module.WatermarkModule;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

@Mod(modid=ExampleMod.MODID,name=ExampleMod.NAME,version=ExampleMod.VERSION)
public class ExampleMod {
	public static final String MODID="example-mod12";
	public static final String NAME="PanelStudio-MC12 Example Mod";
	public static final String VERSION="0.2.1";
	public static Logger logger;
	private static ClickGUI gui;

	@EventHandler
	public void preInit (FMLPreInitializationEvent event) {
		logger = event.getModLog();
	}

	@EventHandler
	public void init (FMLInitializationEvent event) {
		Category.init();
		Category.OTHER.modules.add(new ClickGUIModule());
		Category.OTHER.modules.add(new HUDEditorModule());
		Category.HUD.modules.add(new TabGUIModule());
		Category.HUD.modules.add(new WatermarkModule());
		Category.HUD.modules.add(new LogoModule());
		gui=new ClickGUI();
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onRender (RenderGameOverlayEvent.Post event) {
		if (event.getType()==RenderGameOverlayEvent.ElementType.HOTBAR) gui.render();
	}
	
	@SubscribeEvent
	public void onKeyInput (KeyInputEvent event) {
		if (Keyboard.isKeyDown(ClickGUIModule.keybind.getKey())) gui.enterGUI();
		if (Keyboard.isKeyDown(HUDEditorModule.keybind.getKey())) gui.enterHUDEditor();
		if (Keyboard.getEventKeyState()) gui.handleKeyEvent(Keyboard.getEventKey());
	}
}
