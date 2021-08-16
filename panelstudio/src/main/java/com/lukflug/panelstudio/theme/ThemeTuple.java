package com.lukflug.panelstudio.theme;

/**
 * Data structure used to reduce argument count.
 * Describes what theme and graphical and logical level is to be used.
 * @author lukflug
 */
public final class ThemeTuple {
	/**
	 * The theme to be used.
	 */
	public final ITheme theme;
	/**
	 * The logical nesting level.
	 */
	public final int logicalLevel;
	/**
	 * The panel nesting level.
	 */
	public final int graphicalLevel;
	
	/**
	 * Constructor.
	 * @param theme the theme to be used
	 * @param logicalLevel the logical nesting level
	 * @param graphicalLevel the panel nesting level
	 */
	public ThemeTuple (ITheme theme, int logicalLevel, int graphicalLevel) {
		this.theme=theme;
		this.logicalLevel=logicalLevel;
		this.graphicalLevel=graphicalLevel;
	}
	
	/**
	 * Create a new theme tuple from a old one.
	 * @param previous the base tuple
	 * @param logicalDiff the difference in logical level
	 * @param graphicalDiff the difference in panel level
	 */
	public ThemeTuple (ThemeTuple previous, int logicalDiff, int graphicalDiff) {
		this.theme=previous.theme;
		this.logicalLevel=previous.logicalLevel+logicalDiff;
		this.graphicalLevel=previous.graphicalLevel+graphicalDiff;
	}
	
	/**
	 * Returns a container renderer.
	 * @param horizontal whether it is a horizontal container
	 * @return the container renderer
	 */
	public IContainerRenderer getContainerRenderer (boolean horizontal) {
		return theme.getContainerRenderer(logicalLevel,graphicalLevel,horizontal);
	}
	
	/**
	 * Returns the panel renderer.
	 * @param <T> the state type
	 * @param type the corresponding class object
	 * @return the panel renderer
	 */
	public <T> IPanelRenderer<T> getPanelRenderer (Class<T> type) {
		return theme.getPanelRenderer(type,logicalLevel,graphicalLevel);
	}
	
	/**
	 * Returns the scroll bar renderer.
	 * @param <T> the state type
	 * @param type the corresponding class object
	 * @return the scroll bar renderer
	 */
	public <T> IScrollBarRenderer<T> getScrollBarRenderer (Class<T> type) {
		return theme.getScrollBarRenderer(type,logicalLevel,graphicalLevel);
	}
	
	/**
	 * Returns the empty space renderer.
	 * @param <T> the state type
	 * @param type the corresponding class object
	 * @param container true, if this is to fill a container, false, if this is to fill a scroll corner
	 * @return the empty space renderer
	 */
	public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer (Class<T> type, boolean container) {
		return theme.getEmptySpaceRenderer(type,logicalLevel,graphicalLevel,container);
	}
	
	/**
	 * Returns the button renderer.
	 * @param <T> the state type
	 * @param type the corresponding class object
	 * @param container whether the component is the title of a panel
	 * @return the button renderer
	 */
	public <T> IButtonRenderer<T> getButtonRenderer (Class<T> type, boolean container) {
		return theme.getButtonRenderer(type,logicalLevel,graphicalLevel,container);
	}
	
	/**
	 * Returns the small button renderer.
	 * @param symbol the symbol ID to be used
	 * @param container whether the component is the title of a panel
	 * @return the button renderer
	 */
	public IButtonRenderer<Void> getSmallButtonRenderer (int symbol, boolean container) {
		return theme.getSmallButtonRenderer(symbol,logicalLevel,graphicalLevel,container);
	}
	
	/**
	 * Returns the keybind renderer.
	 * @param container whether the component is the title of a panel
	 * @return the keybind renderer
	 */
	public IButtonRenderer<String> getKeybindRenderer (boolean container) {
		return theme.getKeybindRenderer(logicalLevel,graphicalLevel,container);
	}
	
	/**
	 * Returns the slider renderer.
	 * @param container whether the component is the title of a panel
	 * @return the slider renderer
	 */
	public ISliderRenderer getSliderRenderer (boolean container) {
		return theme.getSliderRenderer(logicalLevel,graphicalLevel,container);
	}

	/**
	 * Returns the radio renderer.
	 * @param container whether the component is the title of a panel
	 * @return the radio renderer
	 */
	public IRadioRenderer getRadioRenderer (boolean container) {
		return theme.getRadioRenderer(logicalLevel,graphicalLevel,container);
	}

	/**
	 * Returns the text renderer.
	 * @param embed whether the text renderer is embedded in another component
	 * @param container whether the component is the title of a panel
	 * @return the text renderer
	 */
	public ITextFieldRenderer getTextRenderer (boolean embed, boolean container) {
		return theme.getTextRenderer(embed,logicalLevel,graphicalLevel,container);
	}

	/**
	 * Returns the toggle switch renderer.
	 * @param container whether the component is the title of a panel
	 * @return the toggle switch renderer
	 */
	public ISwitchRenderer<Boolean> getToggleSwitchRenderer (boolean container) {
		return theme.getToggleSwitchRenderer(logicalLevel,graphicalLevel,container);
	}

	/**
	 * Returns the cycle switch renderer.
	 * @param container whether the component is the title of a panel
	 * @return the cycle switch renderer
	 */
	public ISwitchRenderer<String> getCycleSwitchRenderer (boolean container) {
		return theme.getCycleSwitchRenderer(logicalLevel,graphicalLevel,container);
	}
}
