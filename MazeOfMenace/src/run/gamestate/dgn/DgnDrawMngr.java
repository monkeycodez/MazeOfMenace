package run.gamestate.dgn;

import render.draw.DDraw;
import run.gamestate.DrawControl;
import window.MMWindow;
import dungeon.Dungeon;

public class DgnDrawMngr implements DrawControl{

	private Dungeon dgn;

	private DDraw d;

	public DgnDrawMngr(Dungeon dgn) {
		this.dgn = dgn;
		d = new DDraw();
	}

	@Override
	public void draw( MMWindow win ){
		d.draw(win, dgn);
		win.swap();
	}

}
