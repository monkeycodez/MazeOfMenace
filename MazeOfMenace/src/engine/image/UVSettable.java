package engine.image;

public interface UVSettable {

	public void setUV(float ustart, float uend, float vstart, float vend, int x, int y);
	
	public SpriteTexture getSprite();

}
