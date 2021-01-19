package com.lukflug.panelstudio.theme;

/**
 * Base class used to enable switch themes "on-the-fly".
 * It provides {@link RendererProxy} to be used as renderers for the components.
 * In this way, the renderers can effectively be switched, without changing the field in {@link com.lukflug.panelstudio.FocusableComponent#renderer}.
 * @author lukflug
 */
public abstract class ThemeMultiplexer implements Theme {
	/**
	 * {@link RendererProxy} for the components.
	 */
	protected final Renderer panelRenderer,containerRenderer,componentRenderer;
	
	/**
	 * Initializes the renderer fields.
	 */
	public ThemeMultiplexer() {
		panelRenderer=new PanelRenderer();
		containerRenderer=new ContainerRenderer();
		componentRenderer=new ComponentRenderer();
	}
	
	/**
	 * Returns the renderer for panels.
	 */
	@Override
	public Renderer getPanelRenderer() {
		return panelRenderer;
	}

	/**
	 * Returns the renderer for containers.
	 */
	@Override
	public Renderer getContainerRenderer() {
		return containerRenderer;
	}

	/**
	 * Returns the renderer for components.
	 */
	@Override
	public Renderer getComponentRenderer() {
		return componentRenderer;
	}
	
	/**
	 * Abstract method that returns the current theme.
	 * @return the current theme
	 */
	protected abstract Theme getTheme();
	
	
	/**
	 * Proxy for the panel renderer.
	 * @author lukflug
	 */
	protected class PanelRenderer extends RendererProxy {
		/**
		 * Returns the current panel renderer.
		 */
		@Override
		protected Renderer getRenderer() {
			return getTheme().getPanelRenderer();
		}
	}
	
	

	/**
	 * Proxy for the container renderer.
	 * @author lukflug
	 */
	protected class ContainerRenderer extends RendererProxy {
		/**
		 * Returns the current container renderer.
		 */
		@Override
		protected Renderer getRenderer() {
			return getTheme().getContainerRenderer();
		}
	}
	

	/**
	 * Proxy for the component renderer.
	 * @author lukflug
	 */
	protected class ComponentRenderer extends RendererProxy {
		/**
		 * Returns the current component renderer.
		 */
		@Override
		protected Renderer getRenderer() {
			return getTheme().getComponentRenderer();
		}
	}
}
