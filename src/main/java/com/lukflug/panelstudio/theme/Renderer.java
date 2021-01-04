package com.lukflug.panelstudio.theme;

import java.awt.Color;
import java.awt.Rectangle;

import com.lukflug.panelstudio.Context;

/**
 * An Interface to standardize the appearance of different components.
 * @author lukflug
 */
public interface Renderer {
	/**
	 * Returns the default height for components.
	 * @param open true if the component in question the the title bar for an open container
	 * @return the component height
	 */
	public int getHeight (boolean open);
	
	/**
	 * Returns the vertical space between two components in a container.
	 * @return the vertical offset
	 */
	public int getOffset();
	
	/**
	 * Returns the horizontal border around a component in a container.
	 * @return the horizontal border
	 */
	public int getBorder();
	
	/**
	 * Returns the border below a container.
	 * @return the bottom border
	 */
	public int getBottomBorder();
	
	/**
	 * Returns the left horizontal border around an entire container.
	 * @param scroll whether the container was restricted due to scrolling
	 * @return the horizontal border
	 */
	public int getLeftBorder (boolean scroll);
	
	/**
	 * Returns the right horizontal border around an entire container.
	 * @param scroll whether the container was restricted due to scrolling
	 * @return the horizontal border
	 */
	public int getRightBorder (boolean scroll);
	
	/**
	 * Render the title bar for a component, while deciding whether to render an active or inactive title bar.
	 * @param context the {@link Context} for the component
	 * @param text the caption of the component
	 * @param focus the focus state for the component
	 */
	public void renderTitle (Context context, String text, boolean focus);
	
	/**
	 * Render the title bar for a component.
	 * @param context the {@link Context} for the component
	 * @param text the caption of the component
	 * @param focus the focus state for the component
	 * @param active whether the component is active or inactive
	 */
	public void renderTitle (Context context, String text, boolean focus, boolean active);
	
	/**
	 * Render the title bar for a container.
	 * @param context the {@link Context} for the component
	 * @param text the caption of the component
	 * @param focus the focus state for the component
	 * @param active whether the component is active or inactive
	 * @param open whether the container is open or closed
	 */
	public void renderTitle (Context context, String text, boolean focus, boolean active, boolean open);
	
	/**
	 * Render partial title bar (e.g. for a slider).
	 * @param context the {@link Context} for the component
	 * @param text the caption of the component
	 * @param focus the focus state for the component
	 * @param active whether the component is active or inactive
	 * @param rectangle the rectangle defining the title bar
	 * @param overlay whether to render mouse hover overlay
	 */
	public void renderRect (Context context, String text, boolean focus, boolean active, Rectangle rectangle, boolean overlay);
	
	/**
	 * Render the background for a container.
	 * @param context the {@link Context} for the component
	 * @param focus the focus state for the component
	 */
	public void renderBackground (Context context, boolean focus);
	
	/**
	 * Render the border for a container.
	 * @param context the {@link Context} for the component
	 * @param focus the focus state for the component
	 * @param active whether the component is active or inactive
	 * @param open whether the container is open or closed
	 */
	public void renderBorder (Context context, boolean focus, boolean active, boolean open);
	
	/**
	 * Renders the scroll bar for a container
	 * @param context context the {@link Context} for the component
	 * @param focus the focus state for the component
	 * @param active whether the component is active or inactive
	 * @param scroll whether the container has scrolling active
	 * @param childHeight the total height of the children
	 * @param scrollPosition the current scroll position
	 * @return the new scroll position
	 */
	public int renderScrollBar (Context context, boolean focus, boolean active, boolean scroll, int childHeight, int scrollPosition);
	
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
	 * Returns the default color scheme.
	 * @return the color scheme
	 */
	public ColorScheme getDefaultColorScheme();
	
	/**
	 * Overrides the default color scheme.
	 * {@link #restoreColorScheme()} should always be called after rendering using a custom color scheme is finished.
	 * @param scheme the custom color scheme
	 */
	public void overrideColorScheme (ColorScheme scheme);
	
	/**
	 * Restores the default color scheme.
	 */
	public void restoreColorScheme();
	
	/**
	 * Utility function to make a color brighter.
	 * @param color a color
	 * @return a brighter version of that color
	 */
	public static Color brighter (Color color) {
		int r=color.getRed(),g=color.getGreen(),b=color.getBlue();
		r+=64;
		g+=64;
		b+=64;
		if (r>255) r=255;
		if (g>255) g=255;
		if (b>255) b=255;
		return new Color(r,g,b,color.getAlpha());
	}
	
	/**
	 * Utility function to make a color darker.
	 * @param color a color
	 * @return a darker version of that color
	 */
	public static Color darker (Color color) {
		int r=color.getRed(),g=color.getGreen(),b=color.getBlue();
		r-=64;
		g-=64;
		b-=64;
		if (r<0) r=0;
		if (g<0) g=0;
		if (b<0) b=0;
		return new Color(r,g,b,color.getAlpha());
	}
}
