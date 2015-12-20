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

	public boolean move( Tile to ){
		if(to == null){
			return false;
		}
		if(at.getLat().getDepth() != to.getLat().getDepth()){
			at.getLat().remove_updateable(update);
			to.getLat().add_updateable(update);
			//TODO post lvl change update
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

	public boolean can_move_north(){
		Tile north = at.north();
		if(north == null){
			return false;
		}

		if(north.is_solid()){
			return false;
		}
		if(north.can_walk() && stat.can_walk){
			return true;
		}
		if(north.is_water() && stat.can_swim){
			return true;
		}
		return false;
	}

	public boolean can_move_west(){
		Tile west = at.west();
		if(west == null){
			return false;
		}
		if(west.is_solid()){
			return false;
		}
		if(west.can_walk() && stat.can_walk){
			return true;
		}
		if(west.is_water() && stat.can_swim){
			return true;
		}
		return false;
	}

	public boolean can_move_east(){
		Tile east = at.east();
		if(east == null){
			return false;
		}

		if(east.is_solid()){
			return false;
		}
		if(east.can_walk() && stat.can_walk){
			return true;
		}
		if(east.is_water() && stat.can_swim){
			return true;
		}
		return false;
	}

	public boolean can_move_south(){
		Tile south = at.south();
		if(south == null){
			return false;
		}
		if(south.is_solid()){
			return false;
		}
		if(south.can_walk() && stat.can_walk){
			return true;
		}
		if(south.is_water() && stat.can_swim){
			return true;
		}
		return false;
	}

}
