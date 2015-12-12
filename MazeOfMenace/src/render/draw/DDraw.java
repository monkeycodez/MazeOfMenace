package render.draw;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import render.io.MMWindow;
import dungeon.Dungeon;
import dungeon.Level;
import dungeon.Pair;
import dungeon.tile.Tile;

public class DDraw{

	private Font fnt;

	private int ftx, fty;

	public DDraw() {
		fnt = new Font("Monospaced", 0, 14);
	}

	public void draw( MMWindow w, Dungeon dgn ){
		Graphics2D g = w.get_graphics();
		FontMetrics ftm = g.getFontMetrics(fnt);
		ftx = ftm.charWidth(' ');
		fty = ftm.getHeight();
		g.setFont(fnt);
		Level cur = dgn.getCurrentLevelObj();
		Tile[][] lvl = cur.getlvl();
		char[] cl = new char[1];
		for(int i = 0; i < cur.getlvl().length; i++){
			for(int j = 0; j < cur.getlvl()[0].length; j++){
				Pair<Character, Color> c = lvl[i][j].getDcomp()
					.getc();
				g.setColor(c.b);
				cl[0] = c.a;
				g.drawChars(cl, 0, 1, i * ftx, j * fty);

			}
		}
	}
}
