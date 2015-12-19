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
/**
 * 
 */
package entity;

import render.draw.DrawComponent;
import dungeon.Level;
import dungeon.tile.Tile;

/**
 * monsters and player extend this provides basic movement with move()
 * 
 * @author matthew
 * 
 */
public abstract class Entity{

	private StatComponent stat = null;

	private DrawComponent draw = null;

	private Updateable update;

	private Tile at;

	public Entity(Tile t, StatComponent stat, DrawComponent d,
		Updateable ups) {
		setStat(stat);
		at = t;
		at.set_e_at(this);
		draw = d;
		update = ups;
		at.getLat().add_updateable(update);
	}

	public DrawComponent getDraw(){
		return draw;
	}

	public Tile getLoc(){
		return at;
	}

	public Level getLOn(){
		return at.getLat();
	}

	public boolean can_move( Tile t ){
		if(t == null){
			return false;
		}
		return true;
	}

	public boolean move( Tile to ){
		if(!can_move(to)){
			return false;
		}
		if(at.getLat().getDepth() != to.getLat().getDepth()){
			at.getLat().remove_updateable(update);
			to.getLat().add_updateable(update);
		}
		at.set_e_at(null);
		to.set_e_at(this);
		at = to;
		return true;
	}

	public Updateable getUpdate(){
		return update;
	}

	public void setUpdate( Updateable update ){
		at.getLat().remove_updateable(this.update);
		this.update = update;
		at.getLat().add_updateable(update);
	}

	public StatComponent getStat(){
		return stat;
	}

	public void setStat( StatComponent stat ){
		this.stat = stat;
	}

}
