package entity;

import dungeon.tile.Tile;

public interface EAction{

	public void do_action();

	public int get_base_cost();

	public default boolean is_valid(){
		return true;
	}

	public static EAction move_to( Entity e, Tile to ){
		return new EAction(){

			@Override
			public void do_action(){
				e.move(to);
			}

			@Override
			public boolean is_valid(){
				return e.can_go_on(to);
			}

			@Override
			public int get_base_cost(){
				return 100;
			}

		};
	}

	public static EAction walk_to( Entity e, Tile to ){
		return new EAction(){

			@Override
			public void do_action(){
				e.move(to);
			}

			@Override
			public boolean is_valid(){
				return e.can_walk_to(to);
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
