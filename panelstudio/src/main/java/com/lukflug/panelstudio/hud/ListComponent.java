package com.lukflug.panelstudio.hud;

import java.awt.Point;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.Interface;
import com.lukflug.panelstudio.PanelConfig;
import com.lukflug.panelstudio.theme.Renderer;

/**
 * HUD component that consists of a list of strings.
 * @author lukflug
 */
public class ListComponent extends HUDComponent {
	/**
	 * The list to be rendered.
	 */
	protected HUDList list;
	/**
	 * Flag saving the state of whether to sort up.
	 */
	protected boolean lastUp=false;
	/**
	 * Flag saving the state of whether to sort right.
	 */
	protected boolean lastRight=false;
	
	/**
	 * Constructor.
	 * @param name the title of the component
	 * @param renderer the renderer for the component
	 * @param position the initial position
	 * @param list the list to be rendered
	 */
	public ListComponent (String name, Renderer renderer, Point position, HUDList list) {
		super(name,renderer,position);
		this.list=list;
	}

	@Override
	public void render (Context context) {
		super.render(context);
		for (int i=0;i<list.getSize();i++) {
			String s=list.getItem(i);
			Point p=context.getPos();
			if (list.sortUp()) {
				p.translate(0,context.getSize().height-(i+1)*context.getInterface().getFontHeight());
			} else {
				p.translate(0,i*context.getInterface().getFontHeight());
			}
			if (list.sortRight()) {
				p.translate(getWidth(context.getInterface())-context.getInterface().getFontWidth(s),0);
			}
			context.getInterface().drawString(p,s,list.getItemColor(i));
		}
	}
	
	@Override
	public Point getPosition (Interface inter) {
		int width=getWidth(inter);
		int height=renderer.getHeight(false)+(list.getSize()-1)*inter.getFontHeight();
		if (lastUp!=list.sortUp()) {
			if (list.sortUp()) position.translate(0,height);
			else position.translate(0,-height);
			lastUp=list.sortUp();
		}
		if (lastRight!=list.sortRight()) {
			if (list.sortRight()) position.translate(width,0);
			else position.translate(-width,0);
			lastRight=list.sortRight();
		}
		if (list.sortUp()) {
			if (list.sortRight()) return new Point(position.x-width,position.y-height);
			else return new Point(position.x,position.y-height);
		} else {
			if (list.sortRight()) return new Point(new Point(position.x-width,position.y));
			else return new Point(position);
		}
	}
	
	@Override
	public void setPosition (Interface inter, Point position) {
		int width=getWidth(inter);
		int height=renderer.getHeight(false)+(list.getSize()-1)*inter.getFontHeight();
		if (list.sortUp()) {
			if (list.sortRight()) this.position=new Point(position.x+width,position.y+height);
			else this.position=new Point(position.x,position.y+height);
		} else {
			if (list.sortRight()) this.position=new Point(position.x+width,position.y);
			else this.position=new Point(position);
		}
	}

	@Override
	public int getWidth (Interface inter) {
		int width=inter.getFontWidth(getTitle());
		for (int i=0;i<list.getSize();i++) {
			String s=list.getItem(i);
			width=Math.max(width,inter.getFontWidth(s));
		}
		return width;
	}

	@Override
	public void getHeight (Context context) {
		context.setHeight(renderer.getHeight(false)+(list.getSize()-1)*context.getInterface().getFontHeight());
	}
	
	@Override
	public void loadConfig (Interface inter, PanelConfig config) {
		super.loadConfig(inter,config);
		this.lastUp=list.sortUp();
		this.lastRight=list.sortRight();
	}
}
