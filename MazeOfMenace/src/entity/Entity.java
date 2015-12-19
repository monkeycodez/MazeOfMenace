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

import java.io.Serializable;
import render.draw.DrawComponent;
import dungeon.Level;
import dungeon.tile.Tile;

/**
 * monsters and player extend this provides basic movement with move()
 * 
 * @author matthew
 * 
 */
public abstract class Entity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private StatComponent stat = null;

	private DrawComponent draw = null;

	private Tile at;

	public Entity(Tile t, StatComponent stat, DrawComponent d) {
		this.stat = stat;
		at = t;
		at.set_e_at(this);
		draw = d;
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
		at.set_e_at(null);
		to.set_e_at(this);
		at = to;
		return true;
	}

	public int getDefense(){
		return stat.getDefense();
	}

	public void setDefense( int defense ){
		stat.setDefense(defense);
	}

	public int getAttack(){
		return stat.getAttack();
	}

	public void setAttack( int attack ){
		stat.setAttack(attack);
	}

	public int getHitchance(){
		return stat.getHitchance();
	}

	public void setHitchance( int hitchance ){
		stat.setHitchance(hitchance);
	}

	public int getHp(){
		return stat.getHp();
	}

	public void setHp( int hp ){
		stat.setHp(hp);
	}

	public int getHpmax(){
		return stat.getHpmax();
	}

	public void setHpmax( int hpmax ){
		stat.setHpmax(hpmax);
	}

}
