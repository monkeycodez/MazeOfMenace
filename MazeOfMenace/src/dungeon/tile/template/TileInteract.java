package dungeon.tile.template;

import entity.Entity;

public interface TileInteract{

	public void on_step( Entity e );

	public void interact( Entity e );

	public static TileInteract nullInteract = new TileInteract(){

		@Override
		public void on_step( Entity e ){
		}

		@Override
		public void interact( Entity e ){
		}
	};

	public static TileInteract cleanFountainItr = new TileInteract(){

		@Override
		public void on_step( Entity e ){
		}

		@Override
		public void interact( Entity e ){
			//TODO: change to real interaction
			e.setHp(10);
		}
	};

}
