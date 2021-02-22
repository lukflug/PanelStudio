package com.lukflug.panelstudio.theme;

import java.awt.Color;

import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IInterface;

/**
 * Interface representing a GUI theme (i.e. skin).
 * @author lukflug
 */
public interface ITheme {
	/**
	 * Function to be called in order to load images.
	 * @param inter the interface to use
	 */
	public void loadAssets (IInterface inter);
	
	/**
	 * Returns the renderer for tooltip descriptions.
	 * @return the description renderer
	 */
	public IDescriptionRenderer getDescriptionRenderer();
	
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
	public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer();
	
	/**
	 * Returns the renderer for stateless buttons.
	 * @param level the panel nesting level
	 * @param container whether this is the title of a panel
	 * @return the title renderer
	 */
	public IButtonRenderer<Void> getButtonRenderer (int level, boolean container);
	
	/**
	 * Returns the renderer for toggle buttons.
	 * @param level the panel nesting level
	 * @param container whether this is the title of a panel
	 * @return the button renderer
	 */
	public IButtonRenderer<IBoolean> getToggleButtonRenderer (int level, boolean container);
	
	/**
	 * Returns the renderer for check marks.
	 * @param level the panel nesting level
	 * @param container whether this is the title of a panel
	 * @return the check mark renderer
	 */
	public IButtonRenderer<IBoolean> getCheckMarkRenderer (int level, boolean container);
	
	/**
	 * Returns the renderer for cycle buttons.
	 * @param level the panel nesting level
	 * @param container whether this is the title of a panel
	 * @return the button renderer
	 */
	public IButtonRenderer<String> getCycleButtonRenderer (int level, boolean container);
	
	/**
	 * Returns the renderer for keybinds.
	 * @param level the panel nesting level
	 * @param container whether this is the title of a panel
	 * @return the keybind renderer
	 */
	public IButtonRenderer<String> getKeybindRenderer (int level, boolean container);
	
	/**
	 * Returns the renderer for sliders.
	 * @param level the panel nesting level
	 * @param container whether this is the title of a panel
	 * @return the slider renderer
	 */
	public ISliderRenderer getSliderRenderer (int level, boolean container);
	
	/**
	 * Returns the main color of a title bar.
	 * @param focus the focus state for the component
	 * @param active whether the component is active or inactive
	 * @return the main color
	 */
	public Color getMainColor (boolean focus, boolean active);
	
	/**
	 * Returns the standard background color.
	 * @param focus the focus state for the component
	 * @return the background color
	 */
	public Color getBackgroundColor (boolean focus);
	
	/**
	 * Returns the font color.
	 * @param focus the focus state for the component
	 * @return the font color
	 */
	public Color getFontColor (boolean focus);
	
	/**
	 * Override the main color.
	 * @param color the color to override
	 */
	public void overrideMainColor (Color color);
	
	/**
	 * Restore the main color.
	 */
	public void restoreMainColor();
	
	/**
	 * Override the alpha of one color with the alpha of another
	 * @param main the main color
	 * @param opacity the color determining the alpha value
	 * @return the main color with the alpha from the other color
	 */
	public static Color combineColors (Color main, Color opacity) {
		return new Color(main.getRed(),main.getGreen(),main.getBlue(),opacity.getAlpha());
	}
}
