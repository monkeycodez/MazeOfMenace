package window.widget;

import java.awt.*;

public class WMenu extends WComp{

	private Runnable opa[];

	private Label[] lbls;

	private int selected = 0;

	public WMenu(String ops[], Runnable opa[], Font fnt, FontMetrics fm) {
		assert (ops.length == opa.length);
		this.opa = opa;
		lbls = new Label[opa.length];
		for(int i = 0; i < opa.length; i++){
			lbls[i] = new Label(ops[i], fnt, fm);
		}
		super.add_childeren(lbls);
	}

	@Override
	public void drawComp(Graphics2D gd){
		for(Label l : lbls){
			l.set_bg(Color.BLACK);
		}
		lbls[selected].set_bg(Color.BLUE);
	}

	public void select(int num){
		if(num < 0 || num > opa.length){
			return;
		}
		selected = num;
	}

	public void run_sel(){
		opa[selected].run();
	}

	public int get_sel(){
		return selected;
	}

	@Override
	public Dimension get_pref_min_size(){
		int h = 0, w = 0;
		for(Label l : lbls){
			Dimension d = l.get_pref_min_size();
			h += d.height;
			if(d.width > w){
				w = d.width;
			}
		}
		return new Dimension(w + 40, h);
	}

	@Override
	public void set_size(int w,int h){
		for(Label l : lbls){
			l.set_size(w, h / lbls.length);
		}
		super.set_size(w, h);
	}

}
