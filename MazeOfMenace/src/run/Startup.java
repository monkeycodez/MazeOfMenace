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
package run;

import java.awt.event.KeyEvent;
import run.input.GameState;
import dungeon.Dungeon;

/**
 * this class runs the startup screen, manages save loading, and 
 * initalizes the dungeon if needed
 * 
 * @author Matthew Gruda
 *
 */
public final class Startup{

	/**
	 * location of startup message
	 */
	public static final String startUpMsgLocation = "dat/startUpMsg";

	/**
	 * this starts up the game main if space is pressed
	 * @param k keyEvent to parse
	 */
	public static void startManager( KeyEvent k ){
		int c = k.getKeyCode();
		if(c == KeyEvent.VK_SPACE){
			start();
		}
	}

	/**
	 * loads a save if applicable, sets the dungeon, and
	 * sets the game in normal mode
	 */
	public static void start(){
		if(SaveAndLoad.isSave()){
			SaveAndLoad.loadSave();
			Init.terminal.clearScreen();
			Init.getDungeon().draw();
			Init.setState(GameState.NORMAL);
			return;
		}
		Init.setDgn(new Dungeon());
		Init.getDungeon().setUpDungeon();
		Init.terminal.clearScreen();
		Init.getDungeon().draw();
		Init.setState(GameState.NORMAL);
	}

	/**
	 * This prints a friendly startup message from
	 * ./dat/startUpMsg
	 */
	public static void printStartupMsg(){
		String s = Util.getTxtMsg(startUpMsgLocation);
		Init.terminal.clearScreen();
		Util.printStringWithLocNoCs(s, Init.terminal, 3, 2, 1);
	}

}
