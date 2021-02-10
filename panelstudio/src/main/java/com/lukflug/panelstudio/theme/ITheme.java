package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.IBoolean;

/**
 * Interface representing a GUI theme (i.e. skin).
 * @author lukflug
 */
public interface ITheme {
	/**
	 * Returns the renderer for the panel title.
	 * @param panel whether this is the root panel or not
	 * @return the title renderer
	 */
	public IButtonRenderer<Void> getTitleRenderer (boolean panel);
	
	/**
	 * Returns the renderer for the panel outline.
	 * @param panel whether this is the root panel or not
	 * @return the panel renderer
	 */
	public IPanelRenderer getPanelRenderer (boolean panel);
	
	/**
	 * Returns the renderer for the panel background.
	 * @param panel whether this is the root panel or not
	 * @return the container renderer
	 */
	public IContainerRenderer getContainerRednerer (boolean panel);
	
	/**
	 * Returns the renderer for scroll bars.
	 * @return the scroll bar renderer
	 */
	public IScrollBarRenderer getScrollBarRenderer();
	
	/**
	 * Returns the renderer for the scroll corner.
	 * @return the empty space renderer
	 */
	public IEmptySpaceRenderer getEmptySpaceRenderer();
	
	/**
	 * Returns the renderer for toggle buttons.
	 * @return the button renderer
	 */
	public IButtonRenderer<IBoolean> getToggleButtonRenderer();
	
	/**
	 * Returns the renderer for check marks.
	 * @return the check mark renderer
	 */
	public IButtonRenderer<IBoolean> getCheckMarkRenderer();
	
	/**
	 * Returns the renderer for cycle buttons.
	 * @return the button renderer
	 */
	public IButtonRenderer<String> getCycleButtonRenderer();
	
	/**
	 * Returns the renderer for keybinds.
	 * @return the keybind renderer
	 */
	public IButtonRenderer<String> getKeybindRenderer();
	
	/**
	 * Returns the renderer for sliders.
	 * @return the slider renderer
	 */
	public ISliderRenderer getSliderRenderer();
}
