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
	 * @param components the setting widgets to use
	 * @param client the client defining the module and setting hierarchy
	 * @param theme the theme to be used
	 */
	public void populateGUI (IComponentAdder gui, IComponentGenerator components, IClient client, ITheme theme);
}
