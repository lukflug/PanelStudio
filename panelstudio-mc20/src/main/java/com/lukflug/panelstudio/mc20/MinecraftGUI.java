package com.lukflug.panelstudio.mc20;

import java.awt.Point;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.container.GUI;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

/**
 * Implementation of Minecraft's {@link Screen} that renders a PanelStudio GUI.
 * @author lukflug, Diliard
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
	 * Current GLFW modifier bits.
	 */
	private int modifiers=0;
	/**
	 * Last rendering time.
	 */
	private long lastTime;
	/**
	 * Drawing context
	 */
	protected DrawContext context=null;
	
	/**
	 * Constructor.
	 */
	public MinecraftGUI() {
		super(Text.literal("PanelStudio GUI"));
	}
	
	/**
	 * Displays the GUI.
	 */
	public void enterGUI() {
		MinecraftClient.getInstance().setScreen(this);
	}
	
	/**
	 * Closes the GUI.
	 */
	public void exitGUI() {
		MinecraftClient.getInstance().setScreen(null);
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
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);
		this.context = context;
		mouse=new Point(mouseX,mouseY);
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

	@Override
	public boolean mouseClicked (double mouseX, double mouseY, int clickedButton) {
		if (!super.mouseReleased(mouseX,mouseY,clickedButton)) {
			mouse=new Point((int)Math.round(mouseX),(int)Math.round(mouseY));
			switch (clickedButton) {
				case IInterface.LBUTTON -> lButton = true;
				case IInterface.RBUTTON -> rButton = true;
			}
			getGUI().handleButton(clickedButton);
		}
		return true;
	}

	@Override
	public boolean mouseReleased (double mouseX, double mouseY, int releaseButton) {
		if (!super.mouseReleased(mouseX,mouseY,releaseButton)) {
			mouse=new Point((int)Math.round(mouseX),(int)Math.round(mouseY));
			switch (releaseButton) {
				case IInterface.LBUTTON -> {
					lButton = false;
				}
				case IInterface.RBUTTON -> {
					rButton = false;
				}
			}
			getGUI().handleButton(releaseButton);
		}
		return true;
	}
	
	@Override
	public boolean keyPressed (int keyCode, int scanCode, int modifiers) {
		this.modifiers=modifiers;
		if (keyCode==GLFW.GLFW_KEY_ESCAPE) exitGUI();
		else getGUI().handleKey(keyCode);
		return true;
	}
	
	@Override
	public boolean charTyped (char chr, int modifiers) {
		this.modifiers=modifiers;
		getGUI().handleChar(chr);
		return true;
	}
	
	@Override
	public boolean shouldPause() {
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
		public boolean getModifier (int modifier) {
			return switch (modifier) {
				case SHIFT -> (modifiers & GLFW.GLFW_MOD_SHIFT) != 0;
				case CTRL -> (modifiers & GLFW.GLFW_MOD_CONTROL) != 0;
				case ALT -> (modifiers & GLFW.GLFW_MOD_ALT) != 0;
				case SUPER -> (modifiers & GLFW.GLFW_MOD_SUPER) != 0;
				default -> false;
			};
		}
		
		@Override
		public long getTime() {
			return lastTime;
		}
		
		@Override
		public boolean getButton (int button) {
			return switch (button) {
				case IInterface.LBUTTON -> lButton;
				case IInterface.RBUTTON -> rButton;
				default -> false;
			};
		}

		@Override
		public Point getMouse() {
			return new Point(mouse);
		}

		@Override
		protected float getZLevel() {
			return 1;
		}

		@Override
		protected DrawContext getContext() {
			return context;
		}
	}
}
