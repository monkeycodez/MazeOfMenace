package render.io;

import java.awt.event.KeyEvent;
import dungeon.Dungeon;
import entity.player.Player;

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
		switch(k.getKeyCode()){
			case KeyEvent.VK_UP:
				p.move(p.getLoc().north());
				break;
			case KeyEvent.VK_DOWN:
				p.move(p.getLoc().south());
				break;
			case KeyEvent.VK_LEFT:
				p.move(p.getLoc().west());
				break;
			case KeyEvent.VK_RIGHT:
				p.move(p.getLoc().east());
				break;
			case KeyEvent.VK_E:
				p.getLoc().getInteract().interact(p);
				break;

		}
	}

}
