package com.lukflug.panelstudio.theme;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.setting.ILabeled;

public class ImpactTheme extends ThemeBase {
	int height,padding;

	public ImpactTheme (IColorScheme scheme, int height, int padding) {
		super(scheme);
		this.height=height;
		this.padding=padding;
		scheme.createSetting(this,"Title Color","The color for panel titles.",true,true,new Color(16,16,16,198),false);
		scheme.createSetting(this,"Background Color","The panel background color.",true,true,new Color(30,30,30,192),false);
		scheme.createSetting(this,"Panel Outline Color","The main color for panel outlines.",false,true,new Color(20,20,20),false);
		scheme.createSetting(this,"Component Outline Color","The main color for component outlines.",true,true,new Color(0,0,0,92),false);
		scheme.createSetting(this,"Active Font Color","The color for active text.",false,true,new Color(255,255,255),false);
		scheme.createSetting(this,"Hovered Font Color","The color for hovered text.",false,true,new Color(192,192,192),false);
		scheme.createSetting(this,"Inactive Font Color","The color for inactive text.",false,true,new Color(128,128,128),false);
		scheme.createSetting(this,"Enabled Color","The color for enabled modules.",false,true,new Color(91,201,79),false);
		scheme.createSetting(this,"Disabled Color","The  color for disabled modules.",false,true,new Color(194,48,48),false);
		scheme.createSetting(this,"Highlight Color","The color for highlighted text.",false,true,new Color(0,0,255),false);
		scheme.createSetting(this,"Tooltip Color","The color for description tooltips.",false,true,new Color(0,0,0,128),false);
	}
	
	protected void renderBackground (Context context, boolean focus) {
		Color color=getBackgroundColor(focus);
		context.getInterface().fillRect(new Rectangle(context.getPos().x+1,context.getPos().y+1,context.getSize().width-2,context.getSize().height-2),color,color,color,color);
	}
	
	protected void renderOverlay (Context context) {
		if (context.isHovered()) {
			Color color=new Color(0,0,0,24);
			context.getInterface().fillRect(context.getRect(),color,color,color,color);
		}
	}

	@Override
	public IDescriptionRenderer getDescriptionRenderer() {
		return new IDescriptionRenderer() {
			@Override
			public void renderDescription(IInterface inter, Point pos, String text) {
				Rectangle rect=new Rectangle(pos,new Dimension(inter.getFontWidth(height,text)+2*padding-2,height+2*padding-2));
				Color color=scheme.getColor("Tooltip Color");
				inter.fillRect(rect,color,color,color,color);
				inter.drawString(new Point(pos.x+padding-1,pos.y+padding-1),height,text,getFontColor(true));
			}
		};
	}

	@Override
	public IContainerRenderer getContainerRenderer(int logicalLevel, int graphicalLevel, boolean horizontal) {
		return new IContainerRenderer() {
			@Override
			public int getBorder() {
				return 2;
			}
			
			@Override
			public int getLeft() {
				return 2;
			}
			
			@Override
			public int getRight() {
				return 2;
			}
			
			@Override
			public int getTop() {
				return 2;
			}
			
			@Override
			public int getBottom() {
				return 2;
			}
		};
	}

	@Override
	public <T> IPanelRenderer<T> getPanelRenderer(Class<T> type, int logicalLevel, int graphicalLevel) {
		return new IPanelRenderer<T>() {
			@Override
			public void renderBackground (Context context, boolean focus) {
				if (graphicalLevel==0) ImpactTheme.this.renderBackground(context,focus);
			}
			
			@Override
			public int getBorder() {
				return graphicalLevel<=0?1:0;
			}
			
			@Override
			public int getLeft() {
				return 1;
			}
			
			@Override
			public int getRight() {
				return 1;
			}
			
			@Override
			public int getTop() {
				return 1;
			}
			
			@Override
			public int getBottom() {
				return 1;
			}
			
			@Override
			public void renderPanelOverlay(Context context, boolean focus, T state, boolean open) {
				Color color=graphicalLevel<=0?scheme.getColor("Panel Outline Color"):scheme.getColor("Component Outline Color");
				ITheme.drawRect(context.getInterface(),context.getRect(),color);
			}

			@Override
			public void renderTitleOverlay(Context context, boolean focus, T state, boolean open) {
				if (graphicalLevel==0) {
					if (open) {
						Color colorA=scheme.getColor("Panel Outline Color");
						context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y+context.getSize().height,context.getSize().width,1),colorA,colorA,colorA,colorA);
					}
				} else renderOverlay(context);
			}
		};
	}

	@Override
	public <T> IScrollBarRenderer<T> getScrollBarRenderer(Class<T> type, int logicalLevel, int graphicalLevel) {
		return new IScrollBarRenderer<T>(){};
	}

	@Override
	public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer(Class<T> type, int logicalLevel, int graphicalLevel, boolean container) {
		return new IEmptySpaceRenderer<T>() {
			@Override
			public void renderSpace(Context context, boolean focus, T state) {
			}
		};
	}

	@Override
	public <T> IButtonRenderer<T> getButtonRenderer(Class<T> type, int logicalLevel, int graphicalLevel, boolean container) {
		return new IButtonRenderer<T>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, T state) {
				if (graphicalLevel<=0) {
					if (container) {
						Color color=scheme.getColor("Title Color");
						context.getInterface().fillRect(context.getRect(),color,color,color,color);
					} else renderBackground(context,focus);
				}
				if (!container) {
					Color color=graphicalLevel<=0?scheme.getColor("Panel Outline Color"):scheme.getColor("Component Outline Color");
					ITheme.drawRect(context.getInterface(),context.getRect(),color);
					renderOverlay(context);
				}
				int colorLevel=1;
				if (type==Boolean.class) colorLevel=(Boolean)state?2:0;
				else if (type==String.class) colorLevel=2;
				if (container && graphicalLevel<=0) colorLevel=2;
				if (context.isHovered() && context.getInterface().getMouse().x>context.getPos().x+context.getSize().height-padding && colorLevel<2) colorLevel++;
				Color fontColor=getFontColor(focus);
				if (colorLevel==2) fontColor=scheme.getColor("Active Font Color");
				else if (colorLevel==0) fontColor=scheme.getColor("Inactive Font Color");
				int xpos=context.getPos().x+context.getSize().height-padding;
				if (container && graphicalLevel<=0) xpos=context.getPos().x+context.getSize().width/2-context.getInterface().getFontWidth(height,title)/2;
				context.getInterface().drawString(new Point(xpos,context.getPos().y+padding),height,title,fontColor);
			}

			@Override
			public int getDefaultHeight() {
				return container?getBaseHeight()-2:getBaseHeight();
			}
		};
	}

	@Override
	public IButtonRenderer<Void> getSmallButtonRenderer(int symbol, int logicalLevel, int graphicalLevel, boolean container) {
		return new IButtonRenderer<Void>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, Void state) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}
		};
	}

	@Override
	public IButtonRenderer<String> getKeybindRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		return new IButtonRenderer<String>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, String state) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}
		};
	}

	@Override
	public ISliderRenderer getSliderRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		return new ISliderRenderer() {
			@Override
			public void renderSlider(Context context, String title, String state, boolean focus, double value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}
		};
	}

	@Override
	public IRadioRenderer getRadioRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		return new IRadioRenderer() {
			@Override
			public void renderItem(Context context, ILabeled[] items, boolean focus, int target, double state, boolean horizontal) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getDefaultHeight(ILabeled[] items, boolean horizontal) {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}

	@Override
	public IResizeBorderRenderer getResizeRenderer() {
		return new IResizeBorderRenderer() {
			@Override
			public void drawBorder(Context context, boolean focus) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getBorder() {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}

	@Override
	public ITextFieldRenderer getTextRenderer(boolean embed, int logicalLevel, int graphicalLevel, boolean container) {
		return new ITextFieldRenderer() {
			@Override
			public int renderTextField(Context context, String title, boolean focus, String content, int position, int select, int boxPosition, boolean insertMode) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}

			@Override
			public Rectangle getTextArea(Context context, String title) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int transformToCharPos(Context context, String title, String content, int boxPosition) {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}

	@Override
	public ISwitchRenderer<Boolean> getToggleSwitchRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		return new ISwitchRenderer<Boolean>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, Boolean state) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}

			@Override
			public Rectangle getOnField(Context context) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Rectangle getOffField(Context context) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

	@Override
	public ISwitchRenderer<String> getCycleSwitchRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		return new ISwitchRenderer<String>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, String state) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}

			@Override
			public Rectangle getOnField(Context context) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Rectangle getOffField(Context context) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

	@Override
	public IColorPickerRenderer getColorPickerRenderer() {
		// TODO maybe make it look more like impact
		return new StandardColorPicker() {
			@Override
			public int getPadding() {
				return padding;
			}

			@Override
			public int getBaseHeight() {
				return ImpactTheme.this.getBaseHeight();
			}
		};
	}

	@Override
	public int getBaseHeight() {
		return height+2*padding;
	}

	@Override
	public Color getMainColor(boolean focus, boolean active) {
		if (active) return scheme.getColor("Enabled Color");
		else return scheme.getColor("Disabled Color");
	}

	@Override
	public Color getBackgroundColor(boolean focus) {
		return scheme.getColor("Background Color");
	}

	@Override
	public Color getFontColor(boolean focus) {
		return scheme.getColor("Hovered Font Color");
	}
}
