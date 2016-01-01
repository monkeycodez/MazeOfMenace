package run;

import run.gamestate.*;
import window.MMWindow;

public class MLoop{

	public static void run(MMWindow win){
		long ms = 0;
		long wait = 0;
		while(true){
			if(win.is_close_requested()){
				System.exit(0);
			}
			ms = System.currentTimeMillis();
			DrawControl dc = CtrlStateManager.get_dc();
			InputControl ic = CtrlStateManager.get_ic();
			UpdateControl uc = CtrlStateManager.get_uc();
			ic.proccess_event(win);
			uc.update(ms);
			dc.draw(win);
			ms = System.currentTimeMillis() - ms;
			wait = 100 / 3 - ms;
			wait = wait < 0 ? 0 : wait;
			try{
				Thread.sleep(wait);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}

	}

}
