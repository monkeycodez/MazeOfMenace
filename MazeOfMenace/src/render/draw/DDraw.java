package render.draw;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import window.MMWindow;
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
		Level cur = dgn.get_cur_lvl();
		char[] cl = new char[1];
		g.setColor(Color.black);
		g.fillRect(0, 0, w.get_size().width, w.get_size().height);
		for(int i = 0; i < cur.xlen(); i++){
			for(int j = 0; j < cur.ylen(); j++){
				Tile ct = cur.getT(i, j);
				Pair<Character, Color> c = ct.getDcomp()
					.getc();
				if(ct.getCurrEntity() != null){
					c = ct.getCurrEntity().getDraw().getc();
				}

				g.setColor(c.b);
				cl[0] = c.a;
				g.drawChars(cl, 0, 1, (3 + i) * ftx, (3 + j) *
					fty);

			}
		}
		g.drawString("" + cur.getDepth(), 90 * ftx, 30);
	}
}
