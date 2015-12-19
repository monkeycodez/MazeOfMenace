package run;

import java.util.List;
import dungeon.Dungeon;
import dungeon.Level;
import entity.Updateable;

public class DgnUpdater implements UpdateControl{

	private Dungeon dgn;

	public DgnUpdater(Dungeon d) {
		dgn = d;
	}

	@Override
	public void update( long delay ){
		Level lcur = dgn.get_cur_lvl();
		List<Updateable> ups = lcur.get_updates();
		for(Updateable up : ups){
			up.update();
		}
	}

}
