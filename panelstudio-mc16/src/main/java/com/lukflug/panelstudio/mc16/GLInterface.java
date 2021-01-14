package com.lukflug.panelstudio.mc16;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutionException;

import org.lwjgl.opengl.GL11;

import com.lukflug.panelstudio.Interface;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

/**
 * Implementation of Interface for OpenGL in minecraft.
 * @author lukflug
 */
public abstract class GLInterface implements Interface {
	/**
	 * Buffer to store current modelview matrix.
	 */
	private static final float[] MODELVIEW=new float[16];
	/**
	 * Buffer to store current projection matrix.
	 */
	private static final float[] PROJECTION=new float[16];
	/**
	 * Buffer to store current viewport.
	 */
	private static final int[] VIEWPORT=new int[16];
	/**
	 * Buffer used to calculate coordinates using gluProject.
	 */
	private static final float[] COORDS=new float[4];
	/**
	 * Clipping rectangle stack.
	 */
	private Stack<Rectangle> clipRect=new Stack<Rectangle>();
	/**
	 * Boolean indicating whether to clip in the horizontal direction 
	 */
	protected boolean clipX;
	protected List<Identifier> textures=new ArrayList<Identifier>();
	
	/**
	 * Constructor.
	 * @param clipX whether to clip in the horizontal direction
	 */
	public GLInterface (boolean clipX) {
		this.clipX=clipX;
	}

	@Override
	public void fillTriangle(Point pos1, Point pos2, Point pos3, Color c1, Color c2, Color c3) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(GL11.GL_TRIANGLES,VertexFormats.POSITION_COLOR);
			bufferbuilder.vertex(pos1.x,pos1.y,getZLevel()).color(c1.getRed()/255.0f,c1.getGreen()/255.0f,c1.getBlue()/255.0f,c1.getAlpha()/255.0f).next();
			bufferbuilder.vertex(pos2.x,pos2.y,getZLevel()).color(c2.getRed()/255.0f,c2.getGreen()/255.0f,c2.getBlue()/255.0f,c2.getAlpha()/255.0f).next();
			bufferbuilder.vertex(pos3.x,pos3.y,getZLevel()).color(c3.getRed()/255.0f,c3.getGreen()/255.0f,c3.getBlue()/255.0f,c3.getAlpha()/255.0f).next();
		tessellator.draw();
	}

	@Override
	public void drawLine(Point a, Point b, Color c1, Color c2) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(GL11.GL_LINES,VertexFormats.POSITION_COLOR);
			bufferbuilder.vertex(a.x,a.y,getZLevel()).color(c1.getRed()/255.0f,c1.getGreen()/255.0f,c1.getBlue()/255.0f,c1.getAlpha()/255.0f).next();
			bufferbuilder.vertex(b.x,b.y,getZLevel()).color(c2.getRed()/255.0f,c2.getGreen()/255.0f,c2.getBlue()/255.0f,c2.getAlpha()/255.0f).next();
		tessellator.draw();
	}

	@Override
	public void fillRect(Rectangle r, Color c1, Color c2, Color c3, Color c4) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR);
			bufferbuilder.vertex(r.x,r.y+r.height,getZLevel()).color(c4.getRed()/255.0f,c4.getGreen()/255.0f,c4.getBlue()/255.0f,c4.getAlpha()/255.0f).next();
			bufferbuilder.vertex(r.x+r.width,r.y+r.height,getZLevel()).color(c3.getRed()/255.0f,c3.getGreen()/255.0f,c3.getBlue()/255.0f,c3.getAlpha()/255.0f).next();
			bufferbuilder.vertex(r.x+r.width,r.y,getZLevel()).color(c2.getRed()/255.0f,c2.getGreen()/255.0f,c2.getBlue()/255.0f,c2.getAlpha()/255.0f).next();
			bufferbuilder.vertex(r.x,r.y,getZLevel()).color(c1.getRed()/255.0f,c1.getGreen()/255.0f,c1.getBlue()/255.0f,c1.getAlpha()/255.0f).next();
		tessellator.draw();
	}

	@Override
	public void drawRect(Rectangle r, Color c1, Color c2, Color c3, Color c4) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(GL11.GL_LINE_LOOP, VertexFormats.POSITION_COLOR);
			bufferbuilder.vertex(r.x,r.y+r.height,getZLevel()).color(c4.getRed()/255.0f,c4.getGreen()/255.0f,c4.getBlue()/255.0f,c4.getAlpha()/255.0f).next();
			bufferbuilder.vertex(r.x+r.width,r.y+r.height,getZLevel()).color(c3.getRed()/255.0f,c3.getGreen()/255.0f,c3.getBlue()/255.0f,c3.getAlpha()/255.0f).next();
			bufferbuilder.vertex(r.x+r.width,r.y,getZLevel()).color(c2.getRed()/255.0f,c2.getGreen()/255.0f,c2.getBlue()/255.0f,c2.getAlpha()/255.0f).next();
			bufferbuilder.vertex(r.x,r.y,getZLevel()).color(c1.getRed()/255.0f,c1.getGreen()/255.0f,c1.getBlue()/255.0f,c1.getAlpha()/255.0f).next();
		tessellator.draw();
	}
	
	@Override
	public synchronized int loadImage(String name) {
		try {
			Identifier rl=new Identifier(getResourcePrefix()+name);
			if (!textures.contains(rl)) {
				MinecraftClient.getInstance().getTextureManager().loadTextureAsync(rl,null).get();
				textures.add(rl);
			}
			return textures.indexOf(rl);
		} catch (ExecutionException e) {
			e.printStackTrace();
			return 0;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public void drawImage(Rectangle r, int rotation, boolean parity, int image) {
		if (image==0) return;
		int texCoords[][]={{0,1},{1,1},{1,0},{0,0}};
		for (int i=0;i<rotation%4;i++) {
			int temp1=texCoords[3][0],temp2=texCoords[3][1];
			texCoords[3][0]=texCoords[2][0];
			texCoords[3][1]=texCoords[2][1];
			texCoords[2][0]=texCoords[1][0];
			texCoords[2][1]=texCoords[1][1];
			texCoords[1][0]=texCoords[0][0];
			texCoords[1][1]=texCoords[0][1];
			texCoords[0][0]=temp1;
			texCoords[0][1]=temp2;
		}
		if (parity) {
			int temp1=texCoords[3][0],temp2=texCoords[3][1];
			texCoords[3][0]=texCoords[0][0];
			texCoords[3][1]=texCoords[0][1];
			texCoords[0][0]=temp1;
			texCoords[0][1]=temp2;
			temp1=texCoords[2][0];
			temp2=texCoords[2][1];
			texCoords[2][0]=texCoords[1][0];
			texCoords[2][1]=texCoords[1][1];
			texCoords[1][0]=temp1;
			texCoords[1][1]=temp2;
		}
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		MinecraftClient.getInstance().getTextureManager().bindTexture(textures.get(image));
		GlStateManager.enableTexture();
		bufferbuilder.begin(GL11.GL_QUADS, VertexFormats.POSITION_TEXTURE);
			bufferbuilder.vertex(r.x,r.y+r.height,getZLevel()).texture(texCoords[0][0],texCoords[0][1]).next();
			bufferbuilder.vertex(r.x+r.width,r.y+r.height,getZLevel()).texture(texCoords[1][0],texCoords[1][1]).next();
			bufferbuilder.vertex(r.x+r.width,r.y,getZLevel()).texture(texCoords[2][0],texCoords[2][1]).next();
			bufferbuilder.vertex(r.x,r.y,getZLevel()).texture(texCoords[3][0],texCoords[3][1]).next();
		tessellator.draw();
		GlStateManager.disableTexture();
	}
	
	/**
	 * Transform {@link #COORDS} with matrix.
	 * @param matrix matrix to multiply with {@link #COORDS} 
	 */
	private void transform (float[] matrix) {
		float[] coords={0,0,0,0};
		for (int i=0;i<4;i++) {
			for (int j=0;j<4;j++) {
				coords[i]+=matrix[i+4*j]*COORDS[j];
			}
		}
		for (int i=0;i<4;i++) COORDS[i]=coords[i];
	}
	
	/**
	 * Project vector and store in {@link #COORDS}
	 * @param x first component
	 * @param y second component
	 * @param z fourth component
	 * @param w fifth component
	 */
	private void project (float x, float y, float z, float w) {
		COORDS[0]=x;
		COORDS[1]=y;
		COORDS[2]=z;
		COORDS[3]=w;
		transform(MODELVIEW);
		transform(PROJECTION);
		for (int i=0;i<4;i++) COORDS[i]=(COORDS[i]/COORDS[3]+1)/2;
		COORDS[0]=COORDS[0]*VIEWPORT[2]+VIEWPORT[0];
		COORDS[1]=COORDS[1]*VIEWPORT[3]+VIEWPORT[1];
	}
	
	/**
	 * Utility function to set clipping rectangle by projecting the coordinates using gluProject.
	 * @param r the clipping rectangle
	 */
	protected void scissor (Rectangle r) {
		if (r==null) {
			GL11.glScissor(0,0,0,0);
			GL11.glEnable(GL11.GL_SCISSOR_TEST);
			return;
		}
		float x1,y1,x2,y2;
		project(r.x,r.y,getZLevel(),1);
		x1=COORDS[0];
		y1=COORDS[1];
		project(r.x+r.width,r.y+r.height,getZLevel(),1);
		x2=COORDS[0];
		y2=COORDS[1];
		if (!clipX) {
			x1=VIEWPORT[0];
			x2=x1+VIEWPORT[2];
		}
		GL11.glScissor(Math.round(Math.min(x1,x2)),Math.round(Math.min(y1,y2)),Math.round(Math.abs(x2-x1)),Math.round(Math.abs(y2-y1)));
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
	}

	@Override
	public void window (Rectangle r) {
		if (clipRect.isEmpty()) {
			scissor(r);
			clipRect.push(r);
		} else {
			Rectangle top=clipRect.peek();
			if (top==null) {
				scissor(null);
				clipRect.push(null);
			} else {
				int x1,y1,x2,y2;
				x1=Math.max(r.x,top.x);
				y1=Math.max(r.y,top.y);
				x2=Math.min(r.x+r.width,top.x+top.width);
				y2=Math.min(r.y+r.height,top.y+top.height);
				if (x2>x1 && y2>y1) {
					Rectangle rect=new Rectangle(x1,y1,x2-x1,y2-y1);
					scissor(rect);
					clipRect.push(rect);
				} else {
					scissor(null);
					clipRect.push(null);
				}
			}
		}
	}

	@Override
	public void restore() {
		if (!clipRect.isEmpty()) {
			clipRect.pop();
			if (clipRect.isEmpty()) GL11.glDisable(GL11.GL_SCISSOR_TEST);
			else scissor(clipRect.peek());
		}
	}
	
	/**
	 * Update the matrix buffers.
	 */
	public void getMatrices() {
		GL11.glGetFloatv(GL11.GL_MODELVIEW_MATRIX,MODELVIEW);
		GL11.glGetFloatv(GL11.GL_PROJECTION_MATRIX,PROJECTION);
		GL11.glGetIntegerv(GL11.GL_VIEWPORT,VIEWPORT);
	}
	
	/**
	 * Set OpenGL to the state used by the rendering methods.
	 * Should be called before rendering.
	 */
	public static void begin() {
		GlStateManager.enableBlend();
		GlStateManager.disableTexture();
		GlStateManager.blendFuncSeparate(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA,GL11.GL_ONE,GL11.GL_ZERO);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GlStateManager.lineWidth(2);
	}
	
	/**
	 * Restore OpenGL to the state expected by Minecraft.
	 * Should be called after rendering.
	 */
	public static void end() {
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.enableTexture();
		GlStateManager.disableBlend();
	}
	
	/**
	 * Get the z-coordinate to render everything.
	 * @return the z-level
	 */
	protected abstract float getZLevel();
	/**
	 * Get the Minecraft resource location string.
	 * @return the resource prefix
	 */
	protected abstract String getResourcePrefix();
}
