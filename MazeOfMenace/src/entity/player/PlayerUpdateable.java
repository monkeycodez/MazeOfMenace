package entity.player;

import entity.EAction;
import entity.Updateable;

public class PlayerUpdateable implements Updateable{

	private int cooldown;

	@Override
	public boolean update(){
		if(next == null){
			return false;
		}
		EAction move = next;
		move.do_action();
		cooldown -= move.get_base_cost();
		return true;
	}

	private EAction next;

	public void register_next_move( EAction e ){
		next = e;
	}

	public int getCooldown(){
		return cooldown;
	}

	public void incCooldown( int cooldown ){
		this.cooldown += cooldown;
	}

}
