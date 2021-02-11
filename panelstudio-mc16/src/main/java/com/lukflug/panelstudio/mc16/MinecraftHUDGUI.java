package com.lukflug.panelstudio.mc16;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.input.Keyboard;

import com.lukflug.panelstudio.container.GUI;

import net.minecraft.client.MinecraftClient;

/**
 * Class designed for GUIs with HUDs.
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
	public void onClose() {
		hudEditor=false;
		super.onClose();
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
		if (scancode!=GLFW.GLFW_KEY_ESCAPE && !getHUDGUI().isOn() && !hudEditor) getHUDGUI().handleKey(scancode);
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
