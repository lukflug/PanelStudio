package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.component.CollapsibleComponent;
import com.lukflug.panelstudio.component.ComponentProxy;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.theme.IContainerRenderer;

/**
 * A panel that can be opened and closed.
 * @author lukflug
 */
public class Panel extends ComponentProxy {
	/**
	 * Creates a generic panel.
	 * @param title the title component of the panel
	 * @param content the content of the panel
	 * @param toggle the toggleable to use for opening and closing the panel 
	 * @param animation the animation to use for opening and closing the panel
	 * @return a vertical container having the functionality of a panel
	 */
	public Panel (IComponent title, IComponent content, IToggleable toggle, Animation animation, IPanelRenderer renderer) {
		VerticalContainer container=new VerticalContainer(title.getTitle(),null,()->content.isVisible(),new IContainerRenderer(){}) {
			@Override
			public void render (Context context) {
				super.render(context);
				renderer.renderPanelOverlay(context,hasFocus(),active);
			}
		};
		CollapsibleComponent collapsible=new CollapsibleComponent(content,toggle,animation);
		container.addComponent(new ComponentProxy(title) {
			@Override
			public void handleButton (Context context, int button) {
				super.handleButton(context,button);
				if (button==IInterface.RBUTTON && context.isClicked()) {
					collapsible.getToggle().toggle();
				}
			}
		});
		container.addComponent(collapsible);
		return container;
	}
}
