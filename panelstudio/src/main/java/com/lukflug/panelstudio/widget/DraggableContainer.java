package com.lukflug.panelstudio.widget;

import java.awt.Point;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.config.IPanelConfig;
import com.lukflug.panelstudio.theme.IRenderer;

/**
 * Class for a {@link IFixedComponent} that is also a {@link CollapsibleContainer} (i.e. a Panel), that can be dragged by the user using the mouse.
 * @author lukflug
 */
public class DraggableContainer extends CollapsibleContainer implements IFixedComponent {
	/**
	 * Flag indicating whether the user is dragging the component with the mouse.
	 */
	protected boolean dragging=false;
	/**
	 * Point storing the point, were the user started to drag the panel.
	 */
	protected Point attachPoint;
	/**
	 * Current position of the panel.
	 */
	protected Point position;
	/**
	 * The panel width.
	 */
	protected int width;
	/**
	 * Whether to allow the body to be dragged.
	 */
	protected boolean bodyDrag=false;
	
	/**
	 * Constructor.
	 * @param title caption of the container
	 * @param description the description for this component
	 * @param renderer {@link IRenderer} for the container
	 * @param open {@link IToggleable} to indicate whether the container is open or closed
	 * @param animation the animation for opening and closing the container
	 * @param toggle the {@link IToggleable} to be toggled by the user
	 * @param position the initial position of the container
	 * @param width the width of the container
	 */
	public DraggableContainer(String title, String description, IRenderer renderer, IToggleable open, Animation animation, IToggleable toggle, Point position, int width) {
		super(title,description,renderer,open,animation,toggle);
		this.position=position;
		this.width=width;
	}

	/**
	 * Handle a mouse state change, including dragging the container.
	 */
	@Override
	public void handleButton (Context context, int button) {
		if (bodyDrag) super.handleButton(context, button);
		else context.setHeight(renderer.getHeight(open.getValue()!=0));
		if (context.isClicked() && button==IInterface.LBUTTON) {
			dragging=true;
			attachPoint=context.getInterface().getMouse();
		} else if (!context.getInterface().getButton(IInterface.LBUTTON) && dragging) {
			Point mouse=context.getInterface().getMouse();
			dragging=false;
			Point p=getPosition(context.getInterface());
			p.translate(mouse.x-attachPoint.x,mouse.y-attachPoint.y);
			setPosition(context.getInterface(),p);
		}
		if (!bodyDrag) super.handleButton(context, button);
	}

	/**
	 * Get the current position of the panel.
	 */
	@Override
	public Point getPosition (IInterface inter) {
		if (dragging) {
			Point point=new Point(position);
			point.translate(inter.getMouse().x-attachPoint.x,inter.getMouse().y-attachPoint.y);
			return point;
		}
		return position;
	}

	/**
	 * Set the position of the panel.
	 */
	@Override
	public void setPosition(IInterface inter, Point position) {
		this.position=new Point(position);
	}

	@Override
	public int getWidth (IInterface inter) {
		return width;
	}

	/**
	 * Request focus within the GUI, if the panel gets focus.
	 */
	@Override
	protected void handleFocus (Context context, boolean focus) {
		if (focus) context.requestFocus();
	}

	@Override
	public void saveConfig(IInterface inter, IPanelConfig config) {
		config.savePositon(position);
		config.saveState(open.isOn());
	}

	@Override
	public void loadConfig(IInterface inter, IPanelConfig config) {
		Point pos=config.loadPosition();
		if (pos!=null) position=pos;
		if (open.isOn()!=config.loadState()) open.toggle();
	}
}
