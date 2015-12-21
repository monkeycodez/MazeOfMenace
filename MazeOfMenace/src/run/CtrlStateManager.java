package run;

import java.util.LinkedList;
import run.gamestate.DrawControl;
import run.gamestate.GameControl;
import run.gamestate.InputControl;
import run.gamestate.UpdateControl;

public class CtrlStateManager{

	private LinkedList<DrawControl> dl = new LinkedList<>();

	private LinkedList<UpdateControl> uc = new LinkedList<>();

	private LinkedList<InputControl> ic = new LinkedList<>();

	public CtrlStateManager(InputControl inc, UpdateControl upc,
		DrawControl drc) {
		ic.push(inc);
		uc.push(upc);
		dl.push(drc);
	}

	public CtrlStateManager(GameControl ctrl) {
		push_all(ctrl);
	}

	public InputControl get_ic(){
		return ic.getFirst();
	}

	public UpdateControl get_uc(){
		return uc.getFirst();
	}

	public DrawControl get_dc(){
		return dl.getFirst();
	}

	public void push_ic( InputControl ict ){
		ic.push(ict);
	}

	public void push_uc( UpdateControl ict ){
		uc.push(ict);
	}

	public void push_dc( DrawControl ict ){
		dl.push(ict);
	}

	public void push_all( GameControl gc ){
		if(gc == null){
			return;
		}
		push_ic(gc);
		push_dc(gc);
		push_uc(gc);
	}

	public void pop_ic(){
		ic.pop();
	}

	public void pop_uc(){
		uc.pop();
	}

	public void pop_dc(){
		dl.pop();
	}

	public void flush_all(){
		dl.clear();
		uc.clear();
		ic.clear();
	}

}
