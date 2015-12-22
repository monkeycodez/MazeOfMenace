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

import items.AbstractItem;
import java.util.LinkedList;
import render.draw.DrawComponent;
import dungeon.Level;
import dungeon.tile.template.TileInteract;
import entity.Entity;

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
public class Tile{

	private DrawComponent dr;

	public DrawComponent getDcomp(){
		return dr;
	}

	private LinkedList<AbstractItem> objs = new LinkedList<>();

	private final int x, y;

	private Level at;

	private Entity e_at;

	private TileInteract inter;

	private TileTemplate from;

	public boolean genFlag1;

	public Tile(int x, int y, TileTemplate t, Level at) {
		from = t;
		dr = from.create_new_draw_component();
		inter = t.create_new_interaction(this);
		this.x = x;
		this.y = y;
		this.at = at;
	}

	public TileTemplate getTemp(){
		return from;
	}

	public void setTemp( TileTemplate t ){
		from = t;
		dr = from.create_new_draw_component();
		inter = t.create_new_interaction(this);
	}

	public boolean is_neighbor( Tile itr ){
		if(itr == north()){
			return true;
		}
		if(itr == east()){
			return true;
		}
		if(itr == south()){
			return true;
		}
		if(itr == west()){
			return true;
		}
		if(itr == ne()){
			return true;
		}
		if(itr == se()){
			return true;
		}
		if(itr == nw()){
			return true;
		}
		if(itr == sw()){
			return true;
		}

		return false;
	}

	public TileInteract getInteract(){
		return inter;
	}

	public boolean is_solid(){
		return from.is_solid();
	}

	public boolean is_water(){
		return from.is_water();
	}

	public int getType(){
		return from.type();
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public boolean can_walk(){
		return from.can_walk();
	}

	public Level getLat(){
		return at;
	}

	public Entity getCurrEntity(){
		return e_at;
	}

	public void set_e_at( Entity e ){
		e_at = e;
	}

	public void setObject( AbstractItem o ){
		objs.add(o);
	}

	public Tile north(){
		return at.getT(x, y - 1);
	}

	public Tile south(){
		return at.getT(x, y + 1);
	}

	public Tile east(){
		return at.getT(x + 1, y);
	}

	public Tile west(){
		return at.getT(x - 1, y);
	}

	public Tile ne(){
		return at.getT(x + 1, y - 1);
	}

	public Tile nw(){
		return at.getT(x - 1, y - 1);
	}

	public Tile sw(){
		return at.getT(x - 1, y + 1);
	}

	public Tile se(){
		return at.getT(x + 1, y + 1);
	}

}
