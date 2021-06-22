package com.lukflug.panelstudio.theme;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.function.IntSupplier;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.setting.ILabeled;

public class RainbowTheme extends ThemeBase {
	protected IBoolean ignoreDisabled,buttonRainbow;
	protected IntSupplier rainbowGradient;
	protected int height,padding;
	protected String separator;
	
	public RainbowTheme (IColorScheme scheme, IBoolean ignoreDisabled, IBoolean buttonRainbow, IntSupplier rainbowGradient, int height, int padding, String separator) {
		super(scheme);
		this.ignoreDisabled=ignoreDisabled;
		this.buttonRainbow=buttonRainbow;
		this.rainbowGradient=rainbowGradient;
		this.height=height;
		this.padding=padding;
		this.separator=separator;
		scheme.createSetting(this,"Title Color","The color for panel titles.",false,true,new Color(64,64,64),false);
		scheme.createSetting(this,"Rainbow Color","The rainbow base color.",false,true,new Color(255,0,0),false);
		scheme.createSetting(this,"Background Color","The main color for disabled components.",false,true,new Color(64,64,64),false);
		scheme.createSetting(this,"Font Color","The main color for text.",false,true,new Color(255,255,255),false);
		scheme.createSetting(this,"Highlight Color","The color for highlighted text.",false,true,new Color(0,0,255),false);
	}
	
	protected void renderOverlay (Context context) {
		Color color=context.isHovered()?new Color(0,0,0,64):new Color(0,0,0,0);
		context.getInterface().fillRect(context.getRect(),color,color,color,color);
	}
	
	protected void renderRainbowRect (Rectangle rect, Context context, boolean focus) {
		Color source=getMainColor(focus,true);
		float[] hsb=Color.RGBtoHSB(source.getRed(),source.getGreen(),source.getBlue(),null);
		float currentHue=hsb[0];
		float targetHue=hsb[0];
		if (rainbowGradient.getAsInt()!=0) targetHue+=rect.height/(float)rainbowGradient.getAsInt();
		else context.getInterface().fillRect(rect,source,source,source,source);
		while (currentHue<targetHue) {
			float nextHue=(float)(Math.floor(currentHue*6)+1)/6;
			if (nextHue>targetHue) nextHue=targetHue;
			Color colorA=Color.getHSBColor(currentHue,hsb[1],hsb[2]);
			Color colorB=Color.getHSBColor(nextHue,hsb[1],hsb[2]);
			int top=(int)Math.round((currentHue-hsb[0])*rainbowGradient.getAsInt());
			int bottom=(int)Math.round((nextHue-hsb[0])*rainbowGradient.getAsInt());
			context.getInterface().fillRect(new Rectangle(rect.x,rect.y+top,rect.width,bottom-top),colorA,colorA,colorB,colorB);
			currentHue=nextHue;
		}
	}

	@Override
	public IDescriptionRenderer getDescriptionRenderer() {
		return new IDescriptionRenderer() {
			@Override
			public void renderDescription(IInterface inter, Point pos, String text) {
				Rectangle rect=new Rectangle(pos,new Dimension(inter.getFontWidth(height,text)+2,height+2));
				Color color=getBackgroundColor(true);
				inter.fillRect(rect,color,color,color,color);
				inter.drawString(new Point(pos.x+1,pos.y+1),height,text,getFontColor(true));
			}
		};
	}

	@Override
	public IContainerRenderer getContainerRenderer(int logicalLevel, int graphicalLevel, boolean horizontal) {
		return new IContainerRenderer() {
			@Override
			public void renderBackground (Context context, boolean focus) {
				if (graphicalLevel==0 && !buttonRainbow.isOn()) renderRainbowRect(context.getRect(),context,focus);
			}
		};
	}

	@Override
	public <T> IPanelRenderer<T> getPanelRenderer(Class<T> type, int logicalLevel, int graphicalLevel) {
		return new IPanelRenderer<T>() {
			@Override
			public int getBorder() {
				return graphicalLevel==0?1:0;
			}
			
			@Override
			public void renderPanelOverlay(Context context, boolean focus, T state, boolean open) {
			}

			@Override
			public void renderTitleOverlay(Context context, boolean focus, T state, boolean open) {
				if (graphicalLevel==0) {
					Color color=getFontColor(focus);
					context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y+context.getSize().height,context.getSize().width,1),color,color,color,color);
				}
			}
		};
	}

	@Override
	public <T> IScrollBarRenderer<T> getScrollBarRenderer(Class<T> type, int logicalLevel, int graphicalLevel) {
		return new IScrollBarRenderer<T>() {
			@Override
			public int renderScrollBar(Context context, boolean focus, T state, boolean horizontal, int height, int position) {
				Color color=getBackgroundColor(focus);
				if (graphicalLevel==0 || buttonRainbow.isOn()) {
					renderRainbowRect(context.getRect(),context,focus);
				}
				if (horizontal) {
					int a=(int)(position/(double)height*context.getSize().width);
					int b=(int)((position+context.getSize().width)/(double)height*context.getSize().width);
					context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y,a,context.getSize().height),color,color,color,color);
					context.getInterface().fillRect(new Rectangle(context.getPos().x+b,context.getPos().y,context.getSize().width-b,context.getSize().height),color,color,color,color);
				} else {
					int a=(int)(position/(double)height*context.getSize().height);
					int b=(int)((position+context.getSize().height)/(double)height*context.getSize().height);
					context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y,context.getSize().width,a),color,color,color,color);
					context.getInterface().fillRect(new Rectangle(context.getPos().x,context.getPos().y+b,context.getSize().width,context.getSize().height-b),color,color,color,color);
				}
				if (horizontal) return (int)((context.getInterface().getMouse().x-context.getPos().x)*height/(double)context.getSize().width-context.getSize().width/2.0);
				else return (int)((context.getInterface().getMouse().y-context.getPos().y)*height/(double)context.getSize().height-context.getSize().height/2.0);
			}

			@Override
			public int getThickness() {
				return 4;
			}
		};
	}

	@Override
	public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer(Class<T> type, int logicalLevel, int graphicalLevel, boolean container) {
		return new IEmptySpaceRenderer<T>() {
			@Override
			public void renderSpace(Context context, boolean focus, T state) {
				Color color=getBackgroundColor(focus);
				context.getInterface().fillRect(context.getRect(),color,color,color,color);
			}
		};
	}

	@Override
	public <T> IButtonRenderer<T> getButtonRenderer(Class<T> type, int logicalLevel, int graphicalLevel, boolean container) {
		return new IButtonRenderer<T>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, Object state) {
				boolean effFocus=container?context.hasFocus():focus;
				boolean active=container&&graphicalLevel!=0;
				if (type==Boolean.class) {
					active=(Boolean)state || (ignoreDisabled.isOn()&&container);
				}
				if (!active) {
					Color color=getBackgroundColor(effFocus);
					context.getInterface().fillRect(context.getRect(),color,color,color,color);
				} else if (graphicalLevel==0 || buttonRainbow.isOn()) {
					renderRainbowRect(context.getRect(),context,effFocus);
				}
				renderOverlay(context);
				String text=(logicalLevel>=2?"> ":"")+title+(type==String.class?separator+state:"");
				context.getInterface().drawString(new Point(context.getPos().x+padding,context.getPos().y+padding),height,text,getFontColor(effFocus));
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
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
		return getButtonRenderer(String.class,logicalLevel,graphicalLevel,container);
	}

	@Override
	public ISliderRenderer getSliderRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		return new ISliderRenderer() {
			@Override
			public void renderSlider(Context context, String title, String state, boolean focus, double value) {
				boolean effFocus=container?context.hasFocus():focus;
				if (graphicalLevel==0 || buttonRainbow.isOn()) {
					renderRainbowRect(context.getRect(),context,effFocus);
				}
				int divider=(int)(context.getSize().width*value);
				Color color=getBackgroundColor(effFocus);
				context.getInterface().fillRect(new Rectangle(context.getPos().x+divider,context.getPos().y,context.getSize().width-divider,context.getSize().height),color,color,color,color);
				renderOverlay(context);
				String text=(logicalLevel>=2?"> ":"")+title+separator+state;
				context.getInterface().drawString(new Point(context.getPos().x+padding,context.getPos().y+padding),height,text,getFontColor(effFocus));
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
				if (graphicalLevel==0 || buttonRainbow.isOn()) {
					renderRainbowRect(context.getRect(),context,focus);
				}
				for (int i=0;i<items.length;i++) {
					Rectangle rect=getItemRect(context,items,i,horizontal);
					Context subContext=new Context(context.getInterface(),rect.width,rect.getLocation(),context.hasFocus(),context.onTop());
					subContext.setHeight(rect.height);
					if (i!=target) {
						Color color=getBackgroundColor(focus);
						context.getInterface().fillRect(subContext.getRect(),color,color,color,color);
					}
					renderOverlay(subContext);
					context.getInterface().drawString(new Point(rect.x+padding,rect.y+padding),height,items[i].getDisplayName(),getFontColor(focus));
				}
			}

			@Override
			public int getDefaultHeight(ILabeled[] items, boolean horizontal) {
				return (horizontal?1:items.length)*getBaseHeight();
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
			public int renderTextField (Context context, String title, boolean focus, String content, int position, int select, int boxPosition, boolean insertMode) {
				boolean effFocus=container?context.hasFocus():focus;
				if (graphicalLevel==0 || buttonRainbow.isOn()) {
					renderRainbowRect(context.getRect(),context,effFocus);
				}
				// Declare and assign variables
				Color textColor=getFontColor(effFocus);
				Color highlightColor=scheme.getColor("Highlight Color");
				Rectangle rect=getTextArea(context,title);
				int strlen=context.getInterface().getFontWidth(height,content.substring(0,position));
				context.getInterface().fillRect(rect,new Color(0,0,0,64),new Color(0,0,0,64),new Color(0,0,0,64),new Color(0,0,0,64));
				ITheme.drawRect(context.getInterface(),rect,new Color(0,0,0,64));
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
				renderOverlay(context);
				context.getInterface().drawString(new Point(context.getPos().x+padding,context.getPos().y+padding/2),height,title+separator,textColor);
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
			public Rectangle getTextArea (Context context, String title) {
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
				return RainbowTheme.this.getBaseHeight();
			}
		};
	}

	@Override
	public int getBaseHeight() {
		return height+2*padding;
	}

	@Override
	public Color getMainColor(boolean focus, boolean active) {
		if (active) return scheme.getColor("Rainbow Color");
		else return scheme.getColor("Background Color");
	}

	@Override
	public Color getBackgroundColor(boolean focus) {
		return scheme.getColor("Background Color");
	}

	@Override
	public Color getFontColor(boolean focus) {
		return scheme.getColor("Font Color");
	}
}
