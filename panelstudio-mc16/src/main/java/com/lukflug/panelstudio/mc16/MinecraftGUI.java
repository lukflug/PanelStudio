package com.lukflug.panelstudio.mc16;

import java.awt.Point;

import com.lukflug.panelstudio.ClickGUI;
import com.lukflug.panelstudio.Interface;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

/**
 * Implementation of Minecraft's GuiScreen that renders a PanelStudio GUI.
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
	 * Saved matrix stack;
	 */
	protected MatrixStack matrixStack=null;
	
	/**
	 * Constructor.
	 */
	public MinecraftGUI() {
		super(new LiteralText("PanelStudio GUI"));
	}
	
	/**
	 * Displays the GUI.
	 */
	public void enterGUI() {
		MinecraftClient.getInstance().openScreen(this);
		getGUI().enter();
	}
	
	/**
	 * Closes the GUI.
	 */
	@Override
	public void onClose() {
		getGUI().exit();
		super.onClose();
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
	public void render (MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
		matrixStack=matrices;
		mouse=new Point((int)Math.round(mouseX),(int)Math.round(mouseY));
		renderGUI();
	}
	
	@Override
	public boolean mouseScrolled (double mouseX, double mouseY, double scroll) {
		if (!super.mouseScrolled(mouseX,mouseY,scroll)) {
			mouse=new Point((int)Math.round(mouseX),(int)Math.round(mouseY));
			if (scroll!=0) {
				if (scroll>0) getGUI().handleScroll(-getScrollSpeed());
				else getGUI().handleScroll(getScrollSpeed());
			}
		}
		return true;
	}

	/**
	 * Updates {@link #lButton} and {@link #rButton}.
	 * @param mouseX current mouse x position
	 * @param mouseY current mouse y position
	 * @param clickedButton number of button being clicked
	 */
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int clickedButton) {
		if (!super.mouseReleased(mouseX,mouseY,clickedButton)) {
			mouse=new Point((int)Math.round(mouseX),(int)Math.round(mouseY));
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
		return true;
	}

	/**
	 * Updates {@link #lButton} and {@link #rButton}.
	 * @param mouseX current mouse x position
	 * @param mouseY current mouse y position
	 * @param releaseButton number of button being released
	 */
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int releaseButton) {
		if (!super.mouseReleased(mouseX,mouseY,releaseButton)) {
			mouse=new Point((int)Math.round(mouseX),(int)Math.round(mouseY));
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
		return true;
	}
	
	/**
	 * Handles the current keys being typed.
	 * @param keyCode key code of key being typed
	 * @param scanCode scancode of the key being typed
	 * @param modifiers modifiers of the keys
	 */
	@Override
	public boolean keyPressed (int keyCode, int scanCode, int modifiers) {
		if (!super.keyPressed(keyCode,scanCode,modifiers)) getGUI().handleKey(keyCode);
		return true;
	}
	
	/**
	 * Returns false.
	 * @return returns false
	 */
	@Override
	public boolean isPauseScreen() {
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
			return getZOffset();
		}
	}
}
