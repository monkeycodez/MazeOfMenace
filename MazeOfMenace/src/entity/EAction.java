package entity;

import dungeon.tile.Tile;

public interface EAction{

	public void do_action();

	public int get_base_cost();

	public static EAction move_to( Entity e, Tile to ){
		return new EAction(){

			@Override
			public void do_action(){
				e.move(to);
			}

			@Override
			public int get_base_cost(){
				return 100;
			}

		};
	}

	public static EAction interact_on( Entity e ){
		return new EAction(){

			@Override
			public int get_base_cost(){
				return 100;
			}

			@Override
			public void do_action(){
				e.getLoc().getInteract().interact(e);
			}

		};
	}

}
