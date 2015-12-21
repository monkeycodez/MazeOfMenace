package run;

import run.gamestate.DrawControl;
import run.gamestate.InputControl;
import run.gamestate.UpdateControl;
import window.MMWindow;

public class MLoop{

	private static CtrlStateManager mgr;

	public static void run(
		MMWindow win,
		CtrlStateManager mg ){
		mgr = mg;
		long ms = 0;
		long wait = 0;
		while(true){
			if(win.is_close_requested()){
				System.exit(0);
			}
			ms = System.currentTimeMillis();
			DrawControl dc = mgr.get_dc();
			InputControl ic = mgr.get_ic();
			UpdateControl uc = mgr.get_uc();
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
