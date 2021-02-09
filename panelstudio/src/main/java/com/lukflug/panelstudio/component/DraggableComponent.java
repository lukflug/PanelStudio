package com.lukflug.panelstudio.component;

import java.awt.Point;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.config.IPanelConfig;

/**
 * Fixed component wrapper that can be dragged by another component.
 * @author lukflug
 */
public class DraggableComponent extends ComponentProxy implements IFixedComponent {
	/**
	 * The fixed component to be dragged.
	 */
	protected IFixedComponent fixedComponent;
	/**
	 * Flag indicating whether the user is dragging the component with the mouse.
	 */
	protected boolean dragging=false;
	/**
	 * Point storing the point, were the user started to drag the panel.
	 */
	protected Point attachPoint;

	/**
	 * Constructor.
	 * @param fixedComponent fixed component to be dragged
	 */
	public DraggableComponent (IFixedComponent fixedComponent) {
		super(fixedComponent);
		this.fixedComponent=fixedComponent;
	}

	@Override
	public Point getPosition(IInterface inter) {
		Point point=fixedComponent.getPosition(inter);
		if (dragging) point.translate(inter.getMouse().x-attachPoint.x,inter.getMouse().y-attachPoint.y);
		return point;
	}

	@Override
	public void setPosition(IInterface inter, Point position) {
		fixedComponent.setPosition(inter,position);
	}

	@Override
	public int getWidth(IInterface inter) {
		return fixedComponent.getWidth(inter);
	}

	@Override
	public boolean savesState() {
		return fixedComponent.savesState();
	}

	@Override
	public void saveConfig(IInterface inter, IPanelConfig config) {
		fixedComponent.saveConfig(inter,config);
	}

	@Override
	public void loadConfig(IInterface inter, IPanelConfig config) {
		fixedComponent.loadConfig(inter,config);
	}
	
	/**
	 * Returns the wrapped dragging component.
	 * @param dragComponent component that is used to drag the panel
	 */
	public IComponent getWrappedDragComponent (IComponent dragComponent) {
		return new ComponentProxy(dragComponent) {
			@Override
			public void handleButton (Context context, int button) {
				super.handleButton(context,button);
				if (context.isClicked() && button==IInterface.LBUTTON) {
					dragging=true;
					attachPoint=context.getInterface().getMouse();
				} else if (!context.getInterface().getButton(IInterface.LBUTTON) && dragging) {
					Point mouse=context.getInterface().getMouse();
					dragging=false;
					Point p=fixedComponent.getPosition(context.getInterface());
					p.translate(mouse.x-attachPoint.x,mouse.y-attachPoint.y);
					fixedComponent.setPosition(context.getInterface(),p);
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
