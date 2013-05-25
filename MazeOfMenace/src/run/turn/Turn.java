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
package run.turn;

import java.util.*;
import run.Init;
import run.input.GameState;
import entity.*;
import entity.player.Display;

/**
 * entitys will be added to the que, then sorted by speed, and then sent the
 * act() command
 * 
 * @author matthew
 * 
 */
public final class Turn {

	private static ArrayList<Entity> turnQue = new ArrayList<Entity>();

	/**
	 * Entitys MUST call this to be able to move
	 * 
	 * @param e
	 *            Entity to add to the que
	 */
	public static void addToTurnQue(Entity e) {
		turnQue.add(e);
	}

	/**
	 * sorts the que according to speed, lower is better
	 */
	private static Entity[] sortQue() {
		Entity[] e = turnQue.toArray(new Entity[turnQue.size()]);
		for (int n = e.length; n > 1; n--) {
			int iMax = 0;
			for (int i = 1; i < n; i++) {
				if (e[i].getSpeed() > e[iMax].getSpeed())
					iMax = i;
			}
			Entity aTemp = e[iMax];
			 e[iMax] = e[n-1];
		      e[n-1] = aTemp;
		}
		return e;
	}

	/**
	 * adds monsters to que by calling their queMove() method
	 */
	private static void monsMove() {
		for (int c = 0; c < Init.getDungeon().getCurrentLevelObj().monsters
				.size(); c++) {
			Init.getDungeon().getCurrentLevelObj().monsters.get(c).queMove();
		}

	}
	

	/**
	 * manages each "turn". player && monsters are added to turn que, sorted 
	 * according so speed, and then their act() methods are called
	 */
	public static void turn() {
		Display.flush();
		Init.getDungeon().getPlayer().regenControl();
		Init.getDungeon().getPlayer().checkDeath(Init.getDungeon().getPlayer());
		monsMove();
		Entity[] e = sortQue();
		for (Entity ent: e) {
			ent.act();
		}
		turnQue = null;
		turnQue = new ArrayList<Entity>();
		if (Init.getState() == GameState.DEAD)
			return;
		Init.terminal.clearScreen();
		Init.getDungeon().draw();
	}
}
