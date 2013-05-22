package engine.image;

import java.io.FileInputStream;
/**
 * This represents a sprite.  a sprite is 1 image containing multiple image parts
 * 
 * for example
 * 
 * +---------+---------+ U
 * |         |         |
 * |    0,0  |    1,0  |
 * +---------+---------+
 * |   image |         |
 * |   0,1   |   1,1   |
 * +---------+---------+ 
 * V
 * using sprites instead of many textures saves memory and improves 
 * preformace
 * 
 * @author matthew
 *
 */
public class SpriteTexture {

	private int sx, sy, px, py, wth, hht;
	private Texture t;
	private float uwidth, vheight;
	
	public SpriteTexture(Texture tex, int psize){
		this(tex, psize, psize);
	}
	
	public SpriteTexture(Texture tex, int psizex, int psizey){
		t = tex;
		wth = tex.getWidth();
		hht = tex.getHeight();
		float numxtiles = wth / (float)psizex;
		float numytiles = hht / (float)psizey;
		sx = wth / psizex;
		px = psizex;
		py = psizey;
		sy = hht / py;
		uwidth = 1f / numytiles;
		vheight = 1f / numytiles;

	}

	public float getUwidth() {
		return uwidth;
	}

	public float getVheight() {
		return vheight;
	}

	public int getSx() {
		return sx;
	}

	public int getSy() {
		return sy;
	}

	public int getPx() {
		return px;
	}

	public int getWth() {
		return wth;
	}

	public int getHht() {
		return hht;
	}

	public Texture getT() {
		return t;
	}
	
	public int getPy(){
		return py;
	}

}
