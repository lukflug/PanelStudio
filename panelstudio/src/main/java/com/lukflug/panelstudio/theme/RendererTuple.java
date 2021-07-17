package com.lukflug.panelstudio.theme;

public class RendererTuple<T> {
	public final IPanelRenderer<T> panelRenderer;
	public final IScrollBarRenderer<T> scrollRenderer;
	public final IEmptySpaceRenderer<T> cornerRenderer;
	public final IEmptySpaceRenderer<T> emptyRenderer;
	
	public RendererTuple (Class<T> type, ThemeTuple theme) {
		panelRenderer=theme.getPanelRenderer(type);
		scrollRenderer=theme.getScrollBarRenderer(type);
		cornerRenderer=theme.getEmptySpaceRenderer(type,false);
		emptyRenderer=theme.getEmptySpaceRenderer(type,true);
	}
}
