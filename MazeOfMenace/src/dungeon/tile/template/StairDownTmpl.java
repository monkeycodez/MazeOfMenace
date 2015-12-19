package dungeon.tile.template;

import java.awt.Color;
import render.draw.DrawComponent;
import render.draw.FixedDrawComp;
import dungeon.Level;
import dungeon.tile.Tile;
import dungeon.tile.TileTemplate;
import entity.Entity;

public class StairDownTmpl implements TileTemplate{

	private static final DrawComponent dr = new FixedDrawComp(
		'>',
		Color.WHITE);

	@Override
	public DrawComponent create_new_draw_component(){
		return dr;
	}

	@Override
	public TileInteract create_new_interaction( Tile to ){
		return new SDTI();
	}

	@Override
	public boolean can_walk(){
		return false;
	}

	public static final int STAIRUP = 6;

	@Override
	public int type(){
		return 6;
	}

	private class SDTI implements TileInteract{

		private Tile nlvl = null;

		@Override
		public void on_step( Entity e ){
		}

		@Override
		public void interact( Entity e ){
			if(nlvl != null){
				e.move(nlvl);
				return;
			}
			Level n = e.getLOn().get_from().makeNewLevel();
			nlvl = n.get_connection("up_stair_1");
			e.move(nlvl);
		}
	}

	@Override
	public boolean is_solid(){
		return false;
	}
}