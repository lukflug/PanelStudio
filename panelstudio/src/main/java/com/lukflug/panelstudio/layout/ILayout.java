package com.lukflug.panelstudio.layout;

import com.lukflug.panelstudio.setting.IClient;
import com.lukflug.panelstudio.theme.ITheme;

/**
 * Interface abstracting the positioning and structure of a GUI
 * @author lukflug
 */
@FunctionalInterface
public interface ILayout {
	/**
	 * Populates a GUI with modules and settings given.
	 * @param gui the GUI to populate
	 * @param client the client defining the module and setting hierarchy
	 */
	public void populateGUI (IComponentAdder gui, IComponentGenerator components, IClient client, ITheme theme);
}
