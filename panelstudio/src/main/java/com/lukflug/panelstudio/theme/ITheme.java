package com.lukflug.panelstudio.theme;

import java.awt.Color;

import com.lukflug.panelstudio.base.Context;
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
	 * Returns the renderer for the panel background.
	 * @param level the panel nesting level
	 * @return the container renderer
	 */
	public IContainerRenderer getContainerRenderer (int level);
	
	/**
	 * Returns the renderer for the panel outline.
	 * @param <T> the state type
	 * @param level the panel nesting level
	 * @return the panel renderer
	 */
	public default <T> IPanelRenderer<T> getPanelRenderer (int level) {
		return new IPanelRenderer<T>() {
			IPanelRenderer<Void> renderer=getPanelRenderer(level);
			
			@Override
			public void renderPanelOverlay(Context context, boolean focus, T state) {
				renderer.renderPanelOverlay(context,focus,null);
			}
		};
	}
	
	/**
	 * Returns the renderer for scroll bars.
	 * @param <T> the state type
	 * @param level the panel nesting level
	 * @return the scroll bar renderer
	 */
	public default <T> IScrollBarRenderer<T> getScrollBarRenderer (int level) {
		return new IScrollBarRenderer<T>() {
			IScrollBarRenderer<Void> renderer=getScrollBarRenderer(level);

			@Override
			public int renderScrollBar(Context context, boolean focus, T state, boolean horizontal, int height, int position) {
				return renderer.renderScrollBar(context,focus,null,horizontal,height,position);
			}

			@Override
			public int getThickness() {
				return renderer.getThickness();
			}
		};
	}
	
	/**
	 * Returns the renderer for the scroll corner.
	 * @param <T> the state type
	 * @param level the panel nesting level
	 * @return the empty space renderer
	 */
	public default <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer (int level) {
		return new IEmptySpaceRenderer<T>() {
			IEmptySpaceRenderer<Void> renderer=getEmptySpaceRenderer(level);

			@Override
			public void renderSpace(Context context, boolean focus, T state) {
				renderer.renderSpace(context,focus,null);
			}
		};
	}
	
	/**
	 * Returns the renderer for buttons.
	 * @param <T> the state type
	 * @param level the panel nesting level
	 * @param container whether this is the title of a panel
	 * @return the title renderer
	 */
	public default <T> IButtonRenderer<T> getButtonRenderer (int level, boolean container) {
		return new IButtonRenderer<T>() {
			IButtonRenderer<Void> renderer=getButtonRenderer(level,container);
			
			@Override
			public void renderButton(Context context, String title, boolean focus, T state) {
				renderer.renderButton(context,title,focus,null);
			}

			@Override
			public int getDefaultHeight() {
				return renderer.getDefaultHeight();
			}
		};
	}
	
	/**
	 * Returns the renderer for check marks.
	 * @param level the panel nesting level
	 * @param container whether this is the title of a panel
	 * @return the check mark renderer
	 */
	public IButtonRenderer<IBoolean> getCheckMarkRenderer (int level, boolean container);
	
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
	 * Get the common height of a component.
	 * @return the base height
	 */
	public int getBaseHeight();
	
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
