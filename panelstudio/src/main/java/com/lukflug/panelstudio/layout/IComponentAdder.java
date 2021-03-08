package com.lukflug.panelstudio.layout;

import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.theme.ITheme;

/**
 * Interface representing thing that can have panels added to it 
 * @author lukflug
 */
@FunctionalInterface
public interface IComponentAdder {
	public void addComponent (IComponent title, IComponent content, ITheme theme);
}
