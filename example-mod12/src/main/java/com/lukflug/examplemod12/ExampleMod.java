package com.lukflug.examplemod12;

import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import com.lukflug.examplemod12.module.Category;
import com.lukflug.examplemod12.module.ClickGUIModule;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
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
	public static final String VERSION="0.2.0";
	public static Logger logger;
	private static final KeyBinding guiBind=new KeyBinding("Open ClickGUI",Keyboard.KEY_O,"PanelStudio Example");
	private static ClickGUI gui;

	@EventHandler
	public void preInit (FMLPreInitializationEvent event) {
		logger = event.getModLog();
	}

	@EventHandler
	public void init (FMLInitializationEvent event) {
		Category.init();
		Category.OTHER.modules.add(new ClickGUIModule());
		ClientRegistry.registerKeyBinding(guiBind);
		gui=new ClickGUI();
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onKeyInput (KeyInputEvent event) {
		if (guiBind.isPressed()) gui.enterGUI();
	}
}
