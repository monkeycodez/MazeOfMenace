/*******************************************************************************
 * Copyright (c) 2013 Matthew Gruda.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 * matthew - initial API and implementation
 ******************************************************************************/
package terminal.gld;

import static org.lwjgl.opengl.GL11.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import run.Init;
import run.Util;
import terminal.gld.event.GLDEvent;
import terminal.gld.event.GLDEventQue;
import dungeon.tile.Floor;
import dungeon.tile.Fountan;
import dungeon.tile.StairDown;
import dungeon.tile.StairUp;
import dungeon.tile.Tile;
import dungeon.tile.Wall;
import engine.image.TextureDB;
import entity.Entity;
import entity.MoveDirection;
import entity.player.Player;

public class GLDungeonDraw {

	public static float yz = -.1f;

	private static boolean d = false;

	private static int dl = -1;

	private static GLDEventQue events = new GLDEventQue();

	private static GLDEvent currevent = null;

	public static float[] bottomv = {
			.5f, -.5f, .5f, // top left
			-.5f, -.5f, .5f, // top right
			-.5f, -.5f, -.5f, // bottom right
			.5f, -.5f, -.5f
	};

	public static void addEvent(GLDEvent e) {
		events.put(e);
	}

	public static void applyrot(MoveDirection d) {
		switch (d) {
			case EAST:
				glRotatef(90, 0, 1, 0);
				break;
			case NORTH:
				break;
			case SOUTH:
				glRotatef(180, 0, 1, 0);
				break;
			case WEST:
				glRotatef(270, 0, 1, 0);
				break;
			default:
				break;

		}
	}

	public static void draw() {
		glEnable(GL_FOG);
		glFog(GL_FOG_COLOR, GLUtils.toBuf(new float[] {
				.1f, .1f, .1f, 1f
		}));
		glFogi(GL_FOG_MODE, GL_LINEAR);
		glFogf(GL_FOG_START, 10f);
		glFogf(GL_FOG_END, 30f);

		if ((currevent != null) && currevent.done()) {
			currevent = null;
		}
		if (currevent == null && events.hasNext()) {
			currevent = events.get();
		}

		if (currevent == null) {
			PlayerStart();
			drawdgn();
			drawEntity();
			drawItems();
			drawHUD();
			PlayerEnd();
		} else {
			glPushMatrix();
			currevent.applyPLocStart();
			drawdgn();
			drawEntity();
			drawItems();
			drawHUD();
			currevent.applyPLocEnd();
			glPopMatrix();
		}
		glDisable(GL_FOG);

	}

	public static void PlayerStart() {
		Init.getDungeon().getCurrentLevelObj();
		Player p = Init.getDungeon().getPlayer();
		int x = p.getX(), y = p.getY();
		glPushMatrix();
		applyrot(p.getFacing());
		glScalef(6, 6, 6);
		glTranslatef(-x, yz, -y);
	}

	public static void PlayerEnd() {
		glPopMatrix();
	}

	private static Color[] mkcolarr(String s) {
		List<Color> colarr = new ArrayList<Color>();
		Color last = Color.white;
		boolean in = false, no = false;
		for (int i = 0; i < s.length(); i++) {

			char c = s.charAt(i);

			if (no) {
				last = Util.getColor(c);
				no = false;
				continue;
			}

			if (in && c == '`') {
				last = Color.white;
				in = !in;
				continue;
			}

			if (c == '`') {
				no = true;
				in = true;
				continue;
			}
			colarr.add(last);

		}
		return colarr.toArray(new Color[] {});
	}

	private static String cleanstr(String s) {
		StringBuilder str = new StringBuilder();

		boolean in = false, no = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (no) {
				no = false;
				continue;
			}

			if (in && c == '`') {
				in = !in;
				continue;
			}

			if (c == '`') {
				no = true;
				in = true;
				continue;
			}
			str.append(c);
		}

		return str.toString();
	}

	private static void drawHUD() {
		glDisable(GL_FOG);
		String s = "";
		s = entity.player.Display.getDispLine();
		int e = glGetError();
		if (e != GL11.GL_NO_ERROR)
			System.out.println(e
					+ " h "
					+ org.lwjgl.opengl.Util
							.translateGLErrorString(e));
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 600, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		glPushMatrix();
		glLoadIdentity();

		glClear(GL_DEPTH_BUFFER_BIT);
		// glColor3f(1, 0, 0);
		String str = cleanstr(s);
		GLDrawer.fnt.drawString(10, 130, str, 0, str.length() - 1,
				.35f, .35f, TrueTypeFont.ALIGN_LEFT,
				mkcolarr(s));

		glPopMatrix();

		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		// glLoadIdentity();
		// GLU.gluPerspective(80, 1366 / 786, 1f, 500f);
		glMatrixMode(GL_MODELVIEW);

	}

	public static void drawItems() {
		Tile[][] tarr = Init.getDungeon().getCurrentLevelObj().getlvl();
		for (Tile[] ta : tarr) {
			for (Tile t : ta) {
				if (t.getObject() != null) {
					drawI(t);
				}
			}
		}
	}

	private static void drawI(Tile t) {
		glBindTexture(GL_TEXTURE_2D, t.getObject().getTexId());
		glPushMatrix();
		glTranslatef(t.getX(), 0, t.getY());
		glScalef(.5f, .5f, .5f);
		glBegin(GL_QUADS);
		{
			glTexCoord2f(0, 0);
			glVertex3f(.5f, -.8f, .5f);
			glTexCoord2f(1, 0);
			glVertex3f(-.5f, -.8f, .5f);
			glTexCoord2f(1, 1);
			glVertex3f(-.5f, -.8f, -.5f);
			glTexCoord2f(0, 1);
			glVertex3f(.5f, -.8f, -.5f);
		}
		glEnd();
		glPopMatrix();
	}

	public static void drawdgn() {

		glPushMatrix();
		disp: {
			if (d) {

				glCallList(dl);
				break disp;
			}
			dl = glGenLists(1);
			glNewList(dl, GL_COMPILE);
			Tile[][] t = Init.getDungeon().getCurrentLevelObj()
					.getlvl();

			for (Tile[] tlz : t) {
				for (Tile tl : tlz) {
					if (tl.getBasetile() instanceof StairDown) {
						drawfloor(tl.getX(),
								tl.getY(),
								TextureDB.getTexture(
										"./dat/tiles/fancy/static/stairdown.png")
										.getTextureID());
					} else if (tl.getBasetile() instanceof StairUp) {
						drawfloor(tl.getX(),
								tl.getY(),
								TextureDB.getTexture(
										"./dat/tiles/fancy/static/stairup.png")
										.getTextureID());
					} else if (tl.getBasetile() instanceof Fountan) {
						drawfloor(tl.getX(),
								tl.getY(),
								TextureDB.getTexture(
										"./dat/tiles/fancy/static/fountain.png")
										.getTextureID());
					} else if (tl.getBasetile() instanceof Floor) {
						drawfloor(tl.getX(), tl.getY());
					} else if (tl.getBasetile() instanceof Wall) {
						drawWall(tl.getX(), tl.getY());
					}
				}
			}
			glEndList();
			glCallList(dl);
			d = true;
		}
		glPopMatrix();

	}

	private static void drawE(Tile t) {
		glBindTexture(GL_TEXTURE_2D, t.getCurrEntity().getTexId());

		glBegin(GL_QUADS);
		{
			glTexCoord2f(1, 0);
			glVertex3f(.5f, .5f, .5f);
			glTexCoord2f(1, 1);
			glVertex3f(.5f, -.5f, .5f);
			glTexCoord2f(0, 1);
			glVertex3f(-.5f, -.5f, .5f);
			glTexCoord2f(0, 0);
			glVertex3f(-.5f, .5f, .5f);
			// back
			glTexCoord2f(1, 0);
			glVertex3f(.5f, .5f, -.5f);
			glTexCoord2f(1, 01);
			glVertex3f(.5f, -.5f, -.5f);
			glTexCoord2f(0, 1);
			glVertex3f(-.5f, -.5f, -.5f);
			glTexCoord2f(0, 0);
			glVertex3f(-.5f, .5f, -.5f);
			// left
			glTexCoord2f(1, 0);
			glVertex3f(-.5f, .5f, .5f);
			glTexCoord2f(1, 1);
			glVertex3f(-.5f, -.5f, .5f);
			glTexCoord2f(0, 1);
			glVertex3f(-.5f, -.5f, -.5f);
			glTexCoord2f(0, 0);
			glVertex3f(-.5f, .5f, -.5f);
			// right
			glTexCoord2f(1, 0);
			glVertex3f(.5f, .5f, .5f);
			glTexCoord2f(1, 1);
			glVertex3f(.5f, -.5f, .5f);
			glTexCoord2f(0, 1);
			glVertex3f(.5f, -.5f, -.5f);
			glTexCoord2f(0, 0);
			glVertex3f(.5f, .5f, -.5f);
		}
		glEnd();
	}

	public static void entityStart(Entity e) {
		glPushMatrix();
		glTranslatef(e.getX(), 0, e.getY());
		glScalef(.5f, .5f, .5f);
	}

	public static void entityEnd(Entity e) {
		glPopMatrix();
	}

	public static void drawEntity() {
		Tile[][] tarr = Init.getDungeon().getCurrentLevelObj().getlvl();
		for (Tile[] ta : tarr) {
			for (Tile t : ta) {
				if ((t.getCurrEntity() != null)
						&& !(t.getCurrEntity() instanceof Player)) {
					if (currevent == null) {
						entityStart(t.getCurrEntity());
						drawE(t);
						entityEnd(t.getCurrEntity());
					} else {
						if (!currevent.applyEntChStart(t
								.getCurrEntity())) {
							entityStart(t.getCurrEntity());
						}
						drawE(t);
						if (!currevent.applyEnrChEnd(t
								.getCurrEntity())) {
							entityEnd(t.getCurrEntity());
						}
					}
				}
			}
		}
	}

	private static void drawfloor(int x, int y) {
		drawfloor(x,
				y,
				TextureDB.getTexture(
						"./dat/tiles/fancy/static/floor.png")
						.getTextureID());
	}

	private static void drawfloor(int x, int y, int texid) {
		glBindTexture(GL_TEXTURE_2D, texid);
		glColor3f(1, 1, 1);
		glPushMatrix();
		glTranslatef(x, 0, y);
		x = 0;
		y = 0;
		glBegin(GL_QUADS);
		{
			glTexCoord2f(0, 0);
			glVertex3f(.5f + x, -.5f, .5f + y);
			glTexCoord2f(1, 0);
			glVertex3f(-.5f + x, -.5f, .5f + y);
			glTexCoord2f(1, 1);
			glVertex3f(-.5f + x, -.5f, -.5f + y);
			glTexCoord2f(0, 1);
			glVertex3f(.5f + x, -.5f, -.5f + y);
		}
		glEnd();
		glBindTexture(GL_TEXTURE_2D,
				TextureDB.getTexture(
						"./dat/tiles/fancy/static/floor.png")
						.getTextureID());
		glBegin(GL_QUADS);
		{
			glTexCoord2f(0, 0);
			glVertex3f(.5f + x, .5f, .5f + y);
			glTexCoord2f(1, 0);
			glVertex3f(-.5f + x, .5f, .5f + y);
			glTexCoord2f(1, 1);
			glVertex3f(-.5f + x, .5f, -.5f + y);
			glTexCoord2f(0, 1);
			glVertex3f(.5f + x, .5f, -.5f + y);
		}
		glEnd();
		glPopMatrix();
	}

	private static void drawWall(int x, int y) {
		glBindTexture(GL_TEXTURE_2D,
				TextureDB.getTexture(
						"./dat/tiles/fancy/static/wall.png")
						.getTextureID());
		glColor3f(1, 1, 1);
		glPushMatrix();
		glTranslatef(x, 0, y);
		x = 0;
		y = 0;
		glBegin(GL_QUADS);
		{
			glTexCoord2f(0, 0);
			glVertex3f(.5f + x, .5f, .5f + y);
			glTexCoord2f(1, 0);
			glVertex3f(.5f + x, -.5f, .5f + y);
			glTexCoord2f(1, 1);
			glVertex3f(-.5f + x, -.5f, .5f + y);
			glTexCoord2f(0, 1);
			glVertex3f(-.5f + x, .5f, .5f + y);
			// back
			glTexCoord2f(0, 0);
			glVertex3f(.5f + x, .5f, -.5f + y);
			glTexCoord2f(1, 0);
			glVertex3f(.5f + x, -.5f, -.5f + y);
			glTexCoord2f(1, 1);
			glVertex3f(-.5f + x, -.5f, -.5f + y);
			glTexCoord2f(0, 1);
			glVertex3f(-.5f + x, .5f, -.5f + y);
			// left
			glTexCoord2f(0, 0);
			glVertex3f(-.5f + x, .5f, .5f + y);
			glTexCoord2f(1, 0);
			glVertex3f(-.5f + x, -.5f, .5f + y);
			glTexCoord2f(1, 1);
			glVertex3f(-.5f + x, -.5f, -.5f + y);
			glTexCoord2f(0, 1);
			glVertex3f(-.5f + x, .5f, -.5f + y);
			// right
			glTexCoord2f(0, 0);
			glVertex3f(.5f + x, .5f, .5f + y);
			glTexCoord2f(1, 0);
			glVertex3f(.5f + x, -.5f, .5f + y);
			glTexCoord2f(1, 1);
			glVertex3f(.5f + x, -.5f, -.5f + y);
			glTexCoord2f(0, 1);
			glVertex3f(.5f + x, .5f, -.5f + y);
		}
		glEnd();
		glPopMatrix();
	}

	public static int getrot(MoveDirection d) {
		switch (d) {
			case EAST:
				return 90;
			case SOUTH:
				return 180;
			case WEST:
				return 270;
			case NORTH:
				return 360;
			default:
				break;
		}
		return 0;
	}

	public static boolean inEvent() {
		return currevent != null;
	}

	public static void updatedgn() {
		d = false;
	}

}
