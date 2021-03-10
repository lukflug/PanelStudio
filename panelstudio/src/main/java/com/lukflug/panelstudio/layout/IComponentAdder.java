package com.lukflug.panelstudio.layout;

import java.awt.Point;

import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.theme.ITheme;

/**
 * Interface representing thing that can have panels added to it 
 * @author lukflug
 */
@FunctionalInterface
public interface IComponentAdder {
	public <S extends IComponent,T extends IComponent> void addComponent (S title, T content, ITheme theme, int level, Point position, int width);
}
