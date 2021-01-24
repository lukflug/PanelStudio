package com.lukflug.panelstudio.theme;

/**
 * Interface representing a GUI theme (i.e. skin).
 * Consists of three {@link IRenderer}, that can be the same or different.
 * @author lukflug
 */
public interface ITheme {
	/**
	 * Returns the renderer that should be used by the root components (i.e. {@link com.lukflug.panelstudio.IFixedComponent}).
	 * @return the panel renderer
	 */
	public IRenderer getPanelRenderer();
	
	/**
	 * Returns the renderer that should be used by containers.
	 * @return the container renderer
	 */
	public IRenderer getContainerRenderer();
	
	/**
	 * Returns the renderer that should be used by components.
	 * @return the component renderer
	 */
	public IRenderer getComponentRenderer();
}
