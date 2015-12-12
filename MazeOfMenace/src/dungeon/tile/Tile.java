/*******************************************************************************
 * Copyright (c) 2012, 2013 Matthew Gruda
 * 
 * This file is part of Maze Of Menace.
 * 
 * Maze Of Menace is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Maze Of Menace is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 * Matthew Gruda- initial API and implementation
 ******************************************************************************/
package dungeon.tile;

import java.awt.Image;
import java.io.Serializable;
import java.util.LinkedList;
import terminal.FancyImageBuffer;
import dungeon.Level;

/**
 * 
 * @author Matthew Gruda. this will contain everything on tile the basetype
 *         (floor/wall) in basetile & mons, player, items in objects
 * 
 */

/**
 * @author matthew
 * 
 */
public class Tile implements Serializable{

	private DrawComponent dr = new DrawComponent(this);

	public DrawComponent getDcomp(){
		return dr;
	}

	private LinkedList<AbstractObject> objs = new LinkedList<>();

	private final int x, y;

	private final Level at = null;

	//OLD STUFF

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BaseTileType basetile;

	private boolean seen, inView;

	public boolean genFlag1;

	public Tile(int x, int y, BaseTileType b) {
		this.x = x;
		this.y = y;
		seen = false;
		inView = false;
		basetile = b;
		genFlag1 = false;
	}

	private void drawf( Image i, int x, int y ){
		FancyImageBuffer.buffer.addImage(x, y, i);
	}

	public BaseTileType getBasetile(){
		return basetile;
	}

	/**
	 * some setters & getters
	 * 
	 * @return
	 */
	public boolean isSeen(){
		return seen;
	}

	public void setSeen( boolean seen ){
		this.seen = seen;
	}

	public boolean isInView(){
		return inView;
	}

	public void setInView( boolean inView ){
		this.inView = inView;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public Level getLat(){
		return at;
	}

}
