package run.gamestate.mmenu;

import java.awt.*;
import java.awt.event.KeyEvent;
import run.CtrlStateManager;
import run.gamestate.*;
import run.gamestate.dgn.*;
import run.gamestate.mmenu.LoadingScreen;
import window.MMWindow;
import window.widget.Label;
import dungeon.Dungeon;

public class MainMenu implements GameControl{

	private static String[] options = {"Start", "Exit"};

	class dpl{

		InputControl ic = null;

		UpdateControl uc = null;

		DrawControl dc = null;
	}

	private static Runnable[] opt_act = new Runnable[]{()-> {

		LoadingScreen.<dpl> do_loading_screen(()-> {
			Dungeon d = new Dungeon();
			d.setUpDungeon();
			dpl z = new dpl();
			z.ic = new DgnInCtrl(d);
			z.uc = new DgnUpdater(d);
			z.dc = new DgnDrawMngr(d);
			return z;

		}, (tpl)-> {
			CtrlStateManager.push_dc(tpl.dc);
			CtrlStateManager.push_ic(tpl.ic);
			CtrlStateManager.push_uc(tpl.uc);
		});

	}, ()-> {
		System.exit(0);
	}};

	private Font ofont = new Font("Monospaced", Font.BOLD, 20);

	private Label[] lbls = new Label[]{
			new Label(options[0], ofont, null, Color.WHITE, new Color(0, 0, 0,
				0)),
			new Label(options[1], ofont, null, Color.WHITE, new Color(0, 0, 0,
				0))};

	public MainMenu(MMWindow win) {
		this(win, options, opt_act);
	}

	public MainMenu(MMWindow win, String ops[], Runnable opa[]) {
		options = ops;
		opt_act = opa;
		assert (ops.length == opa.length);

	}

	private int selected = 0;

	private boolean sized = false;

	private Color clear = new Color(0, 0, 0, 0);

	@Override
	public void draw(MMWindow win){
		Dimension d = win.get_size();
		Graphics2D gd = win.get_graphics();
		gd.setColor(Color.black);
		gd.fillRect(0, 0, d.width, d.height);
		if(!sized){
			sized = true;
			FontMetrics fm = gd.getFontMetrics(ofont);
			int xcen = d.width / 2;
			int ycen = d.height / 2;
			int maxl = 0;
			for(int i = 0; i < options.length; i++){
				int lsz = fm.stringWidth(options[i]);
				if(lsz > maxl){
					maxl = lsz;
				}

			}

			int width = maxl * 4;
			int xloc = xcen - maxl * 2;
			int height = fm.getHeight() * 5 / 3;
			int halfopt = options.length / 2;
			for(int i = 0; i < lbls.length; i++){
				int yads = i - halfopt;
				Rectangle rect = new Rectangle(xloc, ycen + yads * height,
					width, height);
				lbls[i].set_bounds(rect);
			}
		}

		for(int i = 0; i < lbls.length; i++){
			if(i == selected){
				lbls[i].set_bg(Color.BLUE);
			}else{
				lbls[i].set_bg(clear);
			}
			lbls[i].draw(gd);
		}
		win.swap();
	}

	@Override
	public void update(long delay){

	}

	@Override
	public void proccess_event(MMWindow win){
		KeyEvent k = win.get_last_event();
		if(k == null){
			return;
		}
		switch(k.getKeyCode()){
			case KeyEvent.VK_UP:
			selected = selected == 0 ? 0 : selected - 1;
				break;
			case KeyEvent.VK_DOWN:
			selected = selected == options.length - 1 ? selected : selected + 1;
				break;
			case KeyEvent.VK_ENTER:
			opt_act[selected].run();
				break;
		}
	}

}
