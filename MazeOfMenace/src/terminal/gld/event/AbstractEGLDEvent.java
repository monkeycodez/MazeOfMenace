package terminal.gld.event;

import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import entity.Entity;
import entity.player.Player;
import run.Init;
import run.Settings;
import terminal.gld.GLDungeonDraw;


public abstract class AbstractEGLDEvent implements GLDEvent{

	protected int time = Settings.getGle();
	private int px, py;
	public AbstractEGLDEvent(){
		Init.getDungeon().getCurrentLevelObj();
		Player p = Init.getDungeon().getPlayer();
		px = p.getX(); py = p.getY();
	}
	
	@Override
	public void applyPLocStart() {
		time--;
		Init.getDungeon().getCurrentLevelObj();
		Player p = Init.getDungeon().getPlayer();
		glPushMatrix();
		GLDungeonDraw.applyrot(p.getFacing());
		glScalef(6, 6, 6);
		glTranslatef(-px, -.1f, -py);
	}

	@Override
	public void applyPLocEnd() {
		GLDungeonDraw.PlayerEnd();

	}
	
	public boolean done(){
		return time == 0;
	}

	@Override
	public boolean applyEntChStart(Entity e) {
		return false;
	}

	@Override
	public boolean applyEnrChEnd(Entity e) {
		return false;
	}

	@Override
	public void drawFinalChange() {
		
	}
	
	

}
