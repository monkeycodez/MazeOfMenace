package run;

import run.gamestate.mmenu.MainMenu;
import window.MMWindow;

public class Init{

	public static void main(String args[]){
		MMWindow w = setup();
		CtrlStateManager mgr = new CtrlStateManager(null);
		MainMenu m = new MainMenu(mgr);
		mgr.push_all(m);
		MLoop.run(w, mgr);
	}

	private static MMWindow setup(){
		MMWindow w = new MMWindow();
		w.init();
		return w;
	}

}
