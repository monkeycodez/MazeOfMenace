package run;

import render.io.MMWindow;
import run.gamestate.DrawControl;
import run.gamestate.InputControl;
import run.gamestate.UpdateControl;

public class MLoop{

	public static InputControl cont;

	public static DrawControl draw;

	public static UpdateControl update;

	public static void run(
		MMWindow win,
		DrawControl dm,
		InputControl c,
		UpdateControl upd ){

		draw = dm;
		cont = c;
		update = upd;
		long ms = 0;
		while(true){
			if(win.is_close_requested()){
				System.exit(0);
			}
			ms = System.currentTimeMillis();
			cont.proccess_event(win);
			update.update(ms);
			draw.draw(win);
			ms = System.currentTimeMillis() - ms;
			try{
				Thread.sleep(100 / 3 - ms);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}

	}

}
