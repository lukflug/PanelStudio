package com.lukflug.panelstudio.layout;

import java.awt.Point;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.theme.ThemeTuple;

/**
 * Interface representing thing that can have panels added to it 
 * @author lukflug
 */
public interface IComponentAdder {
	public <S extends IComponent,T extends IComponent> void addComponent (S title, T content, ThemeTuple theme, Point position, int width, Supplier<Animation> animation);
	
	public void addPopup (IFixedComponent popup);
}
