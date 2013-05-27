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

package run;

import java.util.Properties;
import org.lwjgl.LWJGLUtil;
import dungeon.Dungeon;
import run.input.GameState;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import terminal.*;

/**
 * contains the main method and some essental
 * objects and methods
 * 
 * @author Matthew Gruda
 * 
 */
public class Init {

	static {
		if (System.getProperty("runningInEclipse") == null)
			System.setProperty("org.lwjgl.librarypath", new File(
					System.getProperty("user.dir"),
					"natives").getAbsolutePath());
	}

	static {
		Settings.initSettings();
	}

	/**
	 * terminal is public to reduce complexity
	 */
	public static final Terminal terminal = Terminal.makeTerminal();

	protected static GameState state = GameState.START;

	private static Dungeon dgn;

	/**
	 * main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			terminal.clearScreen();
			state = GameState.START;
			Startup.printStartupMsg();
			terminal.enterPrivateMode();
			// start game
		} catch (UnsatisfiedLinkError e) {
			Object[] options = {
					"EXIT"
			};
			int n = JOptionPane.showOptionDialog(null,
					"Your native libs need to be in ./natives\n error message: \n"
							+ e.getMessage(),
					"ERROR",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null,  
					options,  // the titles of buttons
					options[0]); // default button title
			e.printStackTrace();
			
			System.exit(0);
		}
	}

	/**
	 * gets the dungeon
	 * 
	 * @return
	 */
	public static Dungeon getDungeon() {
		return dgn;
	}

	/**
	 * gets the state
	 * 
	 * @return
	 */
	public static GameState getState() {
		return state;
	}

	/**
	 * sets the gamestate
	 * 
	 * @param s
	 */
	public static void setState(GameState s) {
		state = s;
	}

	/**
	 * sets dungeon for startup and restart
	 * 
	 * @param d
	 *                dungeon to use
	 */
	protected static void setDgn(Dungeon d) {
		dgn = d;
	}

	public static boolean useGL() {
		return Settings.isGl();
	}

}
