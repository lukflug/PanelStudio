package com.lukflug.panelstudio.mc;

import java.awt.Point;

import org.lwjgl.input.Mouse;

import com.lukflug.panelstudio.ClickGUI;
import com.lukflug.panelstudio.Interface;

import net.minecraft.client.gui.GuiScreen;

public abstract class MinecraftGUI extends GuiScreen {
	private Point mouse=new Point();
	private boolean lButton=false,rButton=false;
	
	protected void renderGUI() {
		getInterface().getMatrices();
    	GLInterface.begin();
        getGUI().render();
        GLInterface.end();
	}
	
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		renderGUI();
    	mouse=new Point(mouseX,mouseY);
        int scroll=Mouse.getDWheel();
        if (scroll!=0) {
        	if (scroll>0) getGUI().handleScroll(-getScrollSpeed());
        	else getGUI().handleScroll(getScrollSpeed());
        }
    }

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
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) {
    	if (keyCode == 1) {
    		getGUI().exit();
    		this.mc.displayGuiScreen(null);
    	} else getGUI().handleKey(keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    protected abstract ClickGUI getGUI();
    protected abstract GUIInterface getInterface();
    protected abstract int getScrollSpeed();
	
	public abstract class GUIInterface extends GLInterface {
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
