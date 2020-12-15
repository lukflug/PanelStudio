package com.lukflug.panelstudio;

/**
 * Object consisting of the configuration of all panels.
 * @author lukflug
 */
public interface ConfigList {
	/**
	 * Add panel to the configuration.
	 * @param title the title
	 * @return the new panel configuration to be populated by the panel
	 */
	public PanelConfig addPanel (String title);
	
	/**
	 * Get panel configuration.
	 * @param title the title
	 * @return the panel configuration corresponding to the respective panel
	 */
	public PanelConfig getPanel (String title);
}
