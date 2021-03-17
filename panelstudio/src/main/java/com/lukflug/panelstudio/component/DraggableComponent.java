package com.lukflug.panelstudio.component;

import java.awt.Point;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.config.IPanelConfig;

/**
 * Fixed component wrapper that can be dragged by another component.
 * @author lukflug
 */
public abstract class DraggableComponent<T extends IFixedComponent> implements IComponentProxy<T>,IFixedComponent {
	/**
	 * Flag indicating whether the user is dragging the component with the mouse.
	 */
	protected boolean dragging=false;
	/**
	 * Point storing the point, were the user started to drag the panel.
	 */
	protected Point attachPoint;

	@Override
	public Point getPosition(IInterface inter) {
		Point point=getComponent().getPosition(inter);
		if (dragging) point.translate(inter.getMouse().x-attachPoint.x,inter.getMouse().y-attachPoint.y);
		return point;
	}

	@Override
	public void setPosition(IInterface inter, Point position) {
		getComponent().setPosition(inter,position);
	}

	@Override
	public int getWidth(IInterface inter) {
		return getComponent().getWidth(inter);
	}

	@Override
	public boolean savesState() {
		return getComponent().savesState();
	}

	@Override
	public void saveConfig(IInterface inter, IPanelConfig config) {
		getComponent().saveConfig(inter,config);
	}

	@Override
	public void loadConfig(IInterface inter, IPanelConfig config) {
		getComponent().loadConfig(inter,config);
	}
	
	@Override
	public String getConfigName() {
		return getComponent().getConfigName();
	}
	
	/**
	 * Returns the wrapped dragging component.
	 * @param dragComponent component that is used to drag the panel
	 */
	public <S extends IComponent> ComponentProxy<S> getWrappedDragComponent (S dragComponent) {
		return new ComponentProxy<S>(dragComponent) {
			@Override
			public void handleButton (Context context, int button) {
				super.handleButton(context,button);
				if (context.isClicked() && button==IInterface.LBUTTON) {
					dragging=true;
					attachPoint=context.getInterface().getMouse();
				} else if (!context.getInterface().getButton(IInterface.LBUTTON) && dragging) {
					Point mouse=context.getInterface().getMouse();
					dragging=false;
					Point p=DraggableComponent.this.getComponent().getPosition(context.getInterface());
					p.translate(mouse.x-attachPoint.x,mouse.y-attachPoint.y);
					DraggableComponent.this.getComponent().setPosition(context.getInterface(),p);
				}
			}
			
			@Override
			public void exit() {
				dragging=false;
				super.exit();
			}
		};
	}
}
