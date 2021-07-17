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
	
	protected void drawButtonBase (IInterface inter, Rectangle rect, boolean focus, boolean clicked, boolean small) {
		Color c1=scheme.getColor("Shadow Color");
		Color c2=getMainColor(focus,false);
		Color c3=getBackgroundColor(focus);
		if (clicked) {
			//Shadow
			inter.fillRect(new Rectangle(rect.x,rect.y,1,rect.height),c1,c1,c1,c1);
			inter.fillRect(new Rectangle(rect.x,rect.y,rect.width,1),c1,c1,c1,c1);
			//Content
			inter.fillRect(new Rectangle(rect.x+1,rect.y+1,rect.width-1,rect.height-1),c3,c3,c3,c3);
		} else {
			// Shadow
			inter.fillRect(new Rectangle(rect.x+rect.width-1,rect.y,1,rect.height),c1,c1,c1,c1);
			inter.fillRect(new Rectangle(rect.x,rect.y+rect.height-1,rect.width,1),c1,c1,c1,c1);
			inter.fillRect(new Rectangle(rect.x+rect.width-2,rect.y+1,1,rect.height-1),c1,c1,c1,c1);
			inter.fillRect(new Rectangle(rect.x+1,rect.y+rect.height-2,rect.width-1,1),c1,c1,c1,c1);
			// Content
			if (small) inter.fillRect(new Rectangle(rect.x+1,rect.y+1,rect.width-3,rect.height-3),c3,c3,c3,c3);
			else inter.fillRect(new Rectangle(rect.x+2,rect.y+2,rect.width-4,rect.height-4),c3,c3,c3,c3);
			// Light
			inter.fillRect(new Rectangle(rect.x,rect.y,rect.width-1,1),c2,c2,c2,c2);
			inter.fillRect(new Rectangle(rect.x,rect.y,1,rect.height-1),c2,c2,c2,c2);
			if (!small) {
				inter.fillRect(new Rectangle(rect.x+1,rect.y+1,rect.width-3,1),c2,c2,c2,c2);
				inter.fillRect(new Rectangle(rect.x+1,rect.y+1,1,rect.height-3),c2,c2,c2,c2);
			}
		}
	}
	
	protected void drawButton (IInterface inter, Rectangle rect, boolean focus, boolean clicked, boolean small) {
		Color c0=getFontColor(focus);
		if (small) ITheme.drawRect(inter,rect,c0);
		else {
			inter.fillRect(new Rectangle(rect.x,rect.y+1,1,rect.height-2),c0,c0,c0,c0);
			inter.fillRect(new Rectangle(rect.x+1,rect.y,rect.width-2,1),c0,c0,c0,c0);
			inter.fillRect(new Rectangle(rect.x+rect.width-1,rect.y+1,1,rect.height-2),c0,c0,c0,c0);
			inter.fillRect(new Rectangle(rect.x+1,rect.y+rect.height-1,rect.width-2,1),c0,c0,c0,c0);
		}
		if (focus && !small) {
			ITheme.drawRect(inter,new Rectangle(rect.x+1,rect.y+1,rect.width-2,rect.height-2),c0);
			drawButtonBase(inter,new Rectangle(rect.x+2,rect.y+2,rect.width-4,rect.height-4),focus,clicked,small);
		} else drawButtonBase(inter,new Rectangle(rect.x+1,rect.y+1,rect.width-2,rect.height-2),focus,clicked,small);
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
				int d=horizontal?context.getSize().height:context.getSize().width;
				int x=context.getPos().x+(horizontal?(int)(position/(double)(height-context.getSize().width)*(context.getSize().width-2*d)):0);
				int y=context.getPos().y+(horizontal?0:(int)(position/(double)(height-context.getSize().height)*(context.getSize().height-2*d)));
				Rectangle rect=new Rectangle(x,y,d*(horizontal?2:1),d*(horizontal?1:2));
				Windows31Theme.this.drawButton(context.getInterface(),rect,focus,context.isClicked(IInterface.LBUTTON)&&rect.contains(context.getInterface().getMouse()),true);
				if (horizontal) return (int)Math.round((context.getInterface().getMouse().x-context.getPos().x-d)/(double)(context.getSize().width-2*d)*(height-context.getSize().width));
				else return (int)Math.round((context.getInterface().getMouse().y-context.getPos().y-d)/(double)(context.getSize().height-2*d)*(height-context.getSize().height));
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
			public void renderButton(Context context, String title, boolean focus, T state) {
				boolean effFocus=container?context.hasFocus():focus;
				boolean active=type==Boolean.class?(Boolean)state:effFocus;
				if (!container && type==Boolean.class) {
					ITheme.drawRect(context.getInterface(),new Rectangle(context.getPos().x,context.getPos().y,height,height),getFontColor(effFocus));
					if ((Boolean)state) {
						context.getInterface().drawLine(context.getPos(),new Point(context.getPos().x+height-1,context.getPos().y+height-1),getFontColor(effFocus),getFontColor(effFocus));
						context.getInterface().drawLine(new Point(context.getPos().x+height-1,context.getPos().y+1),new Point(context.getPos().x,context.getPos().y+height),getFontColor(effFocus),getFontColor(effFocus));
					}
					context.getInterface().drawString(new Point(context.getPos().x+height+padding,context.getPos().y),height,title,getFontColor(effFocus));
					return;
				} else if (container) {
					Color color=getMainColor(effFocus,active);
					context.getInterface().fillRect(context.getRect(),color,color,color,color);
					Color lineColor=getFontColor(effFocus);
					context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y+context.getSize().height-1,context.getSize().width,1),lineColor,lineColor,lineColor,lineColor);
				} else drawButton(context.getInterface(),context.getRect(),effFocus,context.isClicked(IInterface.LBUTTON),false);
				Color color=(container&&active)?getMainColor(effFocus,false):getFontColor(effFocus);
				String string=title;
				if (type==String.class) string+=separator+state;
				else if (type==Color.class) color=(Color)state;
				context.getInterface().drawString(new Point(context.getPos().x+context.getSize().width/2-context.getInterface().getFontWidth(height,string)/2,context.getPos().y+(container?0:3)+padding),height,string,color);
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
			public void renderButton(Context context, String title, boolean focus, Void state) {
				Windows31Theme.this.drawButton(context.getInterface(),context.getRect(),focus,context.isClicked(IInterface.LBUTTON),true);
				Point points[]=new Point[3];
				int padding=context.getSize().height<=12?4:6;
				Rectangle rect=new Rectangle(context.getPos().x+padding/2,context.getPos().y+padding/2,context.getSize().height-2*(padding/2),context.getSize().height-2*(padding/2));
				if (title==null) rect.x+=context.getSize().width/2-context.getSize().height/2;
				Color color=getFontColor(focus);
				switch (symbol) {
				case ITheme.CLOSE:
					context.getInterface().drawLine(new Point(rect.x,rect.y),new Point(rect.x+rect.width,rect.y+rect.height),color,color);
					context.getInterface().drawLine(new Point(rect.x,rect.y+rect.height),new Point(rect.x+rect.width,rect.y),color,color);
					break;
				case ITheme.MINIMIZE:
					context.getInterface().fillRect(new Rectangle(rect.x,rect.y+rect.height-2,rect.width,2),color,color,color,color);
					break;
				case ITheme.ADD:
					if (rect.width%2==1) rect.width-=1;
					if (rect.height%2==1) rect.height-=1;
					context.getInterface().fillRect(new Rectangle(rect.x+rect.width/2-1,rect.y,2,rect.height),color,color,color,color);
					context.getInterface().fillRect(new Rectangle(rect.x,rect.y+rect.height/2-1,rect.width,2),color,color,color,color);
					break;
				case ITheme.LEFT:
					if (rect.height%2==1) rect.height-=1;
					points[2]=new Point(rect.x+rect.width,rect.y);
					points[1]=new Point(rect.x+rect.width,rect.y+rect.height);
					points[0]=new Point(rect.x,rect.y+rect.height/2);
					break;
				case ITheme.RIGHT:
					if (rect.height%2==1) rect.height-=1;
					points[0]=new Point(rect.x,rect.y);
					points[1]=new Point(rect.x,rect.y+rect.height);
					points[2]=new Point(rect.x+rect.width,rect.y+rect.height/2);
					break;
				case ITheme.UP:
					if (rect.width%2==1) rect.width-=1;
					points[0]=new Point(rect.x,rect.y+rect.height);
					points[1]=new Point(rect.x+rect.width,rect.y+rect.height);
					points[2]=new Point(rect.x+rect.width/2,rect.y);
					break;
				case ITheme.DOWN:
					if (rect.width%2==1) rect.width-=1;
					points[2]=new Point(rect.x,rect.y);
					points[1]=new Point(rect.x+rect.width,rect.y);
					points[0]=new Point(rect.x+rect.width/2,rect.y+rect.height);
					break;
				}
				if (symbol>=ITheme.LEFT && symbol<=ITheme.DOWN) {
					context.getInterface().fillTriangle(points[0],points[1],points[2],color,color,color);
				}
				if (title!=null) context.getInterface().drawString(new Point(context.getPos().x+(symbol==ITheme.NONE?padding:context.getSize().height),context.getPos().y+padding),height,title,getFontColor(focus));
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
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
			public void renderSlider(Context context, String title, String state, boolean focus, double value) {
				boolean effFocus=container?context.hasFocus():focus;
				Color colorA=getMainColor(effFocus,true);
				if (container && effFocus) context.getInterface().fillRect(context.getRect(),colorA,colorA,colorA,colorA);
				Rectangle rect=getSlideArea(context,title,state);
				Color colorB=getBackgroundColor(effFocus);
				context.getInterface().fillRect(rect,colorB,colorB,colorB,colorB);
				ITheme.drawRect(context.getInterface(),rect,getFontColor(effFocus));
				int divider=(int)((rect.width-rect.height)*value);
				Rectangle buttonRect=new Rectangle(rect.x+divider,rect.y,rect.height,rect.height);
				boolean clicked=context.isClicked(IInterface.LBUTTON) && buttonRect.contains(context.getInterface().getMouse());
				Windows31Theme.this.drawButton(context.getInterface(),buttonRect,effFocus,clicked,true);
				Color color=(container&&effFocus)?getMainColor(effFocus,false):getFontColor(effFocus);
				String string=title+separator+state; 
				context.getInterface().drawString(new Point(context.getPos().x+padding,context.getPos().y+padding),height,string,color);
			}
			
			@Override
			public Rectangle getSlideArea (Context context, String title, String state) {
				if (container) return context.getRect();
				else return new Rectangle(context.getPos().x,context.getPos().y+context.getSize().height-height,context.getSize().width,height);
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight()+height;
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
			public int renderTextField (Context context, String title, boolean focus, String content, int position, int select, int boxPosition, boolean insertMode) {
				boolean effFocus=container?(context.hasFocus()||focus):focus;
				// Declare and assign variables
				Color textColor=getFontColor(effFocus);
				Color titleColor=(container&&effFocus)?getMainColor(effFocus,false):textColor;
				Color highlightColor=getMainColor(effFocus,true);
				Rectangle rect=getTextArea(context,title);
				int strlen=context.getInterface().getFontWidth(height,content.substring(0,position));
				if (container && effFocus) {
					context.getInterface().fillRect(context.getRect(),highlightColor,highlightColor,highlightColor,highlightColor);
					context.getInterface().fillRect(rect,titleColor,titleColor,titleColor,titleColor);
				}
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
				context.getInterface().drawString(new Point(context.getPos().x+padding,context.getPos().y+padding),height,title+separator,titleColor);
				// Draw the box
				context.getInterface().window(rect);
				if (select>=0) {
					int x3=rect.x+padding/2-offset+context.getInterface().getFontWidth(height,content.substring(0,select));
					context.getInterface().fillRect(new Rectangle(Math.min(x1,x3),rect.y+padding,Math.abs(x3-x1),height),highlightColor,highlightColor,highlightColor,highlightColor);
					context.getInterface().drawString(new Point(rect.x+padding/2-offset,rect.y+padding),height,content.substring(0,Math.min(position,select)),textColor);
					context.getInterface().drawString(new Point(Math.min(x1,x3),rect.y+padding),height,content.substring(Math.min(position,select),Math.max(position,select)),getMainColor(effFocus,false));
					context.getInterface().drawString(new Point(Math.max(x1,x3),rect.y+padding),height,content.substring(Math.max(position,select)),textColor);
				} else context.getInterface().drawString(new Point(rect.x+padding/2-offset,rect.y+padding),height,content,textColor);
				if ((System.currentTimeMillis()/500)%2==0 && focus) {
					if (insertMode) context.getInterface().fillRect(new Rectangle(x1,rect.y+padding+height,x2-x1,1),textColor,textColor,textColor,textColor);
					else context.getInterface().fillRect(new Rectangle(x1,rect.y+padding,1,height),textColor,textColor,textColor,textColor);
				}
				ITheme.drawRect(context.getInterface(),rect,textColor);
				context.getInterface().restore();
				return boxPosition;
			}

			@Override
			public int getDefaultHeight() {
				int height=getBaseHeight();
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
			public void renderButton(Context context, String title, boolean focus, Boolean state) {
				boolean effFocus=container?context.hasFocus():focus;
				Color colorA=getMainColor(effFocus,true);
				if (container && effFocus) context.getInterface().fillRect(context.getRect(),colorA,colorA,colorA,colorA);
				context.getInterface().drawString(new Point(context.getPos().x+padding,context.getPos().y+padding),height,title+separator+(state?"On":"Off"),getFontColor(focus));
				Rectangle rect=new Rectangle(context.getPos().x+context.getSize().width-2*context.getSize().height,context.getPos().y,2*context.getSize().height,context.getSize().height);
				Color colorB=getMainColor(effFocus,state);
				context.getInterface().fillRect(rect,colorB,colorB,colorB,colorB);
				ITheme.drawRect(context.getInterface(),rect,getFontColor(effFocus));
				Rectangle field=state?getOnField(context):getOffField(context);
				drawButton(context.getInterface(),field,focus,context.isClicked(IInterface.LBUTTON)&&field.contains(context.getInterface().getMouse()),true);
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
	public ISwitchRenderer<String> getCycleSwitchRenderer (int logicalLevel, int graphicalLevel, boolean container) {
		return new ISwitchRenderer<String>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, String state) {
				boolean effFocus=container?context.hasFocus():focus;
				Color colorA=getMainColor(effFocus,true);
				if (container && effFocus) context.getInterface().fillRect(context.getRect(),colorA,colorA,colorA,colorA);
				Context subContext=new Context(context,context.getSize().width-2*context.getSize().height,new Point(0,0),true,true);
				subContext.setHeight(context.getSize().height);
				Color textColor=(container&&effFocus)?getMainColor(effFocus,false):getFontColor(effFocus);
				context.getInterface().drawString(new Point(context.getPos().x+padding,context.getPos().y+padding),height,title+separator+state,textColor);
				Rectangle rect=getOnField(context);
				subContext=new Context(context,rect.width,new Point(rect.x-context.getPos().x,0),true,true);
				subContext.setHeight(rect.height);
				getSmallButtonRenderer(ITheme.RIGHT,logicalLevel,graphicalLevel,container).renderButton(subContext,null,effFocus,null);
				rect=getOffField(context);
				subContext=new Context(context,rect.width,new Point(rect.x-context.getPos().x,0),true,true);
				subContext.setHeight(rect.height);
				getSmallButtonRenderer(ITheme.LEFT,logicalLevel,graphicalLevel,false).renderButton(subContext,null,effFocus,null);
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
