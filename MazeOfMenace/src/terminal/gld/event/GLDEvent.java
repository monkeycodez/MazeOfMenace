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

import entity.Entity;

public interface GLDEvent {

	public void applyPLocStart();
	public void applyPLocEnd();
	
	public boolean applyEntChStart(Entity e);
	public boolean applyEnrChEnd(Entity e);
	
	public void drawFinalChange();
	
	public boolean done();
	
}
