/*******************************************************************************
 * Copyright (c) 2012, 2013 Matthew Gruda
 * 
 *    This file is part of Maze Of Menace.
 * 
 *     Maze Of Menace is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     Maze Of Menace is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *     All rights reserved. This program and the accompanying materials
 *     are made available under the terms of the GNU Public License v3.0
 *     which accompanies this distribution, and is available at
 *     http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Matthew Gruda- initial API and implementation
 ******************************************************************************/
/**
 * 
 */
package dungeon.tile;

import items.AbstractItem;

import java.awt.Color;
import java.awt.Image;
import java.io.Serializable;

import run.*;

/**
 * @author Matthew Gruda all mons, player & items extend this provides a
 *         location for objects and a default draw method for extending objects
 *         to overwrite also provides a move method
 */
public abstract class AbstractObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Location loc;
	private int z;

	/**
	 * initalizes location and adds it to the tile objects
	 */
	public AbstractObject(int x, int y, int z) {
		loc = new Location(x, y);
		// Init.getDungeon().getLevel(z).getlvl()[x][y].addObject(this);
		this.setZ(z);
	}

	public AbstractObject() {
	}

	/**
	 * sets the location of obj, and depth. note: this should never be used
	 * alone to set the location, as it will case problems with object being on
	 * two tiles at once, object needs to update currEntity, or the objects
	 * arraylist on tiles
	 * 
	 * @param x
	 *            = x coord
	 * @param y
	 *            = y coord
	 * @param z
	 *            = depth
	 */
	public void setLoc(int x, int y) {
		loc.setX(x);
		loc.setY(y);
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public int getX() {
		return loc.getX();
	}

	public int getY() {
		return loc.getY();
	}

	public Location getLoc() {
		return loc;
	}

	/**
	 * the default draw method meant to be overrode
	 * 
	 * @param x
	 * @param y
	 */
	public abstract char draw();

	/**
	 * @return the z
	 */
	public int getZ() {
		return z;
	}
	
	public Image getImage(){
		return null;
	}
	
	public abstract Color getColor();

	/**
	 * @param z
	 *            the z to set
	 */
	public void setZ(int z) {
		this.z = z;
	}
	
	public abstract Image getfIm();
}
