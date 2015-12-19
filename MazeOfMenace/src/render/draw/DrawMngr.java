package render.draw;

import render.io.MMWindow;
import dungeon.Dungeon;

public class DrawMngr{

	private Dungeon dgn;

	private DDraw d;

	public DrawMngr(Dungeon dgn) {
		this.dgn = dgn;
		d = new DDraw();
	}

	public void draw( MMWindow win ){
		d.draw(win, dgn);
		win.swap();
	}

}
