package com.lukflug.panelstudio.theme;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.setting.ILabeled;

public class Windows31Theme extends ThemeBase {
	protected int height,padding,scroll;
	protected String separator;

	public Windows31Theme (IColorScheme scheme, int height, int padding, int scroll, String separator) {
		super(scheme);
		this.height=height;
		this.padding=padding;
		this.separator=separator;
		this.scroll=scroll;
		scheme.createSetting(this,"Title Color","The color for panel titles.",false,true,new Color(0,0,168),false);
		scheme.createSetting(this,"Background Color","The color for the background.",false,true,new Color(252,252,252),false);
		scheme.createSetting(this,"Button Color","The main color for buttons.",false,true,new Color(192,196,200),false);
		scheme.createSetting(this,"Shadow Color","The color for button shadows.",false,true,new Color(132,136,140),false);
		scheme.createSetting(this,"Font Color","The main color for text.",false,true,new Color(0,0,0),false);
	}
	
	protected void drawButtonBase (IInterface inter, Rectangle rect, boolean focus, boolean clicked) {
		Color c1=scheme.getColor("Shadow Color");
		Color c3=getBackgroundColor(focus);
		if (clicked) {
			//Shadow
			inter.fillRect(new Rectangle(rect.x,rect.y,1,rect.height),c1,c1,c1,c1);
			inter.fillRect(new Rectangle(rect.x,rect.y,rect.width,1),c1,c1,c1,c1);
			//Content
			inter.fillRect(new Rectangle(rect.x+1,rect.y+1,rect.width-1,rect.height-1),c3,c3,c3,c3);
		} else {
			// Shadow
			inter.fillRect(new Rectangle(rect.x+rect.width-1,rect.y+1,1,rect.height-2),c1,c1,c1,c1);
			inter.fillRect(new Rectangle(rect.x+1,rect.y+rect.height-1,rect.width-2,1),c1,c1,c1,c1);
			inter.fillRect(new Rectangle(rect.x+rect.width-2,rect.y+2,1,rect.height-3),c1,c1,c1,c1);
			inter.fillRect(new Rectangle(rect.x+2,rect.y+rect.height-2,rect.width-3,1),c1,c1,c1,c1);
			// Content
			inter.fillRect(new Rectangle(rect.x+2,rect.y+2,rect.width-4,rect.height-4),c3,c3,c3,c3);
		}
	}
	
	protected void drawButton (IInterface inter, Rectangle rect, boolean focus, boolean clicked) {
		Color c0=getFontColor(focus);
		inter.fillRect(new Rectangle(rect.x,rect.y+1,1,rect.height-2),c0,c0,c0,c0);
		inter.fillRect(new Rectangle(rect.x+1,rect.y,rect.width-2,1),c0,c0,c0,c0);
		inter.fillRect(new Rectangle(rect.x+rect.width-1,rect.y+1,1,rect.height-2),c0,c0,c0,c0);
		inter.fillRect(new Rectangle(rect.x+1,rect.y+rect.height-1,rect.width-2,1),c0,c0,c0,c0);
		if (focus) {
			ITheme.drawRect(inter,new Rectangle(rect.x+1,rect.y+1,rect.width-2,rect.height-2),c0);
			drawButtonBase(inter,new Rectangle(rect.x+2,rect.y+2,rect.width-4,rect.height-4),focus,clicked);
		} else drawButtonBase(inter,new Rectangle(rect.x+1,rect.y+1,rect.width-2,rect.height-2),focus,clicked);
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
				ITheme.drawRect(inter,rect,getMainColor(true,true));
			}
		};
	}

	@Override
	public IContainerRenderer getContainerRenderer(int logicalLevel, int graphicalLevel, boolean horizontal) {
		return new IContainerRenderer() {
			@Override
			public int getBorder() {
				return 1;
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
		};
	}

	@Override
	public <T> IPanelRenderer<T> getPanelRenderer(Class<T> type, int logicalLevel, int graphicalLevel) {
		return new IPanelRenderer<T>() {
			@Override
			public void renderBackground (Context context, boolean focus) {
				Rectangle rect=context.getRect();
				Color c=getMainColor(focus,false);
				context.getInterface().fillRect(new Rectangle(rect.x+3,rect.y+3,rect.width-6,rect.height-6),c,c,c,c);
			}
			
			@Override
			public int getBorder() {
				return 1;
			}
			
			@Override
			public int getLeft() {
				return 4;
			}
			
			@Override
			public int getRight() {
				return 4;
			}
			
			@Override
			public int getTop() {
				return 4;
			}
			
			@Override
			public int getBottom() {
				return 4;
			}
			
			@Override
			public void renderPanelOverlay(Context context, boolean focus, T state, boolean open) {
				Rectangle rect=context.getRect();
				ITheme.drawRect(context.getInterface(),rect,getFontColor(focus));
				ITheme.drawRect(context.getInterface(),new Rectangle(rect.x+1,rect.y+1,rect.width-2,rect.height-2),getMainColor(focus,focus));
				ITheme.drawRect(context.getInterface(),new Rectangle(rect.x+2,rect.y+2,rect.width-4,rect.height-4),getMainColor(focus,focus));
			}

			@Override
			public void renderTitleOverlay(Context context, boolean focus, T state, boolean open) {
			}
		};
	}

	@Override
	public <T> IScrollBarRenderer<T> getScrollBarRenderer(Class<T> type, int logicalLevel, int graphicalLevel) {
		return new IScrollBarRenderer<T>() {
			@Override
			public int renderScrollBar(Context context, boolean focus, T state, boolean horizontal, int height, int position) {
				Color color=getBackgroundColor(focus);
				context.getInterface().fillRect(context.getRect(),color,color,color,color);
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public int getThickness() {
				return scroll;
			}
		};
	}

	@Override
	public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer(Class<T> type, int logicalLevel, int graphicalLevel, boolean container) {
		return new IEmptySpaceRenderer<T>() {
			@Override
			public void renderSpace(Context context, boolean focus, T state) {
				Color color;
				if (container) color=getMainColor(focus,false);
				else color=getBackgroundColor(focus);
				context.getInterface().fillRect(context.getRect(),color,color,color,color);
			}
		};
	}

	@Override
	public <T> IButtonRenderer<T> getButtonRenderer(Class<T> type, int logicalLevel, int graphicalLevel, boolean container) {
		return new IButtonRenderer<T>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, boolean containerFocus, T state) {
				boolean effFocus=container?containerFocus:focus;
				boolean active=type==Boolean.class?(Boolean)state:effFocus;
				if (!container && type==Boolean.class) {
					ITheme.drawRect(context.getInterface(),new Rectangle(context.getRect().x,context.getRect().y,height,height),getFontColor(effFocus));
					if ((Boolean)state) {
						context.getInterface().drawLine(context.getPos(),new Point(context.getRect().x+height-1,context.getRect().y+height-1),getFontColor(effFocus),getFontColor(effFocus));
						context.getInterface().drawLine(new Point(context.getRect().x+height-1,context.getRect().y+1),new Point(context.getRect().x,context.getRect().y+height),getFontColor(effFocus),getFontColor(effFocus));
					}
					context.getInterface().drawString(new Point(context.getRect().x+height+padding,context.getRect().y),height,title,getFontColor(effFocus));
					return;
				} else if (container) {
					Color color=getMainColor(effFocus,active);
					context.getInterface().fillRect(context.getRect(),color,color,color,color);
					Color lineColor=getFontColor(effFocus);
					context.getInterface().fillRect(new Rectangle(context.getRect().x,context.getRect().y+context.getRect().height-1,context.getRect().width,1),lineColor,lineColor,lineColor,lineColor);
				} else drawButton(context.getInterface(),context.getRect(),effFocus,context.isClicked(IInterface.LBUTTON));
				Color color=(container&&active)?getMainColor(effFocus,false):getFontColor(effFocus);
				String string=title;
				if (type==String.class) string+=separator+state;
				else if (type==Color.class) color=(Color)state;
				context.getInterface().drawString(new Point(context.getRect().x+context.getRect().width/2-context.getInterface().getFontWidth(height,string)/2,context.getRect().y+(container?0:3)+padding),height,string,color);
			}

			@Override
			public int getDefaultHeight() {
				if (!container && type==Boolean.class) return height;
				return container?getBaseHeight():getBaseHeight()+6;
			}
		};
	}

	@Override
	public IButtonRenderer<Void> getSmallButtonRenderer(int symbol, int logicalLevel, int graphicalLevel, boolean container) {
		return new IButtonRenderer<Void>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, boolean containerFocus, Void state) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getDefaultHeight() {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}

	@Override
	public IButtonRenderer<String> getKeybindRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		return getButtonRenderer(String.class,logicalLevel,graphicalLevel,container);
	}

	@Override
	public ISliderRenderer getSliderRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		return new ISliderRenderer() {
			@Override
			public void renderSlider(Context context, String title, String state, boolean focus, boolean containerFocus, double value) {
				boolean effFocus=container?containerFocus:focus;
				if (!container) drawButton(context.getInterface(),context.getRect(),effFocus,context.isClicked(IInterface.LBUTTON));
				Color colorA=getMainColor(effFocus,true);
				Rectangle rect=getSlideArea(context);
				int divider=(int)(rect.width*value);
				context.getInterface().fillRect(new Rectangle(rect.x,rect.y,divider,rect.height),colorA,colorA,colorA,colorA);
				Color color=container?getMainColor(effFocus,false):getFontColor(effFocus);
				String string=title+separator+state; 
				context.getInterface().drawString(new Point(context.getRect().x+context.getRect().width/2-context.getInterface().getFontWidth(height,string)/2,context.getRect().y+(container?0:3)+padding),height,string,color);
			}
			
			@Override
			public Rectangle getSlideArea (Context context) {
				if (container) return context.getRect();
				else return new Rectangle(context.getRect().x+3,context.getRect().y+3,context.getRect().width-6,context.getRect().height-6);
			}

			@Override
			public int getDefaultHeight() {
				return container?getBaseHeight():getBaseHeight()+6;
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
					Color color=getMainColor(focus,true);
					if (i==target) context.getInterface().fillRect(rect,color,color,color,color);
					context.getInterface().drawString(new Point(rect.x+padding,rect.y+padding),height,items[i].getDisplayName(),i==target?getMainColor(focus,false):getFontColor(focus));
				}
			}

			@Override
			public int getDefaultHeight (ILabeled[] items, boolean horizontal) {
				return (horizontal?1:items.length)*getBaseHeight();
			}
		};
	}
	
	@Override
	public ITextFieldRenderer getTextRenderer (boolean embed, int logicalLevel, int graphicalLevel, boolean container) {
		return new ITextFieldRenderer() {
			@Override
			public int renderTextField (Context context, String title, boolean focus, boolean containerFocus, String content, int position, int select, int boxPosition, boolean insertMode) {
				boolean effFocus=container?containerFocus:focus;
				// Declare and assign variables
				Color textColor=getFontColor(effFocus);
				Color highlightColor=getMainColor(focus,true);
				Rectangle rect=getTextArea(context,title);
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
				context.getInterface().drawString(new Point(context.getRect().x+padding,context.getRect().y+padding/2),height,title+separator,textColor);
				// Draw the box
				context.getInterface().window(rect);
				if (select>=0) {
					int x3=rect.x+padding/2-offset+context.getInterface().getFontWidth(height,content.substring(0,select));
					context.getInterface().fillRect(new Rectangle(Math.min(x1,x3),rect.y+padding/2,Math.abs(x3-x1),height),highlightColor,highlightColor,highlightColor,highlightColor);
				}
				context.getInterface().drawString(new Point(rect.x+padding/2-offset,rect.y+padding/2),height,content,textColor);
				if ((System.currentTimeMillis()/500)%2==0 && focus) {
					if (insertMode) context.getInterface().fillRect(new Rectangle(x1,rect.y+padding/2+height,x2-x1,1),textColor,textColor,textColor,textColor);
					else context.getInterface().fillRect(new Rectangle(x1,rect.y+padding/2,1,height),textColor,textColor,textColor,textColor);
				}
				ITheme.drawRect(context.getInterface(),rect,getFontColor(effFocus));
				context.getInterface().restore();
				return boxPosition;
			}

			@Override
			public int getDefaultHeight() {
				int height=getBaseHeight()-padding;
				if (height%2==1) height+=1;
				return height;
			}

			@Override
			public Rectangle getTextArea(Context context, String title) {
				Rectangle rect=context.getRect();
				int length=padding+context.getInterface().getFontWidth(height,title+separator);
				return new Rectangle(rect.x+length,rect.y,rect.width-length,rect.height);
			}
			
			@Override
			public int transformToCharPos(Context context, String title, String content, int boxPosition) {
				Rectangle rect=getTextArea(context,title);
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
				Color borderColor=getFontColor(focus);
				ITheme.drawRect(context.getInterface(),rect,borderColor);
				ITheme.drawRect(context.getInterface(),new Rectangle(rect.x,rect.y+getBorder(),rect.width,rect.height-2*getBorder()),borderColor);
				ITheme.drawRect(context.getInterface(),new Rectangle(rect.x+getBorder(),rect.y,rect.width-2*getBorder(),rect.height),borderColor);
			}

			@Override
			public int getBorder() {
				return 4;
			}
		};
	}
	
	@Override
	public ISwitchRenderer<Boolean> getToggleSwitchRenderer (int logicalLevel, int graphicalLevel, boolean container) {
		return new ISwitchRenderer<Boolean>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, boolean containerFocus, Boolean state) {
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
	public ISwitchRenderer<String> getCycleSwitchRenderer (int logicalLevel, int graphicalLevel, boolean container) {
		return new ISwitchRenderer<String>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, boolean containerFocus, String state) {
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
				return Windows31Theme.this.getBaseHeight();
			}
		};
	}

	@Override
	public int getBaseHeight() {
		return height+2*padding;
	}

	@Override
	public Color getMainColor(boolean focus, boolean active) {
		if (active) return getColor(scheme.getColor("Title Color"));
		else return scheme.getColor("Background Color");
	}

	@Override
	public Color getBackgroundColor(boolean focus) {
		return scheme.getColor("Button Color");
	}

	@Override
	public Color getFontColor(boolean focus) {
		return scheme.getColor("Font Color");
	}
}
