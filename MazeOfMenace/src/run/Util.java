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

import run.input.GameState;

import java.awt.Color;
import java.io.*;
import terminal.*;

/**
 * gennerally usefull stuff that doesn't fit elsewhere
 * 
 * @author matthew
 * 
 */
public class Util {

	private static boolean initalized = false;
	private static BufferedWriter displayMsgOut;

	/**
	 * initalizes the logger
	 * 
	 * @return whether initalized correctly
	 */
	private static boolean initialize() {
		try {
			displayMsgOut = new BufferedWriter(new PrintWriter(new File(
					"log/display.log")));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * logs a message from string s
	 * 
	 * @param s
	 */
	public static void logDisplayMsg(String s) {
		if (!initalized)
			initalized = initialize();
		try {
			displayMsgOut.write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			displayMsgOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * closes all open printstrams
	 */
	public static void closeStreams() {
		try {
			displayMsgOut.write("\n");
			displayMsgOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
		}
	}

	/**
	 * prints a string to terminal term, after clearing the screen
	 * wrapper for printStringWithLocNoCs
	 * 
	 * @param printString
	 * @param term
	 */
	public static void printString(String printString, Terminal term) {
		char[] printArray = printString.toCharArray();
		for (char c : printArray)
			term.putCharacter(c);
	}

	/**
	 * prints a string with a start location, margin and clears before printing
	 * 
	 * @param printString
	 * @param term
	 * @param xStar
	 * @param yStart
	 * @param margin
	 */
	public static void printStringWithLoc(String printString, Terminal term,
			int xStar, int yStart, int margin) {
		term.clearScreen();
		printStringWithLocNoCs(printString, term, xStar, yStart, margin);

	}

	/**
	 * prints a string with start location, margin without clearing
	 * 
	 * @param printString
	 * @param term
	 * @param xStar
	 * @param yStart
	 * @param margin
	 */
	public static void printStringWithLocNoCs(String printString,
			Terminal term, int xStar, int yStart, int margin) {
		char[] printArray = printString.toCharArray();
		term.moveCursor(xStar, yStart);
		boolean inColor = false, print = true;
		Color col = Color.white;
		for (int c = 0; c < printArray.length; c++) {
			if (printArray[c] == '`' && !inColor) {
				inColor = true;
				print = false;
				col = getColor(printArray[c + 1]);
			} else if (printArray[c] == '`' && inColor) {
				col = Color.white;
				inColor = false;
				print = false;
			}

			if (inColor && c != 0 && printArray[c - 1] == '`')
				print = false;
			if (printArray[c] == '\n') {
				yStart++;
				term.moveCursor(margin, yStart);
				continue;
			}
			if (print)
				term.putCharacter(printArray[c], col);
			else
				print = true;
		}
	}

	public static Color getColor(char c) {
		switch (c) {
		case 'r':
			return Color.red;
		case 'g':
			return Color.GREEN;
		case 'o':
			return Color.orange.darker();
		case 'b':
			return Color.BLUE;
		case 'c':
			return Color.CYAN;
		case 'p':
			return Color.MAGENTA;
		case 'f': 
			return items.OrbOfYendor.c;
		}
		return Color.WHITE;
	}

	/**
	 * provides a method to show a string on death or error. does not kill
	 * proccess
	 * 
	 * @param s
	 */
	public static void kill(String s, boolean isFile) {
		Init.terminal.clearScreen();
		String c = "";
		if (isFile)
			c = getTxtMsg(s);
		else
			c = s;
		c += "\nPress end to exit or R to restart the game";
		Init.setState(GameState.DEAD);
		printStringWithLocNoCs(c, Init.terminal, 5, 5, 5);
	}

	/**
	 * reads text from a file, add '\n' removed and closes and returns the
	 * output
	 * 
	 * @param file
	 *            file to read
	 * @return the String containing the contents
	 */
	public static String getTxtMsg(String file) {
		String s = "";
		try {
			FileReader r = new FileReader(new File(file));
			BufferedReader read = new BufferedReader(r);
			while (true)
				try {
					String c = read.readLine();
					if (c == null)
						break;
					c += '\n';
					s += c;
				} catch (IOException e) {
					e.printStackTrace();
				}
			read.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
}
