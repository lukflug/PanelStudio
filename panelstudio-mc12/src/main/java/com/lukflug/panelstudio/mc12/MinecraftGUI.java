package com.lukflug.panelstudio.mc12;

import java.awt.Point;

import org.lwjgl.input.Mouse;

import com.lukflug.panelstudio.ClickGUI;
import com.lukflug.panelstudio.Interface;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

/**
 * Implementation of Minecraft's GuiScreen that renders a PanelStudio GUI.
 * @author lukflug
 */
public abstract class MinecraftGUI extends GuiScreen {
	/**
	 * The current mouse position.
	 */
	private Point mouse=new Point();
	/**
	 * Current left mouse button state.
	 */
	private boolean lButton=false;
	/**
	 * Current right mouse button state.
	 */
	private boolean rButton=false;
	
	/**
	 * Displays the GUI.
	 */
	public void enterGUI() {
		Minecraft.getMinecraft().displayGuiScreen(this);
		getGUI().enter();
	}
	
	/**
	 * Closes the GUI.
	 */
	public void exitGUI() {
		getGUI().exit();
		Minecraft.getMinecraft().displayGuiScreen(null);
	}
	
	/**
	 * Updates the matrix buffers and renders the GUI.
	 */
	protected void renderGUI() {
		getInterface().getMatrices();
		GLInterface.begin();
		getGUI().render();
		GLInterface.end();
	}
	
	/**
	 * Draws the screen, updates the mouse position and handles scroll events.
	 * @param mouseX current mouse x position
	 * @param mouseY current mouse y position
	 * @param partialTicks partial tick count
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		mouse=new Point(mouseX,mouseY);
		renderGUI();
		int scroll=Mouse.getDWheel();
		if (scroll!=0) {
			if (scroll>0) getGUI().handleScroll(-getScrollSpeed());
			else getGUI().handleScroll(getScrollSpeed());
		}
	}

	/**
	 * Updates {@link #lButton} and {@link #rButton}.
	 * @param mouseX current mouse x position
	 * @param mouseY current mouse y position
	 * @param clickedButton number of button being clicked
	 */
	@Override
	public void mouseClicked(int mouseX, int mouseY, int clickedButton) {
		mouse=new Point(mouseX,mouseY);
		switch (clickedButton) {
		case Interface.LBUTTON:
			lButton=true;
			break;
		case Interface.RBUTTON:
			rButton=true;
			break;
		}
		getGUI().handleButton(clickedButton);
	}

	/**
	 * Updates {@link #lButton} and {@link #rButton}.
	 * @param mouseX current mouse x position
	 * @param mouseY current mouse y position
	 * @param releaseButton number of button being released
	 */
	@Override
	public void mouseReleased(int mouseX, int mouseY, int releaseButton) {
		mouse=new Point(mouseX,mouseY);
		switch (releaseButton) {
		case Interface.LBUTTON:
			lButton=false;
		break;
		case Interface.RBUTTON:
			rButton=false;
			break;
		}
		getGUI().handleButton(releaseButton);
	}
	
	/**
	 * Handles the current keys being typed.
	 * @param typedChar character being typed
	 * @param keyCode scancode of key being typed
	 */
	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		if (keyCode == 1) exitGUI();
		else getGUI().handleKey(keyCode);
	}

	/**
	 * Returns false.
	 * @return returns false
	 */
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	/**
	 * Get the {@link ClickGUI} to be rendered.
	 * @return current ClickGUI
	 */
	protected abstract ClickGUI getGUI();
	/**
	 * Get current {@link GUIInterface}.
	 * @return the current interface
	 */
	protected abstract GUIInterface getInterface();
	/**
	 * Get current scroll speed.
	 * @return the scroll speed
	 */
	protected abstract int getScrollSpeed();
	
	/**
	 * Implementation of {@link GLInterface} to be used with {@link MinecraftGUI}
	 * @author lukflug
	 */
	public abstract class GUIInterface extends GLInterface {
		public GUIInterface (boolean clipX) {
			super(clipX);
		}
		
		@Override
		public boolean getButton(int button) {
			switch (button) {
			case Interface.LBUTTON:
				return lButton;
			case Interface.RBUTTON:
				return rButton;
			}
			return false;
		}

		@Override
		public Point getMouse() {
			return new Point(mouse);
		}

		@Override
		protected float getZLevel() {
			return zLevel;
		}
	}
}
