package render.draw;

import java.awt.Color;
import dungeon.Pair;

public class FixedDrawComp implements DrawComponent{

	private Pair<Character, Color> p;

	public FixedDrawComp(char c, Color col) {
		p = new Pair<>(c, col);
	}

	@Override
	public Pair<Character, Color> getc(){
		return p;
	}

}
