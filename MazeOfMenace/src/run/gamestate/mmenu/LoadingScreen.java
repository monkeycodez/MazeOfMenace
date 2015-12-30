package run.gamestate.mmenu;

import java.awt.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import run.CtrlStateManager;
import run.gamestate.GameControl;
import util.Utils;
import window.MMWindow;

public class LoadingScreen<V> implements GameControl{

	private CtrlStateManager mgr;

	private Future<V> checker;

	private Consumer<V> atend;

	private Callable<V> td;

	public LoadingScreen(CtrlStateManager mgr) {
		this(mgr, null, null);
	}

	public LoadingScreen(CtrlStateManager mgr, Callable<V> todo,
		Consumer<V> atexit) {
		this.mgr = mgr;
		td = todo;
		atend = atexit;

	}

	public void start(){
		checker = Utils.bgthread.submit(td);
		mgr.push_all(this);
	}

	@Override
	public void proccess_event(MMWindow win){
	}

	@Override
	public void update(long delay){
		if(checker.isDone() || checker.isCancelled()){
			mgr.pop_dc();
			mgr.pop_ic();
			mgr.pop_uc();
			try{
				atend.accept(checker.get());
			}catch(InterruptedException e){
				e.printStackTrace();
			}catch(ExecutionException e){
				e.printStackTrace();
			}
		}
	}

	private boolean dirty = true;

	@Override
	public void draw(MMWindow win){
		if(!dirty){
			return;
		}
		Graphics2D gd = win.get_graphics();
		Dimension d = win.get_size();
		gd.setColor(Color.BLACK);
		gd.fillRect(0, 0, d.width, d.height);
		gd.setColor(Color.WHITE);
		Font f = new Font("Monospaced", 0, 20);
		FontMetrics fm = gd.getFontMetrics(f);
		gd.setFont(f);
		String write = "Loading...";
		int len = fm.stringWidth(write);
		gd.drawString(write, d.width / 2 - len / 2, d.height / 2);
		win.swap();
		dirty = true;
	}

	public static <V> void do_loading_screen(
		CtrlStateManager mgr,
		Callable<V> todo,
		Consumer<V> atexit){
		LoadingScreen<V> l = new LoadingScreen<>(mgr, todo, atexit);
		l.start();
	}

	public Callable<V> getToDo(){
		return td;
	}

	public void setToDo(Callable<V> td){
		this.td = td;
	}

	public Consumer<V> getAtEnd(){
		return atend;
	}

	public void setAtEnd(Consumer<V> atend){
		this.atend = atend;
	}

}
