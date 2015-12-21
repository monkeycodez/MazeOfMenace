package run.gamestate.mmenu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import run.CtrlStateManager;
import run.gamestate.GameControl;
import run.gamestate.dgn.DgnDrawMngr;
import run.gamestate.dgn.DgnInCtrl;
import run.gamestate.dgn.DgnUpdater;
import window.MMWindow;
import window.widget.Label;
import dungeon.Dungeon;

public class MainMenu implements GameControl{

	private String[] options = {
			"Start",
			"Exit"
	};

	private Runnable[] opt_act = new Runnable[]{
			new Runnable(){

				@Override
				public void run(){
					Dungeon d = new Dungeon();
					d.setUpDungeon();
					mgr.flush_all();
					mgr.push_ic(new DgnInCtrl(d));
					mgr.push_uc(new DgnUpdater(d));
					mgr.push_dc(new DgnDrawMngr(d));
				}

			},
			new Runnable(){

				@Override
				public void run(){
					System.exit(0);
				}

			}
	};

	private Font ofont = new Font("Monospaced", Font.BOLD, 20);

	private Label[] lbls = new Label[]{
			new Label(
				options[0],
				ofont,
				null,
				Color.WHITE,
				new Color(0, 0, 0, 0)),
			new Label(
				options[1],
				ofont,
				null,
				Color.WHITE,
				new Color(0, 0, 0, 0))
	};

	private CtrlStateManager mgr;

	public MainMenu(CtrlStateManager mgr) {
		this.mgr = mgr;
	}

	public MainMenu(CtrlStateManager mgr, String ops[], Runnable opa[]) {
		options = ops;
		opt_act = opa;
		assert (ops.length == opa.length);
		lbls = new Label[opa.length];
		for(int i = 0; i < ops.length; i++){
			lbls[i] = new Label(
				options[i],
				ofont,
				null,
				Color.WHITE,
				new Color(0, 0, 0, 0));
		}
	}

	private int selected = 0;

	private boolean sized = false;

	private Color clear = new Color(0, 0, 0, 0);

	@Override
	public void draw( MMWindow win ){
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
				Rectangle rect = new Rectangle(xloc, ycen +
					yads * height, width, height);
				lbls[i].set_box(rect);
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
	public void update( long delay ){

	}

	@Override
	public void proccess_event( MMWindow win ){
		KeyEvent k = win.get_last_event();
		if(k == null){
			return;
		}
		switch(k.getKeyCode()){
			case KeyEvent.VK_UP:
				selected = selected == 0 ? 0 : selected - 1;
				break;
			case KeyEvent.VK_DOWN:
				selected = selected == options.length - 1
					? selected : selected + 1;
				break;
			case KeyEvent.VK_ENTER:
				opt_act[selected].run();
				break;
		}
	}

}
