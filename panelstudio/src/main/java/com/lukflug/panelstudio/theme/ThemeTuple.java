package com.lukflug.panelstudio.theme;

public final class ThemeTuple {
	public final ITheme theme;
	public final int logicalLevel;
	public final int graphicalLevel;
	
	public ThemeTuple (ITheme theme, int logicalLevel, int graphicalLevel) {
		this.theme=theme;
		this.logicalLevel=logicalLevel;
		this.graphicalLevel=graphicalLevel;
	}
	
	public ThemeTuple (ThemeTuple previous, int logicalDiff, int graphicalDiff) {
		this.theme=previous.theme;
		this.logicalLevel=previous.logicalLevel+logicalDiff;
		this.graphicalLevel=previous.graphicalLevel+graphicalDiff;
	}
	
	public IContainerRenderer getContainerRenderer (boolean horizontal) {
		return theme.getContainerRenderer(logicalLevel,graphicalLevel,horizontal);
	}
	
	public <T> IPanelRenderer<T> getPanelRenderer (Class<T> type) {
		return theme.getPanelRenderer(type,logicalLevel,graphicalLevel);
	}
	
	public <T> IScrollBarRenderer<T> getScrollBarRenderer (Class<T> type) {
		return theme.getScrollBarRenderer(type,logicalLevel,graphicalLevel);
	}
	
	public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer (Class<T> type, boolean container) {
		return theme.getEmptySpaceRenderer(type,logicalLevel,graphicalLevel,container);
	}
	
	public <T> IButtonRenderer<T> getButtonRenderer (Class<T> type, boolean container) {
		return theme.getButtonRenderer(type,logicalLevel,graphicalLevel,container);
	}
	
	public IButtonRenderer<String> getKeybindRenderer (boolean container) {
		return theme.getKeybindRenderer(logicalLevel,graphicalLevel,container);
	}
	
	public ISliderRenderer getSliderRenderer (boolean container) {
		return theme.getSliderRenderer(logicalLevel,graphicalLevel,container);
	}
	
	public IRadioRenderer getRadioRenderer (boolean container) {
		return theme.getRadioRenderer(logicalLevel,graphicalLevel,container);
	}
	
	public ITextFieldRenderer getTextRenderer (boolean container) {
		return theme.getTextRenderer(logicalLevel,graphicalLevel,container);
	}
	
	public ISwitchRenderer<Boolean> getToggleSwitchRenderer (boolean container) {
		return theme.getToggleSwitchRenderer(logicalLevel,graphicalLevel,container);
	}
	
	public ISwitchRenderer<String> getCycleSwitchRenderer (boolean container) {
		return theme.getCycleSwitchRenderer(logicalLevel,graphicalLevel,container);
	}
}
