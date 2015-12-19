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
package dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dungeon.tile.Tile;
import dungeon.tile.TileTemplate;
import entity.Updateable;

/**
 * @author Matthew Gruda will hold each individual level
 */

public class Level{

	private Dungeon from;

	private Tile[][] lvl;

	private final List<Updateable> ents =
		new ArrayList<>(0);

	private final int depth;

	private Map<String, Tile> connections;

	public Level(int depth, Dungeon from, TileTemplate[][] map) {
		this.depth = depth;
		this.from = from;
		connections = new HashMap<>();
		lvl = new Tile[map.length][map[0].length];
		for(int x = 0; x < map.length; x++){
			for(int y = 0; y < map[0].length; y++){
				lvl[x][y] = new Tile(x, y, map[x][y], this);
			}
		}
	}

	public Tile getT( int x, int y ){
		if(x < 0 || x >= lvl.length){
			return null;
		}
		if(y < 0 || y >= lvl[0].length){
			return null;
		}
		return lvl[x][y];
	}

	public Dungeon get_from(){
		return from;
	}

	public void set_connection( int x, int y, String name ){
		connections.put(name, getT(x, y));
	}

	public Tile get_connection( String name ){
		return connections.get(name);
	}

	/**
	 * @return the depth
	 */
	public int getDepth(){
		return depth;
	}

	public int xlen(){
		return lvl.length;
	}

	public int ylen(){
		return lvl[0].length;
	}

	public void add_updateable( Updateable up ){
		if(up != null){
			ents.add(up);
		}
	}

	public void remove_updateable( Updateable up ){
		ents.remove(up);
	}

	public List<Updateable> get_updates(){
		return ents;
	}
}
