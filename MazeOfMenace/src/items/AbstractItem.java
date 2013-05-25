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
package items;

import java.awt.Image;

import run.Init;
import dungeon.tile.AbstractObject;
import dungeon.tile.Location;
import engine.image.TextureDB;

public abstract class AbstractItem extends AbstractObject {

	@Override
	public Image getfIm() {
		// TODO Auto-generated method stub
		return null;
	}

	private static final long serialVersionUID = 1L;
	private boolean onFloor = true;
	
	public AbstractItem(int x, int y, int z) {
		super(x, y, z);
	}

	public AbstractItem() {	}

	public void pickUp(AbstractItem i){
		
	}
	
	public abstract String getName();
	
	/**
	 * 
	 * @param oldLoc
	 * @param newLoc
	 */
	public void setLoc(Location oldLoc, Location newLoc, AbstractItem obj){
		super.setLoc(newLoc);
		Init.getDungeon().getLevel(getZ()).getlvl()[oldLoc.getX()][oldLoc.getY()].removeObject(obj);
		Init.getDungeon().getLevel(getZ()).getlvl()[newLoc.getX()][newLoc.getY()].addObject(obj);
	}
	
	public void pickUp(){
		onFloor = false;
		Init.getDungeon().getLevel(getZ()).getlvl()[getX()][getY()].removeObject(null);
	}
	
	public int getTexId(){
		return TextureDB.getTexture(
				"./dat/tiles/fancy/entity/armor.png")
				.getTextureID();
	}
	
}
