package generator;

import dungeon.Dungeon;
import dungeon.Level;

public interface LevelGenStrategy{

	public Level form_dungeon( Dungeon dgn, int depth );

}
