/*******************************************************************************
 * Copyright (c) 2013 Matthew Gruda.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     matthew - initial API and implementation
 ******************************************************************************/
package terminal.gld.event;

import java.util.*;


public class GLDEventQue {

	private ArrayList<GLDEvent> objs = new ArrayList<GLDEvent>();
	
	public GLDEventQue(){}
	
	public void put(GLDEvent e){
		objs.add(e);
	}
	
	public GLDEvent get(){
		return objs.remove(0);
	}

	public boolean hasNext(){
		return !objs.isEmpty();
	}
	
	public int size(){
		return objs.size();
	}
}
