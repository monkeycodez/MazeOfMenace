package dungeon.tile;

import java.awt.Color;
import dungeon.Pair;

public class DrawComponent{

	private Tile t;

	public DrawComponent(Tile tl) {
		t = tl;
	}

	public Pair<Character, Color> getc(){
		Color c = Color.black;
		char cl = ' ';
		c = Color.gray;
		cl = t.getBasetile().draw();
		//}
		return new Pair<Character, Color>(cl, c);
	}

}
