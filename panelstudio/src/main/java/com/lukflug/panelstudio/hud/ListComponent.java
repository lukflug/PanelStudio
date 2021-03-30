package com.lukflug.panelstudio.hud;

import java.awt.Dimension;
import java.awt.Point;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.config.IPanelConfig;
import com.lukflug.panelstudio.setting.ILabeled;

public class ListComponent extends HUDComponent {
	protected HUDList list;
	protected boolean lastUp=false;
	protected boolean lastRight=false;
	protected int height,border;
	
	public ListComponent (ILabeled label, Point position, String configName, HUDList list, int height, int border) {
		super(label,position,configName);
		this.list=list;
		this.height=height;
		this.border=border;
	}
	
	@Override
	public void render (Context context) {
		super.render(context);
		for (int i=0;i<list.getSize();i++) {
			String s=list.getItem(i);
			Point p=context.getPos();
			if (list.sortUp()) {
				p.translate(0,(height+border)*(list.getSize()-1-i));
			} else {
				p.translate(0,i*(height+border));
			}
			if (list.sortRight()) {
				p.translate(getWidth(context.getInterface())-context.getInterface().getFontWidth(height,s),0);
			}
			context.getInterface().drawString(p,height,s,list.getItemColor(i));
		}
	}
	
	@Override
	public Point getPosition (IInterface inter) {
		Dimension size=getSize(inter);
		if (lastUp!=list.sortUp()) {
			if (list.sortUp()) position.translate(0,size.height);
			else position.translate(0,-size.height);
			lastUp=list.sortUp();
		}
		if (lastRight!=list.sortRight()) {
			if (list.sortRight()) position.translate(size.width,0);
			else position.translate(-size.width,0);
			lastRight=list.sortRight();
		}
		if (list.sortUp()) {
			if (list.sortRight()) return new Point(position.x-size.width,position.y-size.height);
			else return new Point(position.x,position.y-size.height);
		} else {
			if (list.sortRight()) return new Point(new Point(position.x-size.width,position.y));
			else return new Point(position);
		}
	}
	
	@Override
	public void setPosition (IInterface inter, Point position) {
		Dimension size=getSize(inter);
		if (list.sortUp()) {
			if (list.sortRight()) this.position=new Point(position.x+size.width,position.y+size.height);
			else this.position=new Point(position.x,position.y+size.height);
		} else {
			if (list.sortRight()) this.position=new Point(position.x+size.width,position.y);
			else this.position=new Point(position);
		}
	}
	
	@Override
	public void loadConfig (IInterface inter, IPanelConfig config) {
		super.loadConfig(inter,config);
		this.lastUp=list.sortUp();
		this.lastRight=list.sortRight();
	}

	@Override
	public Dimension getSize (IInterface inter) {
		int width=inter.getFontWidth(height,getTitle());
		for (int i=0;i<list.getSize();i++) {
			String s=list.getItem(i);
			width=Math.max(width,inter.getFontWidth(height,s));
		}
		int height=(this.height+border)*list.getSize()-border;
		if (height<0) height=0;
		return new Dimension(width+2*border,height);
	}
}
