package run;

import render.draw.DrawMngr;
import render.io.InputControl;
import render.io.MMWindow;

public class MLoop{

	public static InputControl cont;

	public static DrawMngr draw;

	public static void run( MMWindow win, DrawMngr dm, InputControl c ){
		draw = dm;
		cont = c;
		long ms = 0;
		while(true){
			if(win.is_close_requested()){
				System.exit(0);
			}
			ms = System.currentTimeMillis();
			c.proccess_event(win);
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
