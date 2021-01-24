package com.lukflug.panelstudio.tabgui;

import com.lukflug.panelstudio.component.IComponent;

/**
 * Interface representing a part of a TabGUI.
 * @author lukflug
 */
public interface ITabGUIComponent extends IComponent {
	/**
	 * Boolean indicating to the parent whether to render this component highlighted.
	 * @return whether this component is active
	 */
	public boolean isActive();
	
	/**
	 * Called by parent to indicate this component has been selected by the user.
	 * @return whether this component is requesting focus
	 */
	public boolean select();
}
