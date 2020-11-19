package com.lukflug.panelstudio.hud;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lukflug.panelstudio.ClickGUI;
import com.lukflug.panelstudio.FixedComponent;
import com.lukflug.panelstudio.Interface;
import com.lukflug.panelstudio.settings.Toggleable;

/**
 * ClickGUI that only renders HUD components when closed.
 * @author lukflug
 */
public class HUDClickGUI extends ClickGUI implements Toggleable {
	/**
	 * List of all components.
	 */
	protected List<FixedComponent> allComponents;
	/**
	 * List of HUD components.
	 */
	protected Set<FixedComponent> hudComponents;
	/**
	 * 
	 */
	protected boolean guiOpen=false;
	
	/**
	 * Constructor.
	 * @param inter the interface for the ClickGUI
	 */
	public HUDClickGUI(Interface inter) {
		super(inter);
		allComponents=new ArrayList<FixedComponent>();
		hudComponents=new HashSet<FixedComponent>();
	}
	
	/**
	 * Returns all components.
	 */
	@Override
	public List<FixedComponent> getComponents() {
		return allComponents;
	}
	
	/**
	 * Add component to {@link #allComponents} instead of the {@link ClickGUI} list.
	 */
	@Override
	public void addComponent (FixedComponent component) {
		allComponents.add(component);
		if (guiOpen) super.addComponent(component);
	}
	
	/**
	 * Add component to {@link #allComponents} and {@link #hudComponents}.
	 * @param component the new HUD component
	 */
	public void addHUDComponent (FixedComponent component) {
		hudComponents.add(component);
		addComponent(component);
		if (!guiOpen) super.addComponent(component);
	}

	/**
	 * Toggles GUI open state and chooses right components to render.
	 */
	@Override
	public void toggle() {
		guiOpen=!guiOpen;
		if (guiOpen) components=allComponents;
		else selectHUDComponents();
	}

	/**
	 * Reteurs whether GUI is open.
	 */
	@Override
	public boolean isOn() {
		return guiOpen;
	}
	
	/**
	 * Add only HUD components to component list.
	 */
	protected void selectHUDComponents() {
		components=new ArrayList<FixedComponent>();
		for (FixedComponent component: allComponents) {
			if (hudComponents.contains(component)) components.add(component);
		}
	}
}
