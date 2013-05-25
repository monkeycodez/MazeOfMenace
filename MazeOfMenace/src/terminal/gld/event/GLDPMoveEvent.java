/*******************************************************************************
 * Copyright (c) 2013 Matthew Gruda.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     matthew - initial API and implementation
 ******************************************************************************/
package terminal.gld.event;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import run.Init;
import terminal.gld.GLDungeonDraw;
import entity.Entity;
import entity.player.Player;

public  class GLDPMoveEvent implements GLDEvent{
	
	private int time = 10;
	float x = 0, y = 0, xinc = 0, yinc = 0;
	
	public GLDPMoveEvent(int ox, int oy, int nx, int ny){
		float xdiff = ox - nx;
		float ydiff = oy - ny;
		x = ox;
		y = oy;
		xinc = xdiff / time;
		yinc = ydiff / time;
	}

	@Override
	public void applyPLocStart() {
		glPushMatrix();
		GLDungeonDraw.applyrot(Init.getDungeon().getPlayer().getFacing());
		glScalef(6, 6, 6);
		x += xinc;
		y += yinc;
		glTranslatef(-x, -.1f, -y);
		time--;
	}

	@Override
	public void applyPLocEnd() {
		glPopMatrix();
		
	}

	@Override
	public void applyEntChStart(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void applyEnrChEnd(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawFinalChange() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean done() {
		return time == 0;
	}

}
