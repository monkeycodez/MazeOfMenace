package run.gamestate.dgn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import run.gamestate.UpdateControl;
import dungeon.Dungeon;
import dungeon.Level;
import entity.Updateable;

public class DgnUpdater implements UpdateControl{

	private Dungeon dgn;

	private PriorityQueue<Updateable> updates;

	private Updateable top;

	public DgnUpdater(Dungeon d) {
		dgn = d;
	}

	private void update_queue(){
		Level lcur = dgn.get_cur_lvl();
		List<Updateable> ups = new ArrayList<>(lcur.get_updates());
		for(Updateable u : ups){
			u.reduce_cooldown(10);
		}
		updates = new PriorityQueue<Updateable>(
			new Comparator<Updateable>(){

				@Override
				public int compare( Updateable l, Updateable r ){
					if(l.get_cooldown() > r.get_cooldown()){
						return 1;
					}else if(r.get_cooldown() > l
						.get_cooldown()){
						return -1;
					}
					int sp = l.get_speed() - r.get_speed();
					return sp > 0 ? 1 : sp < 0 ? -1 : 0;
				}

			});
		updates.addAll(ups);
	}

	@Override
	public void update( long delay ){
		if(updates == null){
			update_queue();
		}
		if(top == null){
			top = updates.remove();
		}
		boolean done = false;
		do{
			done = top.update();
			if(!done){
				return;
			}
			top = updates.poll();
		}while(!updates.isEmpty());
		updates = null;

	}

}
