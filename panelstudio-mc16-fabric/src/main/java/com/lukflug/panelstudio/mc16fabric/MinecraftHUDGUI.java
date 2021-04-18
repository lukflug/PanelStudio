package com.lukflug.panelstudio.mc16fabric;

import org.lwjgl.glfw.GLFW;

import com.lukflug.panelstudio.container.GUI;
import com.lukflug.panelstudio.hud.HUDGUI;

/**
 * An extension of {@link MinecraftGUI} for HUD editors.
 * @author lukflug
 */
public abstract class MinecraftHUDGUI extends MinecraftGUI {
	/**
	 * Whether {@link GUI#enter()} has been called.
	 */
	private boolean guiOpened=false;
	
	@Override
	public void enterGUI() {
		if (!getHUDGUI().getGUIVisibility().isOn()) getHUDGUI().getGUIVisibility().toggle();
		if (!getHUDGUI().getHUDVisibility().isOn()) getHUDGUI().getHUDVisibility().toggle();
		super.enterGUI();
	}
	
	@Override
	public void exitGUI() {
		if (getHUDGUI().getGUIVisibility().isOn()) getHUDGUI().getGUIVisibility().toggle();
		if (getHUDGUI().getHUDVisibility().isOn()) getHUDGUI().getHUDVisibility().toggle();
		super.exitGUI();
	}

	/**
	 * Open the HUD editor.
	 */
	public void enterHUDEditor() {
		if (getHUDGUI().getGUIVisibility().isOn()) getHUDGUI().getGUIVisibility().toggle();
		if (!getHUDGUI().getHUDVisibility().isOn()) getHUDGUI().getHUDVisibility().toggle();
		super.enterGUI();
	}
	
	@Override
	public void init() {
	}
	
	@Override
	public void removed() {
	}
	
	@Override
	protected void renderGUI() {
		if (!guiOpened) getGUI().enter();
		guiOpened=true;
		super.renderGUI();
	}
	
	/**
	 * Render function to be called even when the GUI is closed.
	 */
	public void render() {
		if (!getHUDGUI().getGUIVisibility().isOn() && !getHUDGUI().getHUDVisibility().isOn()) renderGUI();
	}
	
	/**
	 * Key event function to be called when the GUI is closed.
	 * @param scancode the key scancode
	 */
	public void handleKeyEvent (int scancode) {
		if (scancode!=GLFW.GLFW_KEY_ESCAPE && !getHUDGUI().getGUIVisibility().isOn() && !getHUDGUI().getGUIVisibility().isOn()) getHUDGUI().handleKey(scancode);
	}
	
	/**
	 * Get the {@link HUDGUI} to be rendered.
	 * @return current GUI
	 */
	protected abstract HUDGUI getHUDGUI();

	@Override
	protected final GUI getGUI() {
		return getHUDGUI();
	}
}
