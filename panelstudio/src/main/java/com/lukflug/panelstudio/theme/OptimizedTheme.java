package com.lukflug.panelstudio.theme;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.IInterface;

@SuppressWarnings("unchecked")
public final class OptimizedTheme implements ITheme {
	private final ITheme theme;
	private IDescriptionRenderer descriptionRenderer=null;
	private final Map<ParameterTuple<Void,Boolean>,IContainerRenderer> containerRenderer=new HashMap<>();
	private final Map<ParameterTuple<Class<?>,Void>,IPanelRenderer<?>> panelRenderer=new HashMap<>();
	private final Map<ParameterTuple<Class<?>,Void>,IScrollBarRenderer<?>> scrollBarRenderer=new HashMap<>();
	private final Map<ParameterTuple<Class<?>,Boolean>,IEmptySpaceRenderer<?>> emptySpaceRenderer=new HashMap<>();
	private final Map<ParameterTuple<Class<?>,Boolean>,IButtonRenderer<?>> buttonRenderer=new HashMap<>();
	private final Map<ParameterTuple<Integer,Boolean>,IButtonRenderer<Void>> smallButtonRenderer=new HashMap<>();
	private final Map<ParameterTuple<Void,Boolean>,IButtonRenderer<String>> keybindRenderer=new HashMap<>();
	private final Map<ParameterTuple<Void,Boolean>,ISliderRenderer> sliderRenderer=new HashMap<>();
	private final Map<ParameterTuple<Void,Boolean>,IRadioRenderer> radioRenderer=new HashMap<>();
	private IResizeBorderRenderer resizeRenderer=null;
	private final Map<ParameterTuple<Boolean,Boolean>,ITextFieldRenderer> textRenderer=new HashMap<>();
	private final Map<ParameterTuple<Void,Boolean>,ISwitchRenderer<Boolean>> toggleSwitchRenderer=new HashMap<>();
	private final Map<ParameterTuple<Void,Boolean>,ISwitchRenderer<String>> cycleSwitchRenderer=new HashMap<>();
	private IColorPickerRenderer colorPickerRenderer=null;
	
	public OptimizedTheme (ITheme theme) {
		this.theme=theme;
	}

	@Override
	public void loadAssets(IInterface inter) {
		theme.loadAssets(inter);
	}

	@Override
	public IDescriptionRenderer getDescriptionRenderer() {
		if (descriptionRenderer==null) descriptionRenderer=theme.getDescriptionRenderer();
		return descriptionRenderer;
	}

	@Override
	public IContainerRenderer getContainerRenderer(int logicalLevel, int graphicalLevel, boolean horizontal) {
		return getRenderer(containerRenderer,()->theme.getContainerRenderer(logicalLevel,graphicalLevel,horizontal),null,logicalLevel,graphicalLevel,horizontal);
	}

	@Override
	public <T> IPanelRenderer<T> getPanelRenderer(Class<T> type, int logicalLevel, int graphicalLevel) {
		return (IPanelRenderer<T>)getRenderer(panelRenderer,()->theme.getPanelRenderer(type,logicalLevel,graphicalLevel),type,logicalLevel,graphicalLevel,null);
	}

	@Override
	public <T> IScrollBarRenderer<T> getScrollBarRenderer(Class<T> type, int logicalLevel, int graphicalLevel) {
		return (IScrollBarRenderer<T>)getRenderer(scrollBarRenderer,()->theme.getScrollBarRenderer(type,logicalLevel,graphicalLevel),type,logicalLevel,graphicalLevel,null);
	}

	@Override
	public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer(Class<T> type, int logicalLevel, int graphicalLevel, boolean container) {
		return (IEmptySpaceRenderer<T>)getRenderer(emptySpaceRenderer,()->theme.getEmptySpaceRenderer(type,logicalLevel,graphicalLevel,container),type,logicalLevel,graphicalLevel,container);
	}

	@Override
	public <T> IButtonRenderer<T> getButtonRenderer(Class<T> type, int logicalLevel, int graphicalLevel, boolean container) {
		return (IButtonRenderer<T>)getRenderer(buttonRenderer,()->theme.getButtonRenderer(type,logicalLevel,graphicalLevel,container),type,logicalLevel,graphicalLevel,container);
	}

	@Override
	public IButtonRenderer<Void> getSmallButtonRenderer(int symbol, int logicalLevel, int graphicalLevel, boolean container) {
		return getRenderer(smallButtonRenderer,()->theme.getSmallButtonRenderer(symbol,logicalLevel,graphicalLevel,container),symbol,logicalLevel,graphicalLevel,container);
	}

	@Override
	public IButtonRenderer<String> getKeybindRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		return getRenderer(keybindRenderer,()->theme.getKeybindRenderer(logicalLevel,graphicalLevel,container),null,logicalLevel,graphicalLevel,container);
	}

	@Override
	public ISliderRenderer getSliderRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		return getRenderer(sliderRenderer,()->theme.getSliderRenderer(logicalLevel,graphicalLevel,container),null,logicalLevel,graphicalLevel,container);
	}

	@Override
	public IRadioRenderer getRadioRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		return getRenderer(radioRenderer,()->theme.getRadioRenderer(logicalLevel,graphicalLevel,container),null,logicalLevel,graphicalLevel,container);
	}

	@Override
	public IResizeBorderRenderer getResizeRenderer() {
		if (resizeRenderer==null) resizeRenderer=theme.getResizeRenderer();
		return resizeRenderer;
	}

	@Override
	public ITextFieldRenderer getTextRenderer(boolean embed, int logicalLevel, int graphicalLevel, boolean container) {
		return getRenderer(textRenderer,()->theme.getTextRenderer(embed,logicalLevel,graphicalLevel,container),embed,logicalLevel,graphicalLevel,container);
	}

	@Override
	public ISwitchRenderer<Boolean> getToggleSwitchRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		return getRenderer(toggleSwitchRenderer,()->theme.getToggleSwitchRenderer(logicalLevel,graphicalLevel,container),null,logicalLevel,graphicalLevel,container);
	}

	@Override
	public ISwitchRenderer<String> getCycleSwitchRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		return getRenderer(cycleSwitchRenderer,()->theme.getCycleSwitchRenderer(logicalLevel,graphicalLevel,container),null,logicalLevel,graphicalLevel,container);
	}
	
	@Override
	public IColorPickerRenderer getColorPickerRenderer() {
		if (colorPickerRenderer==null) colorPickerRenderer=theme.getColorPickerRenderer();
		return colorPickerRenderer;
	};

	@Override
	public int getBaseHeight() {
		return theme.getBaseHeight();
	}

	@Override
	public Color getMainColor(boolean focus, boolean active) {
		return theme.getMainColor(focus,active);
	}

	@Override
	public Color getBackgroundColor(boolean focus) {
		return theme.getBackgroundColor(focus);
	}

	@Override
	public Color getFontColor(boolean focus) {
		return theme.getFontColor(focus);
	}

	@Override
	public void overrideMainColor(Color color) {
		theme.overrideMainColor(color);
	}

	@Override
	public void restoreMainColor() {
		theme.restoreMainColor();
	}
	
	private static <S,T,U> U getRenderer (Map<ParameterTuple<S,T>,U> table, Supplier<U> init, S type, int logicalLevel, int graphicalLevel, T container) {
		ParameterTuple<S,T> key=new ParameterTuple<S,T>(type,logicalLevel,graphicalLevel,container);
		U value=table.getOrDefault(key,null);
		if (value==null) table.put(key,value=init.get());
		return value;
	}

	
	private static class ParameterTuple<S,T> {
		private final S type;
		private final int logicalLevel;
		private final int graphicalLevel;
		private final T container;
		
		public ParameterTuple (S type, int logicalLevel, int graphicalLevel, T container) {
			this.type=type;
			this.logicalLevel=logicalLevel;
			this.graphicalLevel=graphicalLevel;
			this.container=container;
		}
		
		@Override
		public int hashCode() {
			return toString().hashCode();
		}
		
		@Override
		public boolean equals (Object o) {
			if (o instanceof ParameterTuple) return toString().equals(o.toString());
			else return false;
		}
		
		@Override
		public String toString() {
			return "("+type+","+logicalLevel+","+graphicalLevel+","+container+")";
		}
	}
}
