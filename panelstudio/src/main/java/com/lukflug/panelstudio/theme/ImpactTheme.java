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
		context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y,context.getSize().width,context.getSize().height),color,color,color,color);
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
			public void renderBackground (Context context, boolean focus) {
				if (graphicalLevel==0) ImpactTheme.this.renderBackground(context,focus);
			}
			
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
				if (graphicalLevel==0) renderBackground(context,focus);
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
				Color valueColor=getFontColor(focus);
				if (context.isHovered() && context.getInterface().getMouse().x>context.getPos().x+context.getSize().height-padding) {
					if (colorLevel<2) colorLevel++;
					valueColor=scheme.getColor("Active Font Color");
				}
				Color fontColor=getFontColor(focus);
				if (colorLevel==2) fontColor=scheme.getColor("Active Font Color");
				else if (colorLevel==0) fontColor=scheme.getColor("Inactive Font Color");
				int xpos=context.getPos().x+context.getSize().height-padding;
				if (container && graphicalLevel<=0) xpos=context.getPos().x+context.getSize().width/2-context.getInterface().getFontWidth(height,title)/2;
				context.getInterface().drawString(new Point(xpos,context.getPos().y+padding-(container?1:0)),height,title,fontColor);
				if (type==String.class) {
					context.getInterface().drawString(new Point(context.getPos().x+context.getSize().width-padding-context.getInterface().getFontWidth(height,(String)state),context.getPos().y+padding-(container?1:0)),height,(String)state,valueColor);
				}
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
				Color valueColor=scheme.getColor("Active Font Color");
				Color fontColor=getFontColor(focus);
				if (context.isHovered() && context.getInterface().getMouse().x>context.getPos().x+context.getSize().height-padding) {
					fontColor=scheme.getColor("Active Font Color");
				}
				int xpos=context.getPos().x+context.getSize().height-padding;
				if (container && graphicalLevel<=0) xpos=context.getPos().x+context.getSize().width/2-context.getInterface().getFontWidth(height,title)/2;
				context.getInterface().drawString(new Point(xpos,context.getPos().y+padding-(container?1:0)),height,title,fontColor);
				context.getInterface().drawString(new Point(context.getPos().x+context.getSize().width-padding-context.getInterface().getFontWidth(height,(String)(focus?"...":state)),context.getPos().y+padding-(container?1:0)),height,(String)(focus?"...":state),valueColor);
			}

			@Override
			public int getDefaultHeight() {
				return container?getBaseHeight()-2:getBaseHeight();
			}
		};
	}

	@Override
	public ISliderRenderer getSliderRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		return new ISliderRenderer() {
			@Override
			public void renderSlider(Context context, String title, String state, boolean focus, double value) {
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
				Rectangle rect=context.getRect();
				if (!container) rect=new Rectangle(rect.x+1,rect.y+1,rect.width-2,rect.height-2);
				if (getColor(null)!=null && (title.equals("Red")||title.equals("Green")||title.equals("Blue")||title.equals("Hue")||title.equals("Saturation")||title.equals("Brightness"))) {
					Color main=getColor(null);
					Color colorA=null,colorB=null;
					float hsb[]=Color.RGBtoHSB(main.getRed(),main.getGreen(),main.getBlue(),null);
					if (title.equals("Red")) {
						colorA=new Color(0,main.getGreen(),main.getBlue());
						colorB=new Color(255,main.getGreen(),main.getBlue());
					} else if (title.equals("Green")) {
						colorA=new Color(main.getRed(),0,main.getBlue());
						colorB=new Color(main.getRed(),255,main.getBlue());
					} else if (title.equals("Blue")) {
						colorA=new Color(main.getRed(),main.getGreen(),0);
						colorB=new Color(main.getRed(),main.getGreen(),255);
					} else if (title.equals("Saturation")) {
						colorA=Color.getHSBColor(hsb[0],0,hsb[2]);
						colorB=Color.getHSBColor(hsb[0],1,hsb[2]);
					} else if (title.equals("Brightness")) {
						colorA=Color.getHSBColor(hsb[0],hsb[1],0);
						colorB=Color.getHSBColor(hsb[0],hsb[1],1);
					}
					if (colorA!=null && colorB!=null) {
						context.getInterface().fillRect(new Rectangle(context.getPos().x+1,context.getPos().y+1,context.getSize().width-2,context.getSize().height-2),colorA,colorB,colorB,colorA);
					} else {
						int a=rect.x,b=rect.width/6,c=rect.width*2/6,d=rect.width*3/6,e=rect.width*4/6,f=rect.width*5/6,g=rect.width;
						b+=a; c+=a; d+=a; e+=a; f+=a; g+=a;
						Color c0=Color.getHSBColor(0f/6,hsb[1],hsb[2]),c1=Color.getHSBColor(1f/6,hsb[1],hsb[2]),c2=Color.getHSBColor(2f/6,hsb[1],hsb[2]);
						Color c3=Color.getHSBColor(3f/6,hsb[1],hsb[2]),c4=Color.getHSBColor(4f/6,hsb[1],hsb[2]),c5=Color.getHSBColor(5f/6,hsb[1],hsb[2]);
						context.getInterface().fillRect(new Rectangle(a,rect.y,b-a,rect.height),c0,c1,c1,c0);
						context.getInterface().fillRect(new Rectangle(b,rect.y,c-b,rect.height),c1,c2,c2,c1);
						context.getInterface().fillRect(new Rectangle(c,rect.y,d-c,rect.height),c2,c3,c3,c2);
						context.getInterface().fillRect(new Rectangle(d,rect.y,e-d,rect.height),c3,c4,c4,c3);
						context.getInterface().fillRect(new Rectangle(e,rect.y,f-e,rect.height),c4,c5,c5,c4);
						context.getInterface().fillRect(new Rectangle(f,rect.y,g-f,rect.height),c5,c0,c0,c5);
					}
					renderOverlay(context);
					Color lineColor=scheme.getColor("Active Font Color");
					int separator=(int)Math.round((rect.width-1)*value);
					context.getInterface().fillRect(new Rectangle(rect.x+separator,rect.y,1,rect.height),lineColor,lineColor,lineColor,lineColor);
				} else {
					Color valueColor=scheme.getColor("Active Font Color");
					Color fontColor=getFontColor(focus);
					if (context.isHovered() && context.getInterface().getMouse().x>context.getPos().x+context.getSize().height-padding) {
						fontColor=scheme.getColor("Active Font Color");
					}
					int xpos=context.getPos().x+context.getSize().height-padding;
					if (container && graphicalLevel<=0) xpos=context.getPos().x+context.getSize().width/2-context.getInterface().getFontWidth(height,title)/2;
					context.getInterface().drawString(new Point(xpos,context.getPos().y+padding-(container?1:0)),height,title,fontColor);
					if (context.isHovered()) {
						context.getInterface().drawString(new Point(context.getPos().x+context.getSize().width-padding-context.getInterface().getFontWidth(height,(String)state),context.getPos().y+padding-(container?1:0)),height,(String)state,valueColor);
					}
					Color lineColor=scheme.getColor("Active Font Color");
					int separator=(int)Math.round((context.getSize().width-context.getSize().height+padding-(container?0:1))*value);
					context.getInterface().fillRect(new Rectangle(context.getPos().x+context.getSize().height-padding,context.getPos().y+context.getSize().height-(container?1:2),separator,1),lineColor,lineColor,lineColor,lineColor);
				}
			}

			@Override
			public int getDefaultHeight() {
				return container?getBaseHeight()-2:getBaseHeight();
			}
			
			@Override
			public Rectangle getSlideArea (Context context, String title, String state) {
				if (getColor(null)!=null && (title.equals("Red")||title.equals("Green")||title.equals("Blue")||title.equals("Hue")||title.equals("Saturation")||title.equals("Brightness"))) {
					Rectangle rect=context.getRect();
					if (!container) rect=new Rectangle(rect.x+1,rect.y+1,rect.width-2,rect.height-2);
					return rect;
				} else return new Rectangle(context.getPos().x+context.getSize().height-padding,context.getPos().y,context.getSize().width-context.getSize().height+padding-(container?0:1),context.getSize().height);
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
				Color color=getBackgroundColor(focus);
				Rectangle rect=context.getRect();
				context.getInterface().fillRect(new Rectangle(rect.x,rect.y,rect.width,getBorder()),color,color,color,color);
				context.getInterface().fillRect(new Rectangle(rect.x,rect.y+rect.height-getBorder(),rect.width,getBorder()),color,color,color,color);
				context.getInterface().fillRect(new Rectangle(rect.x,rect.y+getBorder(),getBorder(),rect.height-2*getBorder()),color,color,color,color);
				context.getInterface().fillRect(new Rectangle(rect.x+rect.width-getBorder(),rect.y+getBorder(),getBorder(),rect.height-2*getBorder()),color,color,color,color);
			}

			@Override
			public int getBorder() {
				return 2;
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
		return new StandardColorPicker() {
			@Override
			public int getPadding() {
				return padding;
			}

			@Override
			public int getBaseHeight() {
				return ImpactTheme.this.getBaseHeight();
			}
			
			@Override
			public void renderCursor (Context context, Point p, Color color) {
				Color fontColor=scheme.getColor("Active Font Color");
				context.getInterface().fillRect(new Rectangle(p.x-1,p.y-1,2,2),fontColor,fontColor,fontColor,fontColor);
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
