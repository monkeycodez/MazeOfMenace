package run;

import java.util.LinkedList;
import run.gamestate.*;

public class CtrlStateManager{

	private static LinkedList<DrawControl> dl = new LinkedList<>();

	private static LinkedList<UpdateControl> uc = new LinkedList<>();

	private static LinkedList<InputControl> ic = new LinkedList<>();

	public static void init_mgr(
		InputControl inc,
		UpdateControl upc,
		DrawControl drc){
		ic.push(inc);
		uc.push(upc);
		dl.push(drc);
	}

	public static void init_mgr(GameControl ctrl){
		push_all(ctrl);
	}

	public static InputControl get_ic(){
		return ic.getFirst();
	}

	public static UpdateControl get_uc(){
		return uc.getFirst();
	}

	public static DrawControl get_dc(){
		return dl.getFirst();
	}

	public static void push_ic(InputControl ict){
		ic.push(ict);
	}

	public static void push_uc(UpdateControl ict){
		uc.push(ict);
	}

	public static void push_dc(DrawControl ict){
		dl.push(ict);
	}

	public static void push_all(GameControl gc){
		if(gc == null){
			return;
		}
		push_ic(gc);
		push_dc(gc);
		push_uc(gc);
	}

	public static void pop_ic(){
		ic.pop();
	}

	public static void pop_uc(){
		uc.pop();
	}

	public static void pop_dc(){
		dl.pop();
	}

	public static void flush_all(){
		dl.clear();
		uc.clear();
		ic.clear();
	}

}
