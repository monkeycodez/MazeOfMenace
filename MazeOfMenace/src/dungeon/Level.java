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
import dungeon.tile.Wall;
import entity.mons.GeneralMonster;
import generator.GenerateMonster;

/**
 * @author Matthew Gruda will hold each individual level
 */

public class Level implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * to simplify things, these will be public final. as objects their
	 * refrences will not be able to be changes, but methods will act upon them
	 * 
	 * lvl - holds everything in the level, monsters, tiles playes, ect monsters
	 * - holds monsters for easier refrence
	 * 
	 */
	private Tile[][] lvl = new Tile[64][32];

	public final ArrayList<GeneralMonster> monsters = new ArrayList<GeneralMonster>(
		0);

	public final int depth;

	private int monsCount = 0;

	private int upStrX, upStrY, dwStrX, dwStrY;

	/**
	 * depth will determine fixed or random level and monster difficulty
	 * GenerateLevel.generateLevel(this) or simiar
	 * 
	 * method will be called to make level
	 */
	public Level(int depth) {
		this.depth = depth;
		preProccess(this);
	}

	public Tile getlvl()[][]{
		return lvl;
	}

	/**
	 * makes all tiles walls should be called first to prevent level gen
	 * accident (sides end in wall, ect.)
	 * 
	 * @param lvl
	 *            = the level to operate on
	 */
	private static void preProccess( Level lvl ){
		for(int i = 0; i < 64; i++){
			for(int c = 0; c < 32; c++){
				lvl.lvl[i][c] = new Tile(i, c, new Wall());
			}
		}
	}

	/**
	 * hides all tiles. used by makeLOS in Player used for makeing tiles out of
	 * veiw
	 */
	public void hide(){
		for(int i = 0; i < 64; i++){
			for(int c = 0; c < 32; c++){
				lvl[i][c].setInView(false);
			}
		}
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

	public void dynMonGen(){
		monsCount++;
		if(monsCount == 10){
			monsCount = 0;
			GenerateMonster.generateDynamicMonster(this);
		}
	}
}
