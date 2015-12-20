package entity.player;

import entity.EAction;
import entity.Updateable;

public class PlayerUpdateable implements Updateable{

	private int cooldown;

	private Player pl;

	public PlayerUpdateable(Player p) {
		pl = p;
	}

	@Override
	public boolean update(){
		if(next == null){
			return false;
		}
		EAction move = next;
		move.do_action();
		cooldown += move.get_base_cost();
		next = null;
		return true;
	}

	private EAction next;

	public void register_next_move( EAction e ){
		next = e;
	}

	@Override
	public void reduce_cooldown( int time ){
		cooldown -= time;
	}

	@Override
	public int get_cooldown(){
		return cooldown;
	}

	@Override
	public int get_speed(){
		//TODO: redo when stat overhaul occurs
		return pl.getStat().getSpeed();
	}

}
