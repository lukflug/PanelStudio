package com.lukflug.clickgui.theme;

/**
 * Interface representing a GUI theme (i.e. skin).
 * Consists of three {@link Renderer}, that can be the same or different.
 * @author lukflug
 */
public interface Theme {
	/**
	 * Returns the renderer that should be used by the root components (i.e. {@link com.lukflug.clickgui.FixedComponent})
	 * @return the panel renderer
	 */
	public Renderer getPanelRender();
	
	/**
	 * Returns the renderer that should be used by containers.
	 * @return the container renderer
	 */
	public Renderer getContainerRender();
	
	/**
	 * Returns the renderer that should be used by components.
	 * @return the component renderer
	 */
	public Renderer getComponentRender();
}
