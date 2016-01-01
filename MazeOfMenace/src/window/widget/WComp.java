package window.widget;

import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class WComp{

	private Rectangle bounds;

	private List<WComp> kids = new LinkedList<>();

	public WComp() {
		bounds = new Rectangle();
	}

	public WComp(Rectangle bounds) {
		this.bounds = bounds;
	}

	public final void draw(Graphics2D gd){
		Graphics2D nd = (Graphics2D) gd.create();
		nd.translate(bounds.x, bounds.y);
		nd.clipRect(0, 0, bounds.width, bounds.height);
		drawComp(nd);
		for(WComp kid : kids){
			kid.draw(nd);
		}
	}

	public Dimension get_pref_min_size(){
		return new Dimension();
	}

	public List<WComp> get_c_list(){
		return kids;
	}

	public Rectangle get_bounds(){
		return bounds;
	}

	public void add_child(WComp c){
		kids.add(c);
	}

	public void add_childeren(WComp[] cls){
		for(WComp w : cls){
			add_child(w);
		}
	}

	public void set_bounds(Rectangle nbounds){
		bounds = nbounds;
	}

	public void set_location(int x,int y){
		bounds.x = x;
		bounds.y = y;
	}

	public void set_size(int w,int h){
		bounds.width = w;
		bounds.height = h;
	}

	public void set_size(Dimension d){
		set_size(d.width, d.height);
	}

	public abstract void drawComp(Graphics2D gd);

}
