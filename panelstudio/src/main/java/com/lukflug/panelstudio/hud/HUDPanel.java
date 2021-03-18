package com.lukflug.panelstudio.hud;

import java.awt.Point;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.component.DraggableComponent;
import com.lukflug.panelstudio.component.IComponentProxy;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.config.IPanelConfig;
import com.lukflug.panelstudio.setting.IBooleanSetting;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.widget.ClosableComponent;
import com.lukflug.panelstudio.widget.ToggleButton;

public class HUDPanel<T extends IFixedComponent> extends DraggableComponent<HUDPanel<T>.HUDPanelComponent> {
	protected T component;
	protected HUDPanel<T>.HUDPanelComponent panel;
	
	public HUDPanel (T component, IBooleanSetting state, Animation animation, ITheme theme) {
		this.component=component;
		panel=new HUDPanelComponent(state,animation,theme);
	}
	
	@Override
	public HUDPanel<T>.HUDPanelComponent getComponent() {
		return panel;
	}
	
	
	protected class HUDPanelComponent implements IFixedComponent,IComponentProxy<ClosableComponent<ToggleButton,T>> {
		protected ClosableComponent<ToggleButton,T> closable;
		
		public HUDPanelComponent (IBooleanSetting state, Animation animation, ITheme theme) {
			closable=new ClosableComponent<ToggleButton,T>(new ToggleButton(state,theme.getButtonRenderer(Boolean.class,0,0,true)),component,()->state.isOn(),state,animation,theme.getPanelRenderer(Boolean.class,0,0));
		}

		@Override
		public ClosableComponent<ToggleButton,T> getComponent() {
			return closable;
		}

		@Override
		public Point getPosition(IInterface inter) {
			return getPosition(inter);
		}

		@Override
		public void setPosition(IInterface inter, Point position) {
			component.setPosition(inter,position);
		}

		@Override
		public int getWidth(IInterface inter) {
			return component.getWidth(inter);
		}

		@Override
		public boolean savesState() {
			return component.savesState();
		}

		@Override
		public void saveConfig(IInterface inter, IPanelConfig config) {
			component.saveConfig(inter,config);
			config.saveState(closable.getCollapsible().getToggle().isOn());
		}

		@Override
		public void loadConfig(IInterface inter, IPanelConfig config) {
			component.loadConfig(inter,config);
			if (closable.getCollapsible().getToggle().isOn()!=config.loadState()) closable.getCollapsible().getToggle().toggle();
		}

		@Override
		public String getConfigName() {
			return component.getConfigName();
		}
	}
}
