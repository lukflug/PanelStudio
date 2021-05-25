package com.lukflug.panelstudio.mc8fabric;

import java.awt.Point;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.container.GUI;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

/**
 * Implementation of Minecraft's {@link Screen} that renders a PanelStudio GUI.
 * @author lukflug
 */
public abstract class MinecraftGUI extends Screen {
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
	 * Last rendering time.
	 */
	private long lastTime;
	
	/**
	 * Displays the GUI.
	 */
	public void enterGUI() {
		MinecraftClient.getInstance().openScreen(this);
	}
	
	/**
	 * Closes the GUI.
	 */
	public void exitGUI() {
		MinecraftClient.getInstance().openScreen(null);
	}
	
	/**
	 * Renders the GUI.
	 */
	protected void renderGUI() {
		lastTime=System.currentTimeMillis();
		getInterface().begin(true);
		getGUI().render();
		getInterface().end(true);
	}
	
	@Override
	public void init() {
		getGUI().enter();
	}
	
	@Override
	public void removed() {
		getGUI().exit();
	}
	
	@Override
	public void render (int mouseX, int mouseY, float partialTicks) {
		mouse=getInterface().screenToGui(new Point(Mouse.getX(),Mouse.getY()));
		renderGUI();
		int scroll=Mouse.getDWheel();
		if (scroll!=0) {
			if (scroll>0) getGUI().handleScroll(-getScrollSpeed());
			else getGUI().handleScroll(getScrollSpeed());
		}
	}

	@Override
	public void mouseClicked (int mouseX, int mouseY, int clickedButton) {
		mouse=getInterface().screenToGui(new Point(Mouse.getX(),Mouse.getY()));
		switch (clickedButton) {
		case IInterface.LBUTTON:
			lButton=true;
			break;
		case IInterface.RBUTTON:
			rButton=true;
			break;
		}
		getGUI().handleButton(clickedButton);
	}

	@Override
	public void mouseReleased (int mouseX, int mouseY, int releaseButton) {
		mouse=getInterface().screenToGui(new Point(Mouse.getX(),Mouse.getY()));
		switch (releaseButton) {
		case IInterface.LBUTTON:
			lButton=false;
			break;
		case IInterface.RBUTTON:
			rButton=false;
			break;
		}
		getGUI().handleButton(releaseButton);
	}
	
	@Override
	protected void keyPressed (char typedChar, int keyCode) {
		if (keyCode == Keyboard.KEY_ESCAPE) exitGUI();
		else {
			getGUI().handleKey(keyCode);
			getGUI().handleChar(typedChar);
		}
	}

	@Override
	public boolean shouldPauseGame() {
		return false;
	}
	
	/**
	 * Get the {@link GUI} to be rendered.
	 * @return current GUI
	 */
	protected abstract GUI getGUI();
	
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
		/**
		 * Constructor.
		 * @param clipX whether to clip in the horizontal direction
		 */
		public GUIInterface (boolean clipX) {
			super(clipX);
		}
		
		@Override
		public long getTime() {
			return lastTime;
		}
		
		@Override
		public boolean getButton (int button) {
			switch (button) {
			case IInterface.LBUTTON:
				return lButton;
			case IInterface.RBUTTON:
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
			return zOffset;
		}
	}
}
