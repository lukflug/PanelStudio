package com.lukflug.panelstudio.theme;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.setting.ILabeled;

/**
 * Theme corresponding to the appearance GameSense 2.0 and 2.1 had.
 * The parameter gradient in the constructor determines, if it appears like GameSense 2.0-2.1.1 (false) or like GameSense 2.1.2-2.1.5 (true).
 * @author lukflug
 */
public class ClearTheme extends ThemeBase {
	protected IBoolean gradient;
	protected int height,padding,border;
	protected String separator;
	
	public ClearTheme (IColorScheme scheme, IBoolean gradient, int height, int padding, int border, String separator) {
		super(scheme);
		this.gradient=gradient;
		this.height=height;
		this.padding=padding;
		this.border=border;
		this.separator=separator;
		scheme.createSetting(this,"Title Color","The color for panel titles.",false,true,new Color(90,145,240),false);
		scheme.createSetting(this,"Enabled Color","The main color for enabled components.",false,true,new Color(90,145,240),false);
		scheme.createSetting(this,"Background Color","The background color.",true,true,new Color(195,195,195,150),false);
		scheme.createSetting(this,"Font Color","The main color for text.",false,true,new Color(255,255,255),false);
		scheme.createSetting(this,"Scroll Bar Color","The color for the scroll bar.",false,true,new Color(90,145,240),false);
	}
	
	protected void renderOverlay (Context context) {
		Color color=context.isHovered()?new Color(0,0,0,64):new Color(0,0,0,0);
		context.getInterface().fillRect(context.getRect(),color,color,color,color);
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
				if (graphicalLevel==0) {
					Color color=getBackgroundColor(focus);
					context.getInterface().fillRect(context.getRect(),color,color,color,color);
				}
			}
			
			@Override
			public int getBorder() {
				return horizontal?0:border;
			}
			
			@Override
			public int getTop() {
				return horizontal?0:border;
			}
		};
	}

	@Override
	public <T> IPanelRenderer<T> getPanelRenderer(Class<T> type, int logicalLevel, int graphicalLevel) {
		return new IPanelRenderer<T>() {
			@Override
			public void renderPanelOverlay(Context context, boolean focus, T state, boolean open) {
			}

			@Override
			public void renderTitleOverlay(Context context, boolean focus, T state, boolean open) {
				if (graphicalLevel>0) {
					Color color=getFontColor(focus);
					Rectangle rect=context.getRect();
					rect=new Rectangle(rect.x+rect.width-rect.height+2,rect.y+2,rect.height-4,rect.height-4);
					if (rect.width%2!=0) {
						rect.width--;
						rect.height--;
						rect.x++;
					} 
					Point p1,p2,p3;
					if (open) {
						p3=new Point(rect.x+rect.width,rect.y);
						p2=new Point(rect.x+rect.width/2,rect.y+rect.height);
						p1=new Point(rect.x,rect.y);
					} else  {
						p3=new Point(rect.x,rect.y);
						p2=new Point(rect.x+rect.width,rect.y+rect.height/2);
						p1=new Point(rect.x,rect.y+rect.height);
					}
					context.getInterface().fillTriangle(p1,p2,p3,color,color,color);
				}
			}
		};
	}

	@Override
	public <T> IScrollBarRenderer<T> getScrollBarRenderer(Class<T> type, int logicalLevel, int graphicalLevel) {
		return new IScrollBarRenderer<T>() {
			@Override
			public int renderScrollBar (Context context, boolean focus, T state, boolean horizontal, int height, int position) {
				if (graphicalLevel==0) {
					Color color=getBackgroundColor(focus);
					context.getInterface().fillRect(context.getRect(),color,color,color,color);
				}
				Color color=ITheme.combineColors(scheme.getColor("Scroll Bar Color"),getBackgroundColor(focus));
				if (horizontal) {
					int a=(int)(position/(double)height*context.getSize().width);
					int b=(int)((position+context.getSize().width)/(double)height*context.getSize().width);
					context.getInterface().fillRect(new Rectangle(context.getPos().x+a+1,context.getPos().y+1,b-a-2,2),color,color,color,color);
					context.getInterface().drawRect(new Rectangle(context.getPos().x+a+1,context.getPos().y+1,b-a-2,2),color,color,color,color);
				} else {
					int a=(int)(position/(double)height*context.getSize().height);
					int b=(int)((position+context.getSize().height)/(double)height*context.getSize().height);
					context.getInterface().fillRect(new Rectangle(context.getPos().x+1,context.getPos().y+a+1,2,b-a-2),color,color,color,color);
					context.getInterface().drawRect(new Rectangle(context.getPos().x+1,context.getPos().y+a+1,2,b-a-2),color,color,color,color);
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
				if (graphicalLevel==0) {
					Color color=getBackgroundColor(focus);
					context.getInterface().fillRect(context.getRect(),color,color,color,color);
				}
			}
		};
	}

	@Override
	public <T> IButtonRenderer<T> getButtonRenderer(Class<T> type, int logicalLevel, int graphicalLevel, boolean container) {
		return new IButtonRenderer<T>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, T state) {
				if (container && graphicalLevel<=0) {
					Color colorA=getColor(scheme.getColor("Title Color")),colorB=gradient.isOn()?getBackgroundColor(focus):colorA;
					context.getInterface().fillRect(context.getRect(),colorA,colorA,colorB,colorB);
				}
				Color color=getFontColor(focus);
				if (type==Boolean.class && (Boolean)state==true) color=getMainColor(focus,true);
				else if (type==Color.class) color=(Color)state;
				if (graphicalLevel>0) renderOverlay(context);
				if (type==String.class) context.getInterface().drawString(new Point(context.getRect().x+padding,context.getRect().y+padding),height,title+separator+state,color);
				else context.getInterface().drawString(new Point(context.getRect().x+padding,context.getRect().y+padding),height,title,color);
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}
		};
	}

	@Override
	public IButtonRenderer<Void> getSmallButtonRenderer(int symbol, int logicalLevel, int graphicalLevel, boolean container) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IButtonRenderer<String> getKeybindRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		return new IButtonRenderer<String>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, String state) {
				Color color=getFontColor(focus);
				if (focus) color=getMainColor(focus,true);
				renderOverlay(context);
				context.getInterface().drawString(new Point(context.getRect().x+padding,context.getRect().y+padding),height,title+separator+(focus?"...":state),color);
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
				Color color=getFontColor(focus);
				Color colorA=getMainColor(focus,true);
				Rectangle rect=getSlideArea(context);
				int divider=(int)(rect.width*value);
				context.getInterface().fillRect(new Rectangle(rect.x,rect.y,divider,rect.height),colorA,colorA,colorA,colorA);
				renderOverlay(context);
				context.getInterface().drawString(new Point(context.getRect().x+padding,context.getRect().y+padding),height,title+separator+state,color);
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
				for (int i=0;i<items.length;i++) {
					Rectangle rect=getItemRect(context,items,i,horizontal);
					Context subContext=new Context(context.getInterface(),rect.width,rect.getLocation(),context.hasFocus(),context.onTop());
					subContext.setHeight(rect.height);
					renderOverlay(subContext);
					context.getInterface().drawString(new Point(rect.x+padding,rect.y+padding),height,items[i].getDisplayName(),i==target?getMainColor(focus,true):getFontColor(focus));
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
	public ITextFieldRenderer getTextRenderer (int logicalLevel, int graphicalLevel, boolean container) {
		return new ITextFieldRenderer() {
			@Override
			public int renderTextField (Context context, String title, boolean focus, String content, int position, int select, int boxPosition, boolean insertMode) {
				return boxPosition;
			}

			@Override
			public int getDefaultHeight() {
				return getBaseHeight();
			}

			@Override
			public Rectangle getTextArea (Context context, String title) {
				return context.getRect();
			}
			
			@Override
			public int transformToCharPos(Context context, String content, int boxPosition) {
				// TODO Auto-generated method stub
				return -1;
			}
		};
	}
	
	public ISwitchRenderer<Boolean> getToggleSwitchRenderer (int logicalLevel, int graphicalLevel, boolean container) {
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
	
	public ISwitchRenderer<String> getCycleSwitchRenderer (int logicalLevel, int graphicalLevel, boolean container) {
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
	public int getBaseHeight() {
		return height+2*padding;
	}

	@Override
	public Color getMainColor(boolean focus, boolean active) {
		if (active) return getColor(scheme.getColor("Enabled Color"));
		else return new Color(0,0,0,0);
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
