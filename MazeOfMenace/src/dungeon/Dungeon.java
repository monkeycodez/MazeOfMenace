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
package dungeon;

import java.util.*;
import generator.*;
import entity.player.*;
import run.Util;
import run.input.GameState;
import terminal.FancyImageBuffer;

import java.io.*;

/**
 * @author Matthew Gruda will hold every level
 */
public class Dungeon implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * literaly holds every object in the dungeon player object here for ez
	 * reference
	 */
	private ArrayList<Level> everything = new ArrayList<Level>();
	private int currentLvl, maxLvl;
	private Player player;

	/**
	 * set up moved to setUpDungeon() to avoid endless NPEs
	 */
	public Dungeon() {
	}

	/**
	 * must be called to initalize dungon
	 */
	public void setUpDungeon() {
		// everything starts at 0 :-)
		maxLvl = 0;
		makeNewLevel();
		currentLvl = 0;
		GeneratePlayer.generatePlayer(everything.get(0));
	}

	/**
	 * makes a new level. calls generateLevel() for design and
	 * generateStartMonster() for monsters
	 */
	private void makeNewLevel() {
		
		everything.add(new Level(maxLvl));
		GenerateLevel.generateLevel(everything.get(everything.size()-1));
		GenerateMonster
				.generateStartMonster(everything.get(everything.size() - 1));
	}

	/**
	 * draws the current dungeon level calls draw() method in level
	 */
	public void draw() {
		FancyImageBuffer.clearPics();
		this.player.makeLOS();
		everything.get(currentLvl).draw();
		everything.get(currentLvl).drawf();
		Display.display();
	}

	/**
	 * @return the level that the player is currently on
	 */
	public Level getCurrentLevelObj() {
		return everything.get(currentLvl);
	}

	/**
	 * moves down one level. currentLvl changed and new level added if needed
	 */
	public void levelUp() {
		System.out.println("Lvl up");
		currentLvl++;
		if (currentLvl > maxLvl) {
			maxLvl++;
			makeNewLevel();
		}
		System.out.println("Current lvl:"+currentLvl);
		player.setZ(currentLvl);
	}

	/**
	 * moves player up 1 level. call to kill() if level = -1
	 * 
	 * @return
	 */
	public boolean levelDown() {
		currentLvl--;
		if (currentLvl == -1) {
			if(!player.hasOrb()){
				Util.kill("You escaped the dungeon\n(what a wimp)", false);
				return false;
			}
			Util.kill("dat/winMsg", true);
			return false;
		}
		player.setZ(currentLvl);
		return true;
	}

	/**
	 * 
	 * @param z
	 *            level to get
	 * @return specified level object
	 */
	public Level getLevel(int z) {
		return everything.get(z);
	}

	/**
	 * sets the player
	 * 
	 * @param player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the player object
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * removes all instances of player in currEntitys
	 */
	public void purgePlayer() {
		for (int i = 0; i < everything.size(); i++) {
			everything.get(i).purgePlayer();
		}
	}
}
