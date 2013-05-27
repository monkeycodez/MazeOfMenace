package terminal.gld.event;

import static org.lwjgl.opengl.GL11.*;

import run.Settings;
import entity.Entity;


public class GLDEMoveEvent extends AbstractEGLDEvent{
	
	private float x = 0, y = 0, xinc = 0, yinc = 0;
	private final Entity e;
	
	public GLDEMoveEvent(Entity e, int ox, int oy, int nx, int ny){
		float xdiff = ox - nx;
		float ydiff = oy - ny;
		x = ox;
		y = oy;
		xinc = xdiff / super.time;
		yinc = ydiff / super.time;
		this.e = e;
//		e.setLoc((int)x, (int)y);
	}

	@Override
	public boolean applyEntChStart(Entity e) {
		if(e == this.e){
			System.out.println("moving"+time);
			glPushMatrix();
			x+=xinc;
			y+=yinc;
			glTranslatef(x, 0, y);
			glScalef(.5f, .5f, .5f);
			if(done()){
				System.out.println("done");
				e.notifyXY();
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean applyEnrChEnd(Entity e) {
		if(this.e == e){
			glPopMatrix();
			return true;
		}
		return false;
	}

	public void drawFinalChange() {}


}
