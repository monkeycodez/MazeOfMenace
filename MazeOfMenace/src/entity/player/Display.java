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
package entity.player;

import run.Init;
import run.Util;

/**
 * to create a display with stats and events at the bottom of the screen
 * 
 * @author matthew
 * 
 */
public final class Display {

	private static String displayString = "";

	public static void display() {
		Init.terminal.moveCursor(1, 33);
		//Util.logDisplayMsg(displayString);
		Util.printStringWithLocNoCs(getCharInfoLine() + displayString,
				Init.terminal, 1, 33, 1);
	}

	private static String getCharInfoLine() {
		Player p = Init.getDungeon().getPlayer();
		return "Dgn Lvl: " + Init.getDungeon().getCurrentLevelObj().depth
				+ "  Lvl: " + p.getLevel() + "  Hp: `" + getHpChar()
				+ p.getHp() + "/" + p.getHpmax() + "`  speed: " + p.getSpeed()
				+ "\n";
	}
	
	public static String getDispLine(){
		return getCharInfoLine();
	}

	private static char getHpChar() {
		double hp = (double) Init.getDungeon().getPlayer().getHp()
				/ (double) Init.getDungeon().getPlayer().getHpmax();
		if (hp > .5)
			return 'g';
		else if (hp > .25)
			return 'o';
		return 'r';
	}

	public static void addMsg(String msg) {
		displayString += msg;
	}

	public static void flush() {
		displayString = "";
	}
}
