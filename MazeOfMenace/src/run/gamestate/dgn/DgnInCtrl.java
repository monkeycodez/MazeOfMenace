package run.gamestate.dgn;

import java.awt.event.KeyEvent;
import render.io.MMWindow;
import run.gamestate.InputControl;
import dungeon.Dungeon;
import entity.EAction;
import entity.player.Player;
import entity.player.PlayerUpdateable;

public class DgnInCtrl implements InputControl{

	private Dungeon dgn;

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
		PlayerUpdateable pup = p.get_p_up();
		switch(k.getKeyCode()){
			case KeyEvent.VK_UP:
				pup.register_next_move(EAction.move_to(p,
					p.getLoc().north()));
				break;
			case KeyEvent.VK_DOWN:
				pup.register_next_move(EAction.move_to(p,
					p.getLoc().south()));
				break;
			case KeyEvent.VK_LEFT:
				pup.register_next_move(EAction.move_to(p,
					p.getLoc().west()));
				break;
			case KeyEvent.VK_RIGHT:
				pup.register_next_move(EAction.move_to(p,
					p.getLoc().east()));
				break;
			case KeyEvent.VK_E:
				pup.register_next_move(EAction.interact_on(p));
				break;

		}
	}

}
