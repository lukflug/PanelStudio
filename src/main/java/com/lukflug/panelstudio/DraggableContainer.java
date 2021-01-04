package com.lukflug.panelstudio;

import java.awt.Point;

import com.lukflug.panelstudio.settings.Toggleable;
import com.lukflug.panelstudio.theme.Renderer;

/**
 * Class for a {@link FixedComponent} that is also a {@link CollapsibleContainer} (i.e. a Panel), that can be dragged by the user using the mouse.
 * @author lukflug
 */
public class DraggableContainer extends CollapsibleContainer implements FixedComponent {
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
	 * @param renderer {@link Renderer} for the container
	 * @param open {@link Toggleable} to indicate whether the container is open or closed
	 * @param animation the animation for opening and closing the container
	 * @param toggle the {@link Toggleable} to be toggled by the user
	 * @param position the initial position of the container
	 * @param width the width of the container
	 */
	public DraggableContainer(String title, String description, Renderer renderer, Toggleable open, Animation animation, Toggleable toggle, Point position, int width) {
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
		if (context.isClicked() && button==Interface.LBUTTON) {
			dragging=true;
			attachPoint=context.getInterface().getMouse();
		} else if (!context.getInterface().getButton(Interface.LBUTTON) && dragging) {
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
	public Point getPosition (Interface inter) {
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
	public void setPosition(Interface inter, Point position) {
		this.position=new Point(position);
	}

	@Override
	public int getWidth (Interface inter) {
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
	public void saveConfig(Interface inter, PanelConfig config) {
		config.savePositon(position);
		config.saveState(open.isOn());
	}

	@Override
	public void loadConfig(Interface inter, PanelConfig config) {
		Point pos=config.loadPosition();
		if (pos!=null) position=pos;
		if (open.isOn()!=config.loadState()) open.toggle();
	}
}
