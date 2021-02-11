package com.lukflug.panelstudio.mc8fabric;

import org.lwjgl.input.Keyboard;

import com.lukflug.panelstudio.ClickGUI;
import com.lukflug.panelstudio.hud.HUDClickGUI;
import net.minecraft.client.MinecraftClient;

/**
 * Class designed for GUIs with HUDs.
 * Ported to 1.8 legacy fabric by
 * @author NirvanaNevermind
 * @author lukflug
 */
public abstract class MinecraftHUDGUI extends MinecraftGUI {
	protected boolean hudEditor=false;
	
	@Override
	public void enterGUI() {
		hudEditor=false;
		super.enterGUI();
	}
	
	@Override
	public void exitGUI() {
		hudEditor=false;
		super.exitGUI();
	}

	/**
	 * Open the HUD editor.
	 */
	public void enterHUDEditor() {
		hudEditor=true;
		if (getHUDGUI().isOn()) getHUDGUI().toggle();
		MinecraftClient.getInstance().openScreen(this);
	}
	
	/**
	 * Render function to be called when the GUI is closed to render the HUD.
	 */
	public void render() {
		if (!getHUDGUI().isOn() && !hudEditor) renderGUI();
	}
	
	/**
	 * Key event function to be called when the GUI is closed.
	 * @param scancode the key scancode
	 */
	public void handleKeyEvent (int scancode) {
		if (scancode!=Keyboard.KEY_ESCAPE && !getHUDGUI().isOn() && !hudEditor) getHUDGUI().handleKey(scancode);
	}
	
	/**
	 * Get the {@link HUDClickGUI} to be rendered.
	 * @return current ClickGUI
	 */
	protected abstract HUDClickGUI getHUDGUI();

	@Override
	protected GUI getGUI() {
		return getHUDGUI();
	}
}
