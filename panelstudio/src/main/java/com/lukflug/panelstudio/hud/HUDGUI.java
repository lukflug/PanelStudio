package com.lukflug.panelstudio.hud;

import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.container.FixedContainer;
import com.lukflug.panelstudio.container.GUI;
import com.lukflug.panelstudio.popup.IPopupPositioner;
import com.lukflug.panelstudio.theme.IDescriptionRenderer;
import com.lukflug.panelstudio.widget.ClosableComponent;

public class HUDGUI extends GUI {
	protected IBoolean guiVisibility,hudVisibility;
	
	public HUDGUI(IInterface inter, IDescriptionRenderer descriptionRenderer, IPopupPositioner descriptionPosition, IBoolean guiVisibility, IBoolean hudVisibility) {
		super(inter,descriptionRenderer,descriptionPosition);
		this.guiVisibility=guiVisibility;
		this.hudVisibility=hudVisibility;
		container=new FixedContainer(()->"GUI",null,false) {
			@Override
			public IBoolean getDefaultVisibility() {
				return guiVisibility;
			}
		};
	}
	
	public void addHUDComponent (IFixedComponent component) {
		container.addComponent(ClosableComponent.createDraggableComponent(null,null,0,false,null),()->true);
	}
}
