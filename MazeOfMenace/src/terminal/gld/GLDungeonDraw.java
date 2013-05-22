package terminal.gld;

import static org.lwjgl.opengl.GL11.*;
import run.Init;
import terminal.gld.event.GLDEvent;
import terminal.gld.event.GLDEventQue;
import dungeon.tile.Floor;
import dungeon.tile.StairDown;
import dungeon.tile.StairUp;
import dungeon.tile.Tile;
import dungeon.tile.Wall;
import engine.image.TextureDB;
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
		glFogf(GL_FOG_START, 18f);
		glFogf(GL_FOG_END, 30f);

		if ((currevent != null) && currevent.done()) {
			currevent = null;
		}
		if (events.hasNext()) {
			currevent = events.get();
		}

		if (currevent == null) {
			glPushMatrix();
			Init.getDungeon().getCurrentLevelObj();
			Player p = Init.getDungeon().getPlayer();
			int x = p.getX(), y = p.getY();
			applyrot(p.getFacing());
			glScalef(6, 6, 6);
			glTranslatef(-x, yz, -y);
			drawdgn();
			drawEntity();
			glPopMatrix();
		} else {
			glPushMatrix();
			currevent.applyPLocStart();

			drawdgn();
			drawEntity();
			currevent.applyPLocEnd();
			glPopMatrix();
		}
		glDisable(GL_FOG);

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
		glPushMatrix();
		glTranslatef(t.getX(), 0, t.getY());
		glScalef(.5f, .5f, .5f);
		glBegin(GL_QUADS);
		{
			glTexCoord2f(0, 0);
			glVertex3f(.5f, .5f, .5f);
			glTexCoord2f(1, 0);
			glVertex3f(.5f, -.5f, .5f);
			glTexCoord2f(1, 1);
			glVertex3f(-.5f, -.5f, .5f);
			glTexCoord2f(0, 1);
			glVertex3f(-.5f, .5f, .5f);
			// back
			glTexCoord2f(0, 0);
			glVertex3f(.5f, .5f, -.5f);
			glTexCoord2f(1, 0);
			glVertex3f(.5f, -.5f, -.5f);
			glTexCoord2f(1, 1);
			glVertex3f(-.5f, -.5f, -.5f);
			glTexCoord2f(0, 1);
			glVertex3f(-.5f, .5f, -.5f);
			// left
			glTexCoord2f(0, 0);
			glVertex3f(-.5f, .5f, .5f);
			glTexCoord2f(1, 0);
			glVertex3f(-.5f, -.5f, .5f);
			glTexCoord2f(1, 1);
			glVertex3f(-.5f, -.5f, -.5f);
			glTexCoord2f(0, 1);
			glVertex3f(-.5f, .5f, -.5f);
			// right
			glTexCoord2f(0, 0);
			glVertex3f(.5f, .5f, .5f);
			glTexCoord2f(1, 0);
			glVertex3f(.5f, -.5f, .5f);
			glTexCoord2f(1, 1);
			glVertex3f(.5f, -.5f, -.5f);
			glTexCoord2f(0, 1);
			glVertex3f(.5f, .5f, -.5f);
		}
		glEnd();
		glPopMatrix();
	}

	public static void drawEntity() {
		Tile[][] tarr = Init.getDungeon().getCurrentLevelObj().getlvl();
		for (Tile[] ta : tarr) {
			for (Tile t : ta) {
				if ((t.getCurrEntity() != null)
						&& !(t.getCurrEntity() instanceof Player)) {
					drawE(t);
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
