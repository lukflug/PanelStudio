package com.lukflug.panelstudio.widget;

/**
 * Class containing predicates for dealing with function keys for text fields.
 * @author lukflug
 */
public interface ITextFieldKeys {
	/**
	 * Scancode predicate for backspace.
	 * @param scancode the scancode
	 * @return true, if key matches
	 */
	public boolean isBackspaceKey (int scancode);
	
	/**
	 * Scancode predicate for delete.
	 * @param scancode the scancode
	 * @return true, if key matches
	 */
	public boolean isDeleteKey (int scancode);
	
	/**
	 * Scancode predicate for insert.
	 * @param scancode the scancode
	 * @return true, if key matches
	 */
	public boolean isInsertKey (int scancode);
	
	/**
	 * Scancode predicate for left.
	 * @param scancode the scancode
	 * @return true, if key matches
	 */
	public boolean isLeftKey (int scancode);
	
	/**
	 * Scancode predicate for right.
	 * @param scancode the scancode
	 * @return true, if key matches
	 */
	public boolean isRightKey (int scancode);
	
	/**
	 * Scancode predicate for home.
	 * @param scancode the scancode
	 * @return true, if key matches
	 */
	public boolean isHomeKey (int scancode);
	
	/**
	 * Scancode predicate for end.
	 * @param scancode the scancode
	 * @return true, if key matches
	 */
	public boolean isEndKey (int scancode);
	
	/**
	 * Scancode predicate for the C-key.
	 * @param scancode the scancode
	 * @return true, if key matches
	 */
	public boolean isCopyKey (int scancode);
	
	/**
	 * Scancode predicate for the V-key.
	 * @param scancode the scancode
	 * @return true, if key matches
	 */
	public boolean isPasteKey (int scancode);
	
	/**
	 * Scancode predicate for the X-key.
	 * @param scancode the scancode
	 * @return true, if key matches
	 */
	public boolean isCutKey (int scancode);
	
	/**
	 * Scancode predicate for the A-key.
	 * @param scancode the scancode
	 * @return true, if key matches
	 */
	public boolean isAllKey (int scancode);
}
