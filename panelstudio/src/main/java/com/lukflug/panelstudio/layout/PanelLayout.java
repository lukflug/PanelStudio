package com.lukflug.panelstudio.layout;

import java.awt.Point;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.FocusableComponent;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.setting.IClient;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.widget.Button;
import com.lukflug.panelstudio.widget.ClosableComponent;
import com.lukflug.panelstudio.widget.ToggleButton;

public class PanelLayout implements ILayout {
	protected final int width;
	protected final Point start;
	protected final int skipX,skipY;
	protected final Supplier<Animation> animation;
	
	public PanelLayout (int width, Point start, int skipX, int skipY, Supplier<Animation> animation) {
		this.width=width;
		this.start=start;
		this.skipX=skipX;
		this.skipY=skipY;
		this.animation=animation;
	}
	
	@Override
	public void populateGUI(IComponentAdder gui, IClient client, ITheme theme) {
		// TODO make sure visibility is considered
		Point pos=start;
		AtomicInteger skipY=new AtomicInteger(this.skipY);
		client.getCategories().forEach(category->{
			Button categoryTitle=new Button(category,theme.getButtonRenderer(Void.class,0,true));
			VerticalContainer categoryContent=new VerticalContainer(category,theme.getContainerRenderer(0));
			gui.addComponent(categoryTitle,categoryContent,theme,0,pos,100,animation);
			pos.translate(skipX,skipY.get());
			skipY.set(-skipY.get());
			category.getModules().forEach(module->{
				FocusableComponent moduleTitle;
				if (module.isEnabled()==null) moduleTitle=new Button(module,theme.getButtonRenderer(Void.class,1,true));
				else moduleTitle=new ToggleButton(module,module.isEnabled(),theme.getButtonRenderer(IBoolean.class,1,true));
				ClosableComponent<FocusableComponent,VerticalContainer> moduleContainer=new ClosableComponent<FocusableComponent,VerticalContainer>(moduleTitle,new VerticalContainer(module,theme.getContainerRenderer(1)),()->module.isEnabled(),new SimpleToggleable(false),animation.get(),theme.getPanelRenderer(IBoolean.class,1));
				categoryContent.addComponent(moduleContainer);
			});
		});
	}
}
