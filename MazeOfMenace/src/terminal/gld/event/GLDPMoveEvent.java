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
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import run.Init;
import run.Settings;
import terminal.gld.GLDungeonDraw;
import entity.Entity;
import entity.player.Player;

public  class GLDPMoveEvent extends AbstractEGLDEvent{
	
	private int time = Settings.getGle();
	float x = 0, y = 0, xinc = 0, yinc = 0;
	private int nx, ny;
	
	public GLDPMoveEvent(int ox, int oy, int nx, int ny){
		time = Settings.getGle();
		float xdiff = ox - nx;
		float ydiff = oy - ny;
		x = ox;
		y = oy;
//		this.nx = nx;
//		this.ny = ny;
		xinc = xdiff / time;
		yinc = ydiff / time;
		Init.getDungeon().getPlayer().setLoc(ox, oy);
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
	public boolean done() {
		return time == 0;
	}

}
