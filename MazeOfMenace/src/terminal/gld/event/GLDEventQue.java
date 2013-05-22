package terminal.gld.event;

import java.util.*;


public class GLDEventQue {

	private ArrayList<GLDEvent> objs = new ArrayList<GLDEvent>();
	
	public GLDEventQue(){
		
	}
	
	public void put(GLDEvent e){
		objs.add(e);
	}
	
	public GLDEvent get(){
		return objs.remove(0);
	}

	public boolean hasNext(){
		return !objs.isEmpty();
	}
}
