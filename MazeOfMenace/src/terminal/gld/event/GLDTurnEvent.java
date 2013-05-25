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

import run.Init;
import terminal.gld.GLDungeonDraw;
import entity.Entity;
import entity.MoveDirection;
import entity.player.Player;
import static org.lwjgl.opengl.GL11.*;

public class GLDTurnEvent implements GLDEvent {
	
	private int time = 10;
	private float inc = 0, rot = 0, orig = 0;
	
	public GLDTurnEvent(MoveDirection org, MoveDirection fin){
		float diff;
		if(org == MoveDirection.EAST && fin == MoveDirection.NORTH)
			diff = -90;
		else if(org == MoveDirection.NORTH && fin == MoveDirection.EAST)
			diff = 90;
		else
			diff = (GLDungeonDraw.getrot(fin)- GLDungeonDraw.getrot(org));
		orig = GLDungeonDraw.getrot(org);
		inc = diff / time;
//		System.out.println("diff:"+diff+" inc : "+inc+" rot: "+rot+" orig: "+orig+" org: "+org+" fin: "+fin);
		
	}

	@Override
	public void applyPLocStart() {
		glPushMatrix();
		Player p = Init.getDungeon().getPlayer();
		int x = p.getX(), y = p.getY();
		rot += inc;
		glRotatef(rot+ orig, 0, 1, 0);
		glScalef(6, 6, 6);
		glTranslatef(-x, -.1f, -y);
		time--;
	}

	@Override
	public void applyPLocEnd() {
		glPopMatrix();
	}

	@Override
	public void applyEntChStart(Entity e) {

	}

	@Override
	public void applyEnrChEnd(Entity e) {

	}

	@Override
	public void drawFinalChange() {

	}

	@Override
	public boolean done() {
		return time == 0;
	}

}
