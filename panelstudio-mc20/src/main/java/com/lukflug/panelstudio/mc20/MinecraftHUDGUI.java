package com.lukflug.panelstudio.mc20;

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
		if (!getGUI().getGUIVisibility().isOn()) getGUI().getGUIVisibility().toggle();
		if (!getGUI().getHUDVisibility().isOn()) getGUI().getHUDVisibility().toggle();
		super.enterGUI();
	}
	
	@Override
	public void exitGUI() {
		if (getGUI().getGUIVisibility().isOn()) getGUI().getGUIVisibility().toggle();
		if (getGUI().getHUDVisibility().isOn()) getGUI().getHUDVisibility().toggle();
		super.exitGUI();
	}

	/**
	 * Open the HUD editor.
	 */
	public void enterHUDEditor() {
		if (getGUI().getGUIVisibility().isOn()) getGUI().getGUIVisibility().toggle();
		if (!getGUI().getHUDVisibility().isOn()) getGUI().getHUDVisibility().toggle();
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
		if (!getGUI().getGUIVisibility().isOn() && !getGUI().getHUDVisibility().isOn()) renderGUI();
	}
	
	/**
	 * Key event function to be called when the GUI is closed.
	 * @param scancode the key scancode
	 */
	public void handleKeyEvent (int scancode) {
		if (scancode!=GLFW.GLFW_KEY_ESCAPE && !getGUI().getGUIVisibility().isOn() && !getGUI().getGUIVisibility().isOn()) getGUI().handleKey(scancode);
	}
	
	@Override
	protected abstract HUDGUI getGUI();
}
