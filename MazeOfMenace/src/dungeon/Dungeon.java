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
import entity.player.Player;
import generator.ClassicLevelGen;
import generator.GeneratePlayer;
import generator.LevelGenStrategy;

/**
 * @author Matthew Gruda will hold every level
 */
public class Dungeon implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<Level> llist = new ArrayList<Level>();

	private Player player;

	private int maxlvl = 0;

	private LevelGenStrategy getstr = new ClassicLevelGen();

	/**
	 * set up moved to setUpDungeon() to avoid endless NPEs
	 */
	public Dungeon() {
	}

	/**
	 * must be called to initalize dungon
	 */
	public void setUpDungeon(){
		makeNewLevel();
		GeneratePlayer.generatePlayer(this, getLevel(0));
	}

	/**
	 * makes a new level. calls generateLevel() for design and
	 * generateStartMonster() for monsters
	 */
	public Level makeNewLevel(){
		Level n = getstr.form_dungeon(this, maxlvl++);
		llist.add(n);
		return n;
	}

	/**
	 * @return the level that the player is currently on
	 */
	public Level getCurrentLevelObj(){
		return player.getLOn();
	}

	/**
	 * 
	 * @param z
	 *            level to get
	 * @return specified level object
	 */
	public Level getLevel( int z ){
		return llist.get(z);
	}

	/**
	 * sets the player
	 * 
	 * @param player
	 */
	public void setPlayer( Player player ){
		this.player = player;
	}

	/**
	 * @return the player object
	 */
	public Player getPlayer(){
		return player;
	}
}
