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
package generator;

import entity.player.*;
import java.util.*;
import dungeon.Level;
import dungeon.tile.*;
import run.Init;

/**
 * static methods for player generation should only be called by dungeon during
 * construction
 * 
 * @author matthew
 * 
 */
public final class GeneratePlayer {

	/**
	 * generates a player, placing it at the up stair location; eventually
	 * make diffrent player classes: fighter, wizard, ect.
	 */
	public static void generatePlayer(Level lvl) {
		for (int x = 0; x < 64; x++) {
			for (int y = 0; y < 32; y++) {
				if(lvl.getlvl()[x][y].getBasetile() instanceof StairUp)
				Init.getDungeon()
						.setPlayer(new Player(x, y, 0));
			}
		}
		assert Init.getDungeon().getPlayer() != null : "could not place player";
	}

}
