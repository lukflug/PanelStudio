package com.lukflug.panelstudio.theme;

/**
 * Data structure used to reduce argument count.
 * Contains combination of renderers commonly used together in a panel.
 * @author lukflug
 */
public class RendererTuple<T> {
	/**
	 * The panel renderer.
	 */
	public final IPanelRenderer<T> panelRenderer;
	/**
	 * The scroll bar renderer.
	 */
	public final IScrollBarRenderer<T> scrollRenderer;
	/**
	 * The scroll corner renderer.
	 */
	public final IEmptySpaceRenderer<T> cornerRenderer;
	/**
	 * The empty container space renderer.
	 */
	public final IEmptySpaceRenderer<T> emptyRenderer;
	
	/**
	 * Constructor.
	 * @param type the panel state type
	 * @param theme the theme tuple to be used
	 */
	public RendererTuple (Class<T> type, ThemeTuple theme) {
		panelRenderer=theme.getPanelRenderer(type);
		scrollRenderer=theme.getScrollBarRenderer(type);
		cornerRenderer=theme.getEmptySpaceRenderer(type,false);
		emptyRenderer=theme.getEmptySpaceRenderer(type,true);
	}
}
