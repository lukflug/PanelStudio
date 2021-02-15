package com.lukflug.panelstudio.theme;

import java.awt.Color;

import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IInterface;

/**
 * Base class used to enable switch themes "on-the-fly".
 * It provides the renderers for the components.
 * In this way, the renderers can effectively be switched, without changing the field in the components itself.
 * @author lukflug
 */
public abstract class ThemeMultiplexer implements ITheme {
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

	@Override
	public void loadAssets(IInterface inter) {
		getTheme().loadAssets(inter);
	}

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
