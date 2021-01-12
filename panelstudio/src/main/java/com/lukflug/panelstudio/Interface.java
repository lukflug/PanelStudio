package com.lukflug.panelstudio;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Interface to abstract rendering and input methods.
 * @author lukflug
 */
public interface Interface {
	/**
	 * ID for the left mouse button.
	 * @see #getButton(int)
	 */
	public static final int LBUTTON=0;
	/**
	 * ID for the right mouse button.
	 * @see #getButton(int)
	 */
	public static final int RBUTTON=1;
	
	/**
	 * Method to query the current mouse position.
	 * May be called by any GUI-related method.
	 * @return current mouse position
	 */
	public Point getMouse();
	
	/**
	 * Method to query the current mouse button state.
	 * May be called by any GUI-related method.
	 * @param button the ID of the mouse button 
	 * @return true if the mouse button is being pressed
	 * @see #LBUTTON
	 * @see #RBUTTON
	 */
	public boolean getButton (int button);
	
	/**
	 * Draw a string on the screen.
	 * May only be called in a GUI rendering method.
	 * @param pos the position of the string on the screen
	 * @param s the string to be displayed
	 * @param c the color of the string to be displayed
	 */
	public void drawString (Point pos, String s, Color c);
	
	/**
	 * Get the font width of a string being rendered by {@link #drawString(Point, String, Color)}
	 * @param s the string to be considered
	 * @return the font width
	 */
	public int getFontWidth (String s);
	
	/**
	 * Get height of font rendered by {@link #drawString(Point, String, Color)}
	 * @return the font height
	 */
	public int getFontHeight();
	
	/**
	 * Draw a triangle on the screen.
	 * The color of the triangle should ideally be smoothly interpolated. 
	 * May only be called in a GUI rendering method.
	 * @param pos1 the first corner of the triangle
	 * @param pos2 the second corner of the triangle
	 * @param pos3 the third corner of the triangle
	 * @param c1 the color at the first corner
	 * @param c2 the color at the second corner
	 * @param c3 the color at the third corner
	 */
	public void fillTriangle (Point pos1, Point pos2, Point pos3, Color c1, Color c2, Color c3);
	
	/**
	 * Draw a line on the screen.
	 * The color of the line should ideally be smoothly interpolated. 
	 * May only be called in a GUI rendering method.
	 * @param a the start of the line
	 * @param b the end of the line
	 * @param c1 the color at the start of the line
	 * @param c2 the color at the end of the line
	 */
	public void drawLine (Point a, Point b, Color c1, Color c2);
	
	/**
	 * Draw an axis-aligned rectangle on the screen.
	 * The color of the rectangle should ideally be smoothly interpolated. 
	 * May only be called in a GUI rendering method.
	 * @param r the rectangle to be rendered
	 * @param c1 the color at the top-left corner of the rectangle
	 * @param c2 the color at the top-right corner of the rectangle
	 * @param c3 the color at the bottom-right corner of the rectangle
	 * @param c4 the color at the bottom-left corner of the rectangle
	 */
	public void fillRect (Rectangle r, Color c1, Color c2, Color c3, Color c4);
	
	/**
	 * Draw the outline of an axis-aligned rectangle on the screen.
	 * The color of the rectangle should ideally be smoothly interpolated.
	 * May only be called in a GUI rendering method.
	 * @param r the rectangle to be rendered
	 * @param c1 the color at the top-left corner of the rectangle
	 * @param c2 the color at the top-right corner of the rectangle
	 * @param c3 the color at the bottom-right corner of the rectangle
	 * @param c4 the color at the bottom-left corner of the rectangle
	 */
	public void drawRect (Rectangle r, Color c1, Color c2, Color c3, Color c4);
	
	/**
	 * Load an image into memory and return a number that may be used to draw that image.
	 * Should only be called during initialization.
	 * The name string should ideally be the filename of the image, not including the path,
	 * since the implementation of this interface must ideally provide the directory in which the images are located.
	 * @param name a string indicating the location of the image to be loaded
	 * @return a number identifying the image
	 * @see #drawImage(Rectangle, int, boolean, int)
	 */
	public int loadImage (String name);
	
	/**
	 * Draw an image.
	 * @param r the rectangle specifying the position of the image
	 * @param rotation the rotation of the image in units of 90 degrees counter-clockwise
	 * @param parity if true, will switch the top-left and bottom-right, the bottom-left and top-right corners
	 * @param image the number identifying the image
	 * @see #loadImage(String)
	 */
	public void drawImage (Rectangle r, int rotation, boolean parity, int image);
	
	/**
	 * Clip all rendering on screen outside the intersection of the specified rectangle and the current clipping rectangle.
	 * May only be called in a GUI rendering method.
	 * The calling method should restore clipping by calling {@link #restore()} after rendering.
	 * @param r the clipping rectangle
	 * @see #restore()
	 */
	public void window (Rectangle r);
	
	/**
	 * Restore the clipping to the previous state.
	 * @see #window(Rectangle)
	 */
	public void restore();
}
