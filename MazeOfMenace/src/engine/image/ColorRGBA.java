package engine.image;

import org.lwjgl.opengl.GL11;

public class ColorRGBA {
	
	public static final ColorRGBA white = new ColorRGBA(1f, 1f, 1f, 1f);

	public final float r, g, b, a;
	
	public ColorRGBA(float r, float g, float b){
		this(r, g, b, 1f);
	}
	
	public ColorRGBA(float r, float g, float b, float a){
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public void bindColor(){
		GL11.glColor4f(r, g, b, a);
	}
	
}
