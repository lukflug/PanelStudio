package com.lukflug.panelstudio.theme;

import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;

/**
 * Interface handling the rendering of text fields.
 * @author lukflug
 */
public interface ITextFieldRenderer {
	/**
	 * Renders the text field.
	 * @param context the context to be used
	 * @param title the title of the text field
	 * @param focus the focus state of the text field
	 * @param content the text field content
	 * @param position the cursor position
	 * @param select the boundary of the selection
	 * @param boxPosition the text render offset
	 * @param insertMode true, if in override, rather than insert mode
	 * @return the new cursor position
	 */
	public int renderTextField (Context context, String title, boolean focus, String content, int position, int select, int boxPosition, boolean insertMode);
	
	/**
	 * Returns the default height.
	 * @return the default height
	 */
	public int getDefaultHeight();
	
	/**
	 * Returns the location of the text box.
	 * @param context the context to be used
	 * @param title the title of the text field
	 * @return the location of the text box
	 */
	public Rectangle getTextArea (Context context, String title);
	
	/**
	 * Maps the current mouse cursor position to a character index.
	 * @param context the context to be used
	 * @param title the title of the text field
	 * @param content the text field content
	 * @param boxPosition the text render offset
	 * @return the character position
	 */
	public int transformToCharPos (Context context, String title, String content, int boxPosition);
}
