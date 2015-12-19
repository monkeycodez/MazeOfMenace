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

import java.io.Serializable;
import java.util.ArrayList;
import dungeon.tile.Tile;
import dungeon.tile.TileTemplate;
import entity.mons.GeneralMonster;

/**
 * @author Matthew Gruda will hold each individual level
 */

public class Level implements Serializable{

	private static final long serialVersionUID = 1L;

	private Dungeon from;

	private Tile[][] lvl = new Tile[64][32];

	public final ArrayList<GeneralMonster> monsters =
		new ArrayList<GeneralMonster>(0);

	public final int depth;

	private int upStrX, upStrY, dwStrX, dwStrY;

	public Level(int depth, Dungeon from, TileTemplate[][] map) {
		this.depth = depth;
		this.from = from;
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

	/**
		 * @return the depth
		 */
	public int getDepth(){
		return depth;
	}

	/**
	 * @return the upStrX
	 */
	public int getUpStrX(){
		return upStrX;
	}

	/**
	 * @param upStrX
	 *            the upStrX to set
	 */
	public void setUpStrX( int upStrX ){
		this.upStrX = upStrX;
	}

	/**
	 * @return the upStrY
	 */
	public int getUpStrY(){
		return upStrY;
	}

	/**
	 * @param upStrY
	 *            the upStrY to set
	 */
	public void setUpStrY( int upStrY ){
		this.upStrY = upStrY;
	}

	/**
	 * @return the dwStrX
	 */
	public int getDwStrX(){
		return dwStrX;
	}

	/**
	 * @param dwStrX
	 *            the dwStrX to set
	 */
	public void setDwStrX( int dwStrX ){
		this.dwStrX = dwStrX;
	}

	/**
	 * @return the dwStrY
	 */
	public int getDwStrY(){
		return dwStrY;
	}

	/**
	 * @param dwStrY
	 *            the dwStrY to set
	 */
	public void setDwStrY( int dwStrY ){
		this.dwStrY = dwStrY;
	}

	public int xlen(){
		return lvl.length;
	}

	public int ylen(){
		return lvl[0].length;
	}
}
