package com.lukflug.panelstudio.theme;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.setting.ILabeled;

/**
 * Recreates the appearance of GameSense 2.2.0.
 * @author lukflug
 */
public class GameSenseTheme extends ThemeBase {
	protected int height,padding,scroll;
	protected String separator;
	
	public GameSenseTheme (IColorScheme scheme, int height, int padding, int scroll, String separator) {
		super(scheme);
		this.height=height;
		this.padding=padding;
		this.scroll=scroll;
		this.separator=separator;
		scheme.createSetting(this,"Title Color","The color for panel titles.",false,true,new Color(255,0,0),false);
		scheme.createSetting(this,"Outline Color","The color for panel outlines.",false,true,new Color(255,0,0),false);
		scheme.createSetting(this,"Enabled Color","The main color for enabled components.",true,true,new Color(255,0,0,150),false);
		scheme.createSetting(this,"Disabled Color","The main color for disabled modules.",false,true,new Color(0,0,0),false);
		scheme.createSetting(this,"Settings Color","The background color for settings.",false,true,new Color(30,30,30),false);
		scheme.createSetting(this,"Font Color","The main color for text.",false,true,new Color(255,255,255),false);
		scheme.createSetting(this,"Highlight Color","The color for highlighted text.",false,true,new Color(0,0,255),false);
	}
	
	protected void fillBaseRect (Context context, boolean focus, boolean active, int logicalLevel, int graphicalLevel, Color colorState) {
		Color color=getMainColor(focus,active);
		if (logicalLevel>1 && !active) color=getBackgroundColor(focus);
		else if (graphicalLevel<=0 && active) color=ITheme.combineColors(getColor(scheme.getColor("Title Color")),scheme.getColor("Enabled Color"));
		if (colorState!=null) color=ITheme.combineColors(colorState,scheme.getColor("Enabled Color"));
		context.getInterface().fillRect(context.getRect(),color,color,color,color);
	}
	
	protected void renderOverlay (Context context) {
		Color color=context.isHovered()?new Color(255,255,255,64):new Color(0,0,0,0);
		context.getInterface().fillRect(context.getRect(),color,color,color,color);
	}
	
	@Override
	public IDescriptionRenderer getDescriptionRenderer() {
		return new IDescriptionRenderer() {
			@Override
			public void renderDescription(IInterface inter, Point pos, String text) {
				Rectangle rect=new Rectangle(pos,new Dimension(inter.getFontWidth(height,text)+4,height+4));
				Color color=getMainColor(true,false);
				inter.fillRect(rect,color,color,color,color);
				inter.drawString(new Point(pos.x+2,pos.y+2),height,text,getFontColor(true));
				Color bordercolor=scheme.getColor("Outline Color");
				inter.fillRect(new Rectangle(rect.x,rect.y,rect.width,1),bordercolor,bordercolor,bordercolor,bordercolor);
				inter.fillRect(new Rectangle(rect.x,rect.y+rect.height-1,rect.width,1),bordercolor,bordercolor,bordercolor,bordercolor);
				inter.fillRect(new Rectangle(rect.x,rect.y,1,rect.height),bordercolor,bordercolor,bordercolor,bordercolor);
				inter.fillRect(new Rectangle(rect.x+rect.width-1,rect.y,1,rect.height),bordercolor,bordercolor,bordercolor,bordercolor);
			}
		};
	}

	@Override
	public IContainerRenderer getContainerRenderer (int logicalLevel, int graphicalLevel, boolean horizontal) {
		return new IContainerRenderer() {
			@Override
			public void renderBackground (Context context, boolean focus) {
				if (graphicalLevel>0) {
					Color color=scheme.getColor("Outline Color");
					context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y,context.getSize().width,1),color,color,color,color);
					context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y+context.getSize().height-1,context.getSize().width,1),color,color,color,color);
				}
			}
			
			@Override
			public int getTop() {
				return graphicalLevel<=0?0:1;
			}
			
			@Override
			public int getBottom() {
				return graphicalLevel<=0?0:1;
			}
		};
	}
	
	@Override
	public <T> IPanelRenderer<T> getPanelRenderer (Class<T> type, int logicalLevel, int graphicalLevel) {
		return new IPanelRenderer<T>() {
			@Override
			public int getLeft() {
				return graphicalLevel==0?1:0;
			}
			
			@Override
			public int getRight() {
				return graphicalLevel==0?1:0;
			}
			
			@Override
			public void renderPanelOverlay(Context context, boolean focus, T state, boolean open) {
				if (graphicalLevel==0) {
					Color color=scheme.getColor("Outline Color");
					context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y,context.getSize().width,1),color,color,color,color);
					context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y+context.getSize().height-1,context.getSize().width,1),color,color,color,color);
					context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y,1,context.getSize().height),color,color,color,color);
					context.getInterface().fillRect(new Rectangle(context.getPos().x+context.getSize().width-1,context.getPos().y,1,context.getSize().height),color,color,color,color);
				}
			}

			@Override
			public void renderTitleOverlay(Context context, boolean focus, T state, boolean open) {
			}
		};
	}
	
	@Override
	public <T> IScrollBarRenderer<T> getScrollBarRenderer (Class<T> type, int logicalLevel, int graphicalLevel) {
		return new IScrollBarRenderer<T>() {
			@Override
			public int renderScrollBar(Context context, boolean focus, T state, boolean horizontal, int height, int position) {
				Color activecolor=getMainColor(focus,true);
				Color inactivecolor=getMainColor(focus,false);
				if (horizontal) {
					int a=(int)(position/(double)height*context.getSize().width);
					int b=(int)((position+context.getSize().width)/(double)height*context.getSize().width);
					context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y,a,context.getSize().height),inactivecolor,inactivecolor,inactivecolor,inactivecolor);
					context.getInterface().fillRect(new Rectangle(context.getPos().x+a,context.getPos().y,b-a,context.getSize().height),activecolor,activecolor,activecolor,activecolor);
					context.getInterface().fillRect(new Rectangle(context.getPos().x+b,context.getPos().y,context.getSize().width-b,context.getSize().height),inactivecolor,inactivecolor,inactivecolor,inactivecolor);
				} else {
					int a=(int)(position/(double)height*context.getSize().height);
					int b=(int)((position+context.getSize().height)/(double)height*context.getSize().height);
					context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y,context.getSize().width,a),inactivecolor,inactivecolor,inactivecolor,inactivecolor);
					context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y+a,context.getSize().width,b-a),activecolor,activecolor,activecolor,activecolor);
					context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y+b,context.getSize().width,context.getSize().height-b),inactivecolor,inactivecolor,inactivecolor,inactivecolor);
				}
				Color bordercolor=scheme.getColor("Outline Color");
				if (horizontal) context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y,context.getSize().width,1),bordercolor,bordercolor,bordercolor,bordercolor);
				else context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y,1,context.getSize().height),bordercolor,bordercolor,bordercolor,bordercolor);
				if (horizontal) return (int)((context.getInterface().getMouse().x-context.getPos().x)*height/(double)context.getSize().width-context.getSize().width/2.0);
				else return (int)((context.getInterface().getMouse().y-context.getPos().y)*height/(double)context.getSize().height-context.getSize().height/2.0);
			}

			@Override
			public int getThickness() {
				return scroll;
			}
		};
	}
	
	@Override
	public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer (Class<T> type, int logicalLevel, int graphicalLevel, boolean container) {
		return (context,focus,state)->{
			Color color;
			if (container) {
				if (logicalLevel>0) color=getBackgroundColor(focus);
				else color=getMainColor(focus,false);
			} else color=scheme.getColor("Outline Color");
			context.getInterface().fillRect(context.getRect(),color,color,color,color);
		};
	}
	
	@Override
	public <T> IButtonRenderer<T> getButtonRenderer (Class<T> type, int logicalLevel, int graphicalLevel, boolean container) {
		return new IButtonRenderer<T>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, T state) {
				if (type==Boolean.class) fillBaseRect(context,focus,(Boolean)state,logicalLevel,graphicalLevel,null);
				else if (type==Color.class) fillBaseRect(context,focus,graphicalLevel<=0,logicalLevel,graphicalLevel,(Color)state);
				else fillBaseRect(context,focus,graphicalLevel<=0,logicalLevel,graphicalLevel,null);
				if (graphicalLevel<=0 && container) {
					Color color=scheme.getColor("Outline Color");
					context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y+context.getSize().height-1,context.getSize().width,1),color,color,color,color);
				}
				renderOverlay(context);
				if (type==String.class) context.getInterface().drawString(new Point(context.getRect().x+padding,context.getRect().y+padding),height,title+separator+state,getFontColor(focus));
				else context.getInterface().drawString(new Point(context.getRect().x+padding,context.getRect().y+padding),height,title,getFontColor(focus));
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}
		};
	}
	
	@Override
	public IButtonRenderer<Void> getSmallButtonRenderer (int symbol, int logicalLevel, int graphicalLevel, boolean container) {
		return new IButtonRenderer<Void>() {
			@Override
			public void renderButton (Context context, String title, boolean focus, Void state) {
				fillBaseRect(context,focus,true,logicalLevel,graphicalLevel,null);
				renderOverlay(context);
				Point points[]=new Point[3];
				Rectangle rect=new Rectangle(context.getRect().x+padding,context.getRect().y+padding,context.getRect().height-2*padding,context.getRect().height-2*padding);
				if (title==null) rect.x+=context.getRect().width/2-context.getRect().height/2;
				switch (symbol) {
				case ITheme.CLOSE:
					break;
				case ITheme.MINIMIZE:
					break;
				case ITheme.ADD:
					break;
				case ITheme.LEFT:
					if (rect.height%2==1) rect.height-=1;
					points[0]=new Point(rect.x,rect.y);
					points[1]=new Point(rect.x,rect.y+rect.height);
					points[2]=new Point(rect.x+rect.width,rect.y+rect.height/2);
					break;
				case ITheme.RIGHT:
					if (rect.height%2==1) rect.height-=1;
					points[2]=new Point(rect.x+rect.width,rect.y);
					points[1]=new Point(rect.x+rect.width,rect.y+rect.height);
					points[0]=new Point(rect.x,rect.y+rect.height/2);
					break;
				case ITheme.UP:
					if (rect.width%2==1) rect.width-=1;
					points[0]=new Point(rect.x,rect.y+rect.height);
					points[1]=new Point(rect.x+rect.width,rect.y+rect.height);
					points[2]=new Point(rect.x+rect.width/2,rect.y);
					break;
				case ITheme.DOWN:
					if (rect.width%2==1) rect.width-=1;
					points[0]=new Point(rect.x,rect.y);
					points[1]=new Point(rect.x+rect.width,rect.y);
					points[2]=new Point(rect.x+rect.width/2,rect.y+rect.height);
					break;
				}
				if (symbol>=ITheme.LEFT && symbol<=ITheme.DOWN) {
					Color color=getFontColor(focus);
					context.getInterface().fillTriangle(points[0],points[1],points[2],color,color,color);
				}
				if (title!=null) context.getInterface().drawString(new Point(context.getRect().x+(symbol==ITheme.NONE?padding:context.getRect().height),context.getRect().y+padding),height,title,getFontColor(focus));
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
				fillBaseRect(context,focus,focus,logicalLevel,graphicalLevel,null);
				renderOverlay(context);
				context.getInterface().drawString(new Point(context.getRect().x+padding,context.getRect().y+padding),height,title+separator+(focus?"...":state),getFontColor(focus));
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
				Color colorA=getMainColor(focus,true),colorB=getBackgroundColor(focus);
				Rectangle rect=getSlideArea(context);
				int divider=(int)(rect.width*value);
				context.getInterface().fillRect(new Rectangle(rect.x,rect.y,divider,rect.height),colorA,colorA,colorA,colorA);
				context.getInterface().fillRect(new Rectangle(rect.x+divider,rect.y,rect.width-divider,rect.height),colorB,colorB,colorB,colorB);
				renderOverlay(context);
				context.getInterface().drawString(new Point(context.getRect().x+padding,context.getRect().y+padding),height,title+separator+state,getFontColor(focus));
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
			public void renderItem (Context context, ILabeled[] items, boolean focus, int target, double state, boolean horizontal) {
				for (int i=0;i<items.length;i++) {
					Rectangle rect=getItemRect(context,items,i,horizontal);
					Context subContext=new Context(context.getInterface(),rect.width,rect.getLocation(),context.hasFocus(),context.onTop());
					subContext.setHeight(rect.height);
					fillBaseRect(subContext,focus,i==target,logicalLevel,graphicalLevel,null);
					renderOverlay(subContext);
					context.getInterface().drawString(new Point(rect.x+padding,rect.y+padding),height,items[i].getDisplayName(),getFontColor(focus));
				}
			}

			@Override
			public int getDefaultHeight (ILabeled[] items, boolean horizontal) {
				return (horizontal?1:items.length)*getBaseHeight();
			}
		};
	}

	@Override
	public IResizeBorderRenderer getResizeRenderer() {
		return new IResizeBorderRenderer() {
			@Override
			public void drawBorder(Context context, boolean focus) {
				Color color=ITheme.combineColors(scheme.getColor("Outline Color"),scheme.getColor("Enabled Color"));
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
	public ITextFieldRenderer getTextRenderer (int logicalLevel, int graphicalLevel, boolean container) {
		return new ITextFieldRenderer() {
			@Override
			public int renderTextField (Context context, String title, boolean focus, String content, int position, int select, int boxPosition, boolean insertMode) {
				// Declare and assign variables
				Color color=focus?scheme.getColor("Outline Color"):scheme.getColor("Settings Color");
				Color textColor=getFontColor(focus);
				Color highlightColor=scheme.getColor("Highlight Color");
				Rectangle rect=getTextArea(context,content);
				int strlen=context.getInterface().getFontWidth(height,content.substring(0,position));
				// Deal with box render offset
				if (boxPosition<position) {
					int minPosition=boxPosition;
					while (minPosition<position) {
						if (context.getInterface().getFontWidth(height,content.substring(0,minPosition))+rect.width-padding>=strlen) break;
						minPosition++;
					}
					if (boxPosition<minPosition) boxPosition=minPosition;
				} else if (boxPosition>position) boxPosition=position-1;
				int maxPosition=content.length();
				while (maxPosition>0) {
					if (context.getInterface().getFontWidth(height,content.substring(maxPosition))>=rect.width-padding) {
						maxPosition++;
						break;
					}
					maxPosition--;
				}
				if (boxPosition>maxPosition) boxPosition=maxPosition;
				else if (boxPosition<0) boxPosition=0;
				int offset=context.getInterface().getFontWidth(height,content.substring(0,boxPosition));
				// Deal with highlighted text
				int x1=rect.x+padding/2-offset+strlen;
				int x2=rect.x+padding/2-offset;
				if (position<content.length()) x2+=context.getInterface().getFontWidth(height,content.substring(0,position+1));
				else x2+=context.getInterface().getFontWidth(height,content+"X");
				// Draw stuff around the box
				fillBaseRect(context,focus,false,logicalLevel,graphicalLevel,null);
				renderOverlay(context);
				if (title!=null) context.getInterface().drawString(new Point(context.getRect().x+padding,context.getRect().y+padding),height,title,textColor);
				// Draw the box
				context.getInterface().window(rect);
				if (select>=0) {
					int x3=rect.x+padding/2-offset+context.getInterface().getFontWidth(height,content.substring(0,select));
					context.getInterface().fillRect(new Rectangle(Math.min(x1,x3),rect.y+padding/2,Math.abs(x3-x1),height),highlightColor,highlightColor,highlightColor,highlightColor);
				}
				context.getInterface().drawString(new Point(rect.x+padding/2-offset,rect.y+padding/2),height,content,textColor);
				if ((System.currentTimeMillis()/500)%2==0) {
					if (insertMode) context.getInterface().fillRect(new Rectangle(x1,rect.y+padding/2+height,x2-x1,1),textColor,textColor,textColor,textColor);
					else context.getInterface().fillRect(new Rectangle(x1,rect.y+padding/2,1,height),textColor,textColor,textColor,textColor);
				}
				context.getInterface().fillRect(new Rectangle(rect.x,rect.y,rect.width,1),color,color,color,color);
				context.getInterface().fillRect(new Rectangle(rect.x,rect.y+rect.height-1,rect.width,1),color,color,color,color);
				context.getInterface().fillRect(new Rectangle(rect.x,rect.y,1,rect.height),color,color,color,color);
				context.getInterface().fillRect(new Rectangle(rect.x+rect.width-1,rect.y,1,rect.height),color,color,color,color);
				context.getInterface().restore();
				return boxPosition;
			}

			@Override
			public int getDefaultHeight() {
				return 2*getBaseHeight();
			}

			@Override
			public Rectangle getTextArea (Context context, String title) {
				Rectangle rect=context.getRect();
				return title==null?rect:new Rectangle(rect.x+padding,rect.y+getBaseHeight(),rect.width-2*padding,rect.height-getBaseHeight()-padding);
			}

			@Override
			public int transformToCharPos(Context context, String content, int boxPosition) {
				Rectangle rect=getTextArea(context,content);
				Point mouse=context.getInterface().getMouse();
				int offset=context.getInterface().getFontWidth(height,content.substring(0,boxPosition));
				if (rect.contains(mouse)) {
					for (int i=1;i<=content.length();i++) {
						if (rect.x+padding/2-offset+context.getInterface().getFontWidth(height,content.substring(0,i))>mouse.x) {
							return i-1;
						}
					}
					return content.length();
				}
				return -1;
			}
		};
	}
	
	public ISwitchRenderer<Boolean> getToggleSwitchRenderer (int logicalLevel, int graphicalLevel, boolean container) {
		return new ISwitchRenderer<Boolean>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, Boolean state) {
				fillBaseRect(context,focus,false,logicalLevel,graphicalLevel,null);
				Color color=scheme.getColor("Outline Color");
				if (graphicalLevel<=0 && container) {
					context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y+context.getSize().height-1,context.getSize().width,1),color,color,color,color);
				}
				renderOverlay(context);
				context.getInterface().drawString(new Point(context.getRect().x+padding,context.getRect().y+padding),height,title+separator+(state?"On":"Off"),getFontColor(focus));
				Color fillColor=getMainColor(focus,true);
				Rectangle rect=state?getOnField(context):getOffField(context);
				context.getInterface().fillRect(rect,fillColor,fillColor,fillColor,fillColor);
				rect=context.getRect();
				rect=new Rectangle(rect.x+rect.width-2*rect.height+3*padding,rect.y+padding,2*rect.height-4*padding,rect.height-2*padding);
				context.getInterface().fillRect(new Rectangle(rect.x,rect.y,rect.width,1),color,color,color,color);
				context.getInterface().fillRect(new Rectangle(rect.x,rect.y+rect.height-1,rect.width,1),color,color,color,color);
				context.getInterface().fillRect(new Rectangle(rect.x,rect.y,1,rect.height),color,color,color,color);
				context.getInterface().fillRect(new Rectangle(rect.x+rect.width-1,rect.y,1,rect.height),color,color,color,color);
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}

			@Override
			public Rectangle getOnField(Context context) {
				Rectangle rect=context.getRect();
				return new Rectangle(rect.x+rect.width-rect.height+padding,rect.y+padding,rect.height-2*padding,rect.height-2*padding);
			}

			@Override
			public Rectangle getOffField(Context context) {
				Rectangle rect=context.getRect();
				return new Rectangle(rect.x+rect.width-2*rect.height+3*padding,rect.y+padding,rect.height-2*padding,rect.height-2*padding);
			}
		};
	}
	
	public ISwitchRenderer<String> getCycleSwitchRenderer (int logicalLevel, int graphicalLevel, boolean container) {
		return new ISwitchRenderer<String>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, String state) {
				fillBaseRect(context,focus,false,logicalLevel,graphicalLevel,null);
				Color color=scheme.getColor("Outline Color");
				if (graphicalLevel<=0 && container) {
					context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y+context.getSize().height-1,context.getSize().width,1),color,color,color,color);
				}
				renderOverlay(context);
				Color textColor=getFontColor(focus);
				context.getInterface().drawString(new Point(context.getRect().x+padding,context.getRect().y+padding),height,title+separator+state,textColor);
				Rectangle rect=getOnField(context);
				Context subContext=new Context(context,rect.width,new Point(rect.x-context.getRect().x,0),true,true);
				subContext.setHeight(rect.height);
				getSmallButtonRenderer(ITheme.LEFT,logicalLevel,graphicalLevel,container).renderButton(subContext,null,focus,null);
				rect=getOffField(context);
				subContext=new Context(context,rect.width,new Point(rect.x-context.getRect().x,0),true,true);
				subContext.setHeight(rect.height);
				getSmallButtonRenderer(ITheme.RIGHT,logicalLevel,graphicalLevel,container).renderButton(subContext,null,focus,null);
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}

			@Override
			public Rectangle getOnField(Context context) {
				Rectangle rect=context.getRect();
				return new Rectangle(rect.x+rect.width-rect.height,rect.y,rect.height,rect.height);
			}

			@Override
			public Rectangle getOffField(Context context) {
				Rectangle rect=context.getRect();
				return new Rectangle(rect.x+rect.width-2*rect.height,rect.y,rect.height,rect.height);
			}
		};
	}

	@Override
	public int getBaseHeight() {
		return height+2*padding;
	}

	@Override
	public Color getMainColor(boolean focus, boolean active) {
		if (active) return ITheme.combineColors(getColor(scheme.getColor("Enabled Color")),scheme.getColor("Enabled Color"));
		else return ITheme.combineColors(scheme.getColor("Disabled Color"),scheme.getColor("Enabled Color"));
	}

	@Override
	public Color getBackgroundColor(boolean focus) {
		return ITheme.combineColors(scheme.getColor("Settings Color"),scheme.getColor("Enabled Color"));
	}

	@Override
	public Color getFontColor(boolean focus) {
		return scheme.getColor("Font Color");
	}
}
