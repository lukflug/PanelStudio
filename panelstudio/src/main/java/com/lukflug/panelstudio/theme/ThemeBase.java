package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.IBoolean;

/**
 * Base class for themes.
 * @author lukflug
 */
public abstract class ThemeBase implements ITheme {
	/**
	 * The description renderer.
	 */
	protected IDescriptionRenderer descriptionRenderer;
	/**
	 * The title renderers.
	 */
	protected IButtonRenderer<Void> titleRenderer,panelTitleRenderer;
	/**
	 * The panel renderers.
	 */
	protected IPanelRenderer panelRenderer,panelPanelRenderer;
	/**
	 * The container renderers.
	 */
	protected IContainerRenderer containerRenderer,panelContainerRenderer;
	/**
	 * The scroll bar renderer.
	 */
	protected IScrollBarRenderer scrollRenderer;
	/**
	 * The scroll corner renderer.
	 */
	protected IEmptySpaceRenderer cornerRenderer;
	/**
	 * The toggle button renderer.
	 */
	protected IButtonRenderer<IBoolean> toggleButtonRenderer;
	/**
	 * The check mark renderer.
	 */
	protected IButtonRenderer<IBoolean> checkMarkRenderer;
	/**
	 * The cycle button renderer.
	 */
	protected IButtonRenderer<String> cycleButtonRenderer;
	/**
	 * The keybind renderer.
	 */
	protected IButtonRenderer<String> keybindRenderer;
	/**
	 * The slider renderer.
	 */
	protected ISliderRenderer sliderRenderer;
	
	@Override
	public IDescriptionRenderer getDescriptionRenderer() {
		return descriptionRenderer;
	}
	
	@Override
	public IButtonRenderer<Void> getTitleRenderer(boolean panel) {
		if (panel) return panelTitleRenderer;
		else return titleRenderer;
	}

	@Override
	public IPanelRenderer getPanelRenderer(boolean panel) {
		if (panel) return panelPanelRenderer;
		else return panelRenderer;
	}

	@Override
	public IContainerRenderer getContainerRednerer(boolean panel) {
		if (panel) return panelContainerRenderer;
		else return containerRenderer;
	}

	@Override
	public IScrollBarRenderer getScrollBarRenderer() {
		return scrollRenderer;
	}

	@Override
	public IEmptySpaceRenderer getEmptySpaceRenderer() {
		return cornerRenderer;
	}

	@Override
	public IButtonRenderer<IBoolean> getToggleButtonRenderer() {
		return toggleButtonRenderer;
	}

	@Override
	public IButtonRenderer<IBoolean> getCheckMarkRenderer() {
		return checkMarkRenderer;
	}

	@Override
	public IButtonRenderer<String> getCycleButtonRenderer() {
		return cycleButtonRenderer;
	}

	@Override
	public IButtonRenderer<String> getKeybindRenderer() {
		return keybindRenderer;
	}

	@Override
	public ISliderRenderer getSliderRenderer() {
		return sliderRenderer;
	}
}
