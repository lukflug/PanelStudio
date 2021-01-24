package com.lukflug.panelstudio.config;

/**
 * Object consisting of the configuration of all panels.
 * @author lukflug
 */
public interface IConfigList {
	/**
	 * Begin loading/storing configuration.
	 * @param loading set if loading
	 */
	public void begin (boolean loading);
	
	/**
	 * End loading/storing configuration.
	 * @param loading set if loading
	 */
	public void end (boolean loading);
	
	/**
	 * Add panel to the configuration.
	 * @param title the title
	 * @return the new panel configuration to be populated by the panel
	 */
	public IPanelConfig addPanel (String title);
	
	/**
	 * Get panel configuration.
	 * @param title the title
	 * @return the panel configuration corresponding to the respective panel
	 */
	public IPanelConfig getPanel (String title);
}
