package com.lukflug.panelstudio.hud;

import java.util.ArrayList;
import java.util.List;

import com.lukflug.panelstudio.ClickGUI;
import com.lukflug.panelstudio.IFixedComponent;
import com.lukflug.panelstudio.IInterface;
import com.lukflug.panelstudio.settings.IToggleable;
import com.lukflug.panelstudio.theme.IDescriptionRenderer;

/**
 * ClickGUI that only renders HUD components when closed.
 * @author lukflug
 */
public class HUDClickGUI extends ClickGUI implements IToggleable {
	/**
	 * List of all components.
	 */
	protected List<IFixedComponent> allComponents=new ArrayList<IFixedComponent>();
	/**
	 * List of HUD components.
	 */
	protected List<IFixedComponent> hudComponents=new ArrayList<IFixedComponent>();
	/**
	 * Whether the GUI components are shown or not.
	 */
	protected boolean guiOpen=false;
	
	/**
	 * Constructor.
	 * @param inter the interface for the ClickGUI
	 * @param descriptionRenderer the {@link IDescriptionRenderer} used by the GUI
	 */
	public HUDClickGUI (IInterface inter, IDescriptionRenderer descriptionRenderer) {
		super(inter,descriptionRenderer);
		components=hudComponents;
	}
	
	/**
	 * Add component to {@link #allComponents} instead of the {@link ClickGUI} list.
	 */
	@Override
	public void addComponent (IFixedComponent component) {
		allComponents.add(component);
		permanentComponents.add(component);
	}

	@Override
	public void showComponent(IFixedComponent component) {
		if (!allComponents.contains(component)) {
			allComponents.add(component);
			if (guiOpen) component.enter(getContext(component,false));
		}
	}

	@Override
	public void hideComponent(IFixedComponent component) {
		if (!permanentComponents.contains(component)) {
			if (allComponents.remove(component) && guiOpen) component.exit(getContext(component,false));
		}
	}
	
	/**
	 * Add component to {@link #allComponents} and {@link #hudComponents}.
	 * @param component the new HUD component
	 */
	public void addHUDComponent (IFixedComponent component) {
		hudComponents.add(component);
		allComponents.add(component);
		permanentComponents.add(component);
	}
	
	/**
	 * Handle the GUI being opened.
	 */
	@Override
	public void enter() {
		components=allComponents;
		guiOpen=true;
		doComponentLoop((context,component)->{
			if (!hudComponents.contains(component)) component.enter(context);
		});
	}
	
	/**
	 * Handle the GUI being closed.
	 */
	@Override
	public void exit() {
		guiOpen=false;
		doComponentLoop((context,component)->{
			if (!hudComponents.contains(component)) component.exit(context);
		});
		components=hudComponents;
	}

	/**
	 * Toggles GUI open state and chooses right components to render.
	 */
	@Override
	public void toggle() {
		if (!guiOpen) enter();
		else exit();
	}

	/**
	 * Returns whether GUI is open.
	 */
	@Override
	public boolean isOn() {
		return guiOpen;
	}

	@Override
	public IToggleable getComponentToggleable(IFixedComponent component) {
		return new IToggleable() {
			@Override
			public void toggle() {
				if (isOn()) hideComponent(component);
				else showComponent(component);
			}

			@Override
			public boolean isOn() {
				return allComponents.contains(component);
			}
		};
	}
}
