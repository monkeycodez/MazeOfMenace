package render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import render.draw.DDraw;
import render.io.MMWindow;
import run.Init;
import dungeon.Dungeon;

public class Tester{

	public static void main( String args[] ){
		MMWindow w = new MMWindow();
		w.init();

		Graphics2D g = w.get_graphics();

		g.setBackground(Color.CYAN);
		g.setColor(Color.BLUE);
		g.clearRect(0, 0, 9000, 9000);
		g.fillRect(0, 0, 6000, 1000);
		g.setColor(Color.red);
		g.setFont(new Font("Monospaced", 0, 14));
		g.drawString("Loading", 600f, 350f);
		w.swap();
		DDraw dd = new DDraw();
		Dungeon d = new Dungeon();
		Init.dgn = d;
		d.setUpDungeon();
		dd.draw(w, d);
		w.swap();
	}

}
