package run;

import run.gamestate.mmenu.MainMenu;
import window.MMWindow;

public class Init{

	public static void main(String args[]){
		MMWindow w = setup();
		MainMenu m = new MainMenu(w);
		CtrlStateManager.push_all(m);
		MLoop.run(w);
	}

	private static MMWindow setup(){
		MMWindow w = new MMWindow();
		w.init();
		return w;
	}

}
