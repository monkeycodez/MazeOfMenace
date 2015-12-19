package dungeon.tile.template;

import java.awt.Color;
import render.draw.DrawComponent;
import render.draw.FixedDrawComp;
import dungeon.tile.Tile;
import dungeon.tile.TileTemplate;
import entity.Entity;

public class StairUpTmpl implements TileTemplate{

	private static final DrawComponent dr = new FixedDrawComp(
		'<',
		Color.WHITE);

	private Tile nxt;

	public StairUpTmpl(Tile next) {
		nxt = next;
	}

	@Override
	public DrawComponent create_new_draw_component(){
		return dr;
	}

	@Override
	public TileInteract create_new_interaction( Tile to ){
		return new TileInteract(){

			@Override
			public void on_step( Entity e ){
			}

			@Override
			public void interact( Entity e ){
				e.move(nxt);
			}
		};
	}

	@Override
	public boolean can_walk(){
		return false;
	}

	public static final int STAIRUP = 5;

	@Override
	public int type(){
		return 5;
	}

	@Override
	public boolean is_solid(){
		return false;
	}
}