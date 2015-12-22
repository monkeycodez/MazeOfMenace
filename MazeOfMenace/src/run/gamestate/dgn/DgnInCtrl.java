package run.gamestate.dgn;

import java.awt.event.KeyEvent;
import java.util.*;
import run.gamestate.InputControl;
import window.MMWindow;
import dungeon.Dungeon;
import dungeon.tile.Tile;
import entity.EAction;
import entity.player.*;

public class DgnInCtrl implements InputControl{

	private static interface key_func{

		public EAction comp( Player p, Tile on );

	}

	private static key_func[] keys = {
			( p, on ) -> EAction.walk_to(p, on.north()),
			( p, on ) -> EAction.walk_to(p, on.south()),
			( p, on ) -> EAction.walk_to(p, on.west()),
			( p, on ) -> EAction.walk_to(p, on.east()),
			( p, on ) -> EAction.walk_to(p, on.nw()),
			( p, on ) -> EAction.walk_to(p, on.ne()),
			( p, on ) -> EAction.walk_to(p, on.sw()),
			( p, on ) -> EAction.walk_to(p, on.se()),
			( p, on ) -> EAction.interact_on(p)
	};

	private Dungeon dgn;

	private Map<Integer, key_func> kmap = new HashMap<>();

	{
		kmap.put(KeyEvent.VK_UP, keys[0]);
		kmap.put(KeyEvent.VK_K, keys[0]);
		kmap.put(KeyEvent.VK_NUMPAD8, keys[0]);

		kmap.put(KeyEvent.VK_DOWN, keys[1]);
		kmap.put(KeyEvent.VK_J, keys[1]);
		kmap.put(KeyEvent.VK_NUMPAD2, keys[1]);

		kmap.put(KeyEvent.VK_LEFT, keys[2]);
		kmap.put(KeyEvent.VK_H, keys[2]);
		kmap.put(KeyEvent.VK_NUMPAD4, keys[2]);

		kmap.put(KeyEvent.VK_RIGHT, keys[3]);
		kmap.put(KeyEvent.VK_L, keys[3]);
		kmap.put(KeyEvent.VK_NUMPAD6, keys[3]);

		kmap.put(KeyEvent.VK_E, keys[8]);

	}

	public DgnInCtrl(Dungeon dgn) {
		this.dgn = dgn;
	}

	@Override
	public void proccess_event( MMWindow win ){
		KeyEvent k = win.get_last_event();
		if(k == null){
			return;
		}
		Player p = dgn.getPlayer();
		Tile on = p.getLoc();
		PlayerUpdateable pup = p.get_p_up();
		EAction next = null;
		key_func det = kmap.get(k.getKeyCode());
		if(det == null){
			return;
		}
		next = det.comp(p, on);
		if(next == null || !next.is_valid()){
			return;
		}
		pup.register_next_move(next);
	}

}
