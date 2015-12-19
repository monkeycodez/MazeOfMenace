package dungeon.tile;

import java.awt.Color;
import render.draw.DrawComponent;
import render.draw.FixedDrawComp;
import dungeon.tile.template.TileInteract;

public interface TileTemplate{

	public DrawComponent create_new_draw_component();

	public boolean can_walk();

	public boolean is_solid();

	public default boolean is_water(){
		return false;
	}

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
			return 1;
		}

		@Override
		public boolean is_solid(){
			return false;
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
			return 3;
		}

		@Override
		public boolean is_solid(){
			return true;
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
			return 2;
		}

		@Override
		public boolean is_solid(){
			return false;
		}
	};

}
