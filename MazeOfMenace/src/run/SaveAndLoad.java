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
package run;
import java.io.*;

import run.input.GameState;
import dungeon.Dungeon;
/**
 * @author Matthew Gruda
 *
 */
public final class SaveAndLoad {
	
	/**
	 * location of the save
	 */
	public final static String saveLoc = "save/save.sav";
	
	private SaveAndLoad(){}

	/**
	 * determins if a save exists
	 * @return
	 */
	public static boolean isSave(){
		return new File(saveLoc).isFile();
	}
	/**
	 * loads a save game, will exit with one if there is a loading
	 * problem, but not a missing file
	 */
	public static void loadSave(){
		try{
			FileInputStream f  = new FileInputStream(saveLoc);
			ObjectInputStream dgnStrm = new ObjectInputStream(f);
			Init.setDgn((Dungeon)dgnStrm.readObject());
			dgnStrm.close();
		} catch (IOException e){
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	/**
	 * Will delete a previous save, and save the game
	 * then quit. error exits with 2.
	 */
	public static void save(){
		try{
			if (isSave()){
				new File(saveLoc).delete();
			} 
			File f = new File(saveLoc);
			f.createNewFile(); 
			FileOutputStream fOut = new FileOutputStream(saveLoc);
			ObjectOutputStream OOut = new ObjectOutputStream(fOut);
			OOut.writeObject(Init.getDungeon());
			OOut.close();
			Init.terminal.exitPrivateMode();
		} catch (Exception e){
			e.printStackTrace();
			System.exit(2); 
		}
	}
	
	private static void removeSave(){
		if (isSave()){
			new File(saveLoc).delete();
		} 
	}
	
	/**
	 * restarts the game with a new dungeon
	 */
	public static void restart() {
		removeSave();
		Init.setState(GameState.START);
		Startup.printStartupMsg();
	}
}
