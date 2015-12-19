package run;

import render.draw.DrawMngr;
import render.io.InputControl;
import render.io.MMWindow;

public class MLoop{

	public static InputControl cont;

	public static DrawMngr draw;

	public static UpdateControl update;

	public static void run(
		MMWindow win,
		DrawMngr dm,
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
				Thread.sleep(1000 / 30);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}

	}

}
