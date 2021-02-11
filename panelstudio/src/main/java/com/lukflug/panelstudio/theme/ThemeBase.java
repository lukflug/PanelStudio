package com.lukflug.panelstudio.theme;

import java.awt.Color;

import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IInterface;

/**
 * Base class for themes.
 * @author lukflug
 */
public abstract class ThemeBase implements ITheme {
	/**
	 * The color scheme.
	 */
	protected final IColorScheme scheme;
	/**
	 * The overridden main color.
	 */
	private Color overrideColor=null;
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
	
	public ThemeBase (IColorScheme scheme) {
		this.scheme=scheme;
	}
	
	@Override
	public void loadAssets (IInterface inter) {
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
	public void overrideMainColor (Color color) {
		overrideColor=color;
	}
	
	@Override
	public void restoreMainColor() {
		overrideColor=null;
	}
	
	/**
	 * Get overridden main color.
	 * @return the main color
	 */
	protected Color getMainColor() {
		if (overrideColor==null) return getDefaultColor();
		else return overrideColor;
	}
	
	/**
	 * Get default main color.
	 * @return the default color
	 */
	protected abstract Color getDefaultColor();
}
