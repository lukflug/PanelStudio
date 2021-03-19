package com.lukflug.panelstudio.hud;

import java.awt.Point;

import com.lukflug.panelstudio.base.AnimatedToggleable;
import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.component.ComponentProxy;
import com.lukflug.panelstudio.component.DraggableComponent;
import com.lukflug.panelstudio.component.IComponentProxy;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.config.IPanelConfig;
import com.lukflug.panelstudio.setting.IBooleanSetting;
import com.lukflug.panelstudio.theme.IButtonRenderer;
import com.lukflug.panelstudio.theme.IPanelRenderer;
import com.lukflug.panelstudio.theme.IPanelRendererProxy;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.widget.ClosableComponent;
import com.lukflug.panelstudio.widget.ToggleButton;

public class HUDPanel<T extends IFixedComponent> extends DraggableComponent<HUDPanel<T>.HUDPanelComponent> {
	protected T component;
	protected HUDPanel<T>.HUDPanelComponent panel;
	
	public HUDPanel (T component, IBooleanSetting state, Animation animation, ITheme theme, IBoolean renderState, int border) {
		this.component=component;
		panel=new HUDPanelComponent(state,animation,theme,renderState,border);
	}
	
	@Override
	public HUDPanel<T>.HUDPanelComponent getComponent() {
		return panel;
	}
	
	
	protected class HUDPanelComponent implements IFixedComponent,IComponentProxy<ClosableComponent<ComponentProxy<ToggleButton>,ComponentProxy<T>>> {
		protected ClosableComponent<ComponentProxy<ToggleButton>,ComponentProxy<T>> closable;
		protected IButtonRenderer<Boolean> titleRenderer;
		protected IPanelRenderer<Boolean> panelRenderer;
		protected int border;
		
		public HUDPanelComponent (IBooleanSetting state, Animation animation, ITheme theme, IBoolean renderState, int border) {
			this.border=border;
			panelRenderer=theme.getPanelRenderer(Boolean.class,0,0);
			titleRenderer=theme.getButtonRenderer(Boolean.class,0,0,true);
			closable=new ClosableComponent<ComponentProxy<ToggleButton>,ComponentProxy<T>>(getWrappedDragComponent(new ToggleButton(state,titleRenderer)),new ComponentProxy<T>(component) {
				@Override
				public int getHeight (int height) {
					return height+2*border;
				}
				
				@Override
				public Context getContext (Context context) {
					return new Context(context,context.getSize().width-2*border,new Point(border,border),context.hasFocus(),context.onTop());
				}
			},()->state.isOn(),new AnimatedToggleable(state,animation),new IPanelRendererProxy<Boolean>() {
				@Override
				public void renderBackground (Context context, boolean focus) {
					if (renderState.isOn()) IPanelRendererProxy.super.renderBackground(context,focus);
				}
				
				@Override
				public void renderPanelOverlay(Context context, boolean focus, Boolean state, boolean open) {
					if (renderState.isOn()) IPanelRendererProxy.super.renderPanelOverlay(context,focus,state,open);
				}

				@Override
				public void renderTitleOverlay(Context context, boolean focus, Boolean state, boolean open) {
					if (renderState.isOn()) IPanelRendererProxy.super.renderTitleOverlay(context,focus,state,open);
				}

				@Override
				public IPanelRenderer<Boolean> getRenderer() {
					return panelRenderer;
				}
			});
		}

		@Override
		public ClosableComponent<ComponentProxy<ToggleButton>,ComponentProxy<T>> getComponent() {
			return closable;
		}

		@Override
		public Point getPosition(IInterface inter) {
			Point pos=getPosition(inter);
			pos.translate(-panelRenderer.getLeft()-border,-panelRenderer.getTop()-titleRenderer.getDefaultHeight()-panelRenderer.getBorder()-border);
			return pos;
		}

		@Override
		public void setPosition(IInterface inter, Point position) {
			position.translate(panelRenderer.getLeft()+border,panelRenderer.getTop()+titleRenderer.getDefaultHeight()+panelRenderer.getBorder()+border);
			component.setPosition(inter,position);
		}

		@Override
		public int getWidth(IInterface inter) {
			return component.getWidth(inter)+panelRenderer.getLeft()+panelRenderer.getRight()+2*border;
		}

		@Override
		public boolean savesState() {
			return component.savesState();
		}

		@Override
		public void saveConfig(IInterface inter, IPanelConfig config) {
			component.saveConfig(inter,config);
		}

		@Override
		public void loadConfig(IInterface inter, IPanelConfig config) {
			component.loadConfig(inter,config);
		}

		@Override
		public String getConfigName() {
			return component.getConfigName();
		}
	}
}
