package dungeon.tile;

import java.awt.Color;
import render.draw.DrawComponent;
import render.draw.FixedDrawComp;
import dungeon.tile.template.TileInteract;

public interface TileTemplate{

	public DrawComponent create_new_draw_component();

	public boolean can_walk();

	public int type();

	default public TileInteract create_new_interaction( Tile to ){
		return TileInteract.nullInteract;
	}

	public static final TileTemplate floor = new TileTemplate(){

		private final DrawComponent dr = new FixedDrawComp(
			'.',
			Color.WHITE);

		@Override
		public DrawComponent create_new_draw_component(){
			return dr;
		}

		@Override
		public boolean can_walk(){
			return true;
		}

		@Override
		public int type(){
			// TODO Auto-generated method stub
			return 1;
		}
	};

	public static final TileTemplate wall = new TileTemplate(){

		private final DrawComponent dr = new FixedDrawComp(
			'#',
			Color.WHITE);

		@Override
		public DrawComponent create_new_draw_component(){
			return dr;
		}

		@Override
		public boolean can_walk(){
			return false;
		}

		@Override
		public int type(){
			// TODO Auto-generated method stub
			return 3;
		}
	};

	public static final TileTemplate fountain = new TileTemplate(){

		private final DrawComponent dr = new FixedDrawComp(
			'%',
			Color.CYAN);

		@Override
		public DrawComponent create_new_draw_component(){
			return dr;
		}

		@Override
		public boolean can_walk(){
			return true;
		}

		@Override
		public int type(){
			// TODO Auto-generated method stub
			return 2;
		}
	};

}
