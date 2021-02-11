package com.lukflug.panelstudio.theme;

import java.awt.Color;

import com.lukflug.panelstudio.base.IBoolean;

/**
 * Base class used to enable switch themes "on-the-fly".
 * It provides the renderers for the components.
 * In this way, the renderers can effectively be switched, without changing the field in the components itself.
 * @author lukflug
 */
public abstract class ThemeMultiplexer extends ThemeBase {
	/**
	 * Initializes the renderer fields.
	 */
	public ThemeMultiplexer (IColorScheme scheme) {
		super(scheme);
		IDescriptionRendererProxy descriptionRenderer=()->getTheme().getDescriptionRenderer();
		IButtonRendererProxy<Void> titleRenderer=()->getTheme().getTitleRenderer(false);
		IButtonRendererProxy<Void> panelTitleRenderer=()->getTheme().getTitleRenderer(true);
		IPanelRendererProxy panelRenderer=()->getTheme().getPanelRenderer(false);
		IPanelRendererProxy panelPanelRenderer=()->getTheme().getPanelRenderer(true);
		IContainerRendererProxy containerRenderer=()->getTheme().getContainerRednerer(false);
		IContainerRendererProxy panelContainerRenderer=()->getTheme().getContainerRednerer(true);
		IScrollBarRendererProxy scrollRenderer=()->getTheme().getScrollBarRenderer();
		IEmptySpaceRendererProxy cornerRenderer=()->getTheme().getEmptySpaceRenderer();
		IButtonRendererProxy<IBoolean> toggleButtonRenderer=()->getTheme().getToggleButtonRenderer();
		IButtonRendererProxy<IBoolean> checkMarkRenderer=()->getTheme().getCheckMarkRenderer();
		IButtonRendererProxy<String> cycleButtonRenderer=()->getTheme().getCycleButtonRenderer();
		IButtonRendererProxy<String> keybindRenderer=()->getTheme().getKeybindRenderer();
		ISliderRendererProxy sliderRenderer=()->getTheme().getSliderRenderer();
		this.descriptionRenderer=descriptionRenderer;
		this.titleRenderer=titleRenderer;
		this.panelTitleRenderer=panelTitleRenderer;
		this.panelRenderer=panelRenderer;
		this.panelPanelRenderer=panelPanelRenderer;
		this.containerRenderer=containerRenderer;
		this.panelContainerRenderer=panelContainerRenderer;
		this.scrollRenderer=scrollRenderer;
		this.cornerRenderer=cornerRenderer;
		this.toggleButtonRenderer=toggleButtonRenderer;
		this.checkMarkRenderer=checkMarkRenderer;
		this.cycleButtonRenderer=cycleButtonRenderer;
		this.keybindRenderer=keybindRenderer;
		this.sliderRenderer=sliderRenderer;
	}

	@Override
	public Color getMainColor(boolean focus, boolean active) {
		return getTheme().getMainColor(focus,active);
	}

	@Override
	public Color getBackgroundColor(boolean focus) {
		return getTheme().getBackgroundColor(focus);
	}

	@Override
	public Color getFontColor(boolean focus) {
		return getTheme().getFontColor(focus);
	}

	@Override
	public void overrideMainColor(Color color) {
		getTheme().overrideMainColor(color);
	}

	@Override
	public void restoreMainColor() {
		getTheme().restoreMainColor();
	}
	
	/**
	 * Abstract method that returns the current theme.
	 * @return the current theme
	 */
	protected abstract ITheme getTheme();
}
