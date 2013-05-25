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
package generator;

import dungeon.*;
import dungeon.tile.*;
import java.util.*;

/**
 * class makes randomly generated levels as there is no need at present to make
 * an object, all methods will be static and class wil be final
 * 
 * @author Matthew Gruda
 */
public final class GenerateLevel {

	private static int xSt, ySt;

	private static Random r = new Random();

	/*
	 * NOTES in all for loops i is x coord, c is y coord
	 * 
	 * will take advantage of reference passing to keep nasty return statements
	 * out
	 */

	/**
	 * this is the main function for making a random level object only level
	 * constructor should call this
	 * 
	 * @param lvl
	 *            the level obj to operate on
	 * @return level the generated level object
	 */
	public static Level generateLevel(Level lvl) {
		// preProccess(lvl); MOVED to level
		// Level lvl = new Level(z);
		// System.out.println("Starting");
		if (GenerateFixedLevel.has(lvl)) {
			GenerateFixedLevel.genFixedLevel(lvl);
			return lvl;
		}
		makeRooms(lvl);
		makeCorridors(lvl);
		makeStairs(lvl);
		mkFountains(lvl);
		postProcess(lvl);
		return lvl;
	}

	/**
	 * makes randomly spaced & sized rooms this method only determines where to
	 * place, how big and how many. calls makeRoom() for room creation
	 * 
	 * @param lvl
	 *            the level to operate on
	 */
	private static void makeRooms(Level lvl) {
		// first make num rooms. will be 4-8 for now
		int numRooms = r.nextInt(9) + 4;
		// now randomly gen each room
		for (int i = 0; i < numRooms; i++) {
			// now determine location
			int x = r.nextInt(55) + 1, y = r.nextInt(23);
			// make dimensions
			int xSize = r.nextInt(7) + 3, ySize = r.nextInt(7) + 3;
			// now the actual room
			makeRoom(lvl, x, y, xSize, ySize);
		}
	}

	/**
	 * makes a room from upper left corner. rooms have all floors for tile
	 * 
	 * @param lvl
	 *            - level obj to act on
	 * @param x
	 *            upper left corner x coord
	 * @param y
	 *            upper left corner y coord
	 * @param xSize
	 *            width
	 * @param ySize
	 *            height
	 */
	private static void makeRoom(Level lvl, int x, int y, int xSize, int ySize) {
		// System.out.println(x + " "+y+" "+xSize+" "+ySize );
		for (int i = x; i < xSize + x; i++)
			for (int c = y; c < ySize + y; c++)
				try {
					lvl.getlvl()[i][c].setBaseTile(new Floor());
				} catch (ArrayIndexOutOfBoundsException e) {
					// System.out.println(e);
				}
	}

	/**
	 * will detirmine where, size, num to make corridors
	 * 
	 * @param lvl
	 *            is the level to operate on
	 */
	private static void makeCorridors(Level lvl) {
		// gen num
		int numCors = r.nextInt(18) + 9;
		for (int i = 0; i < numCors; i++)
			// generate place. make sure is valid (next to room)
			for (; true;) {
				// make random location
				int x = r.nextInt(50) + 6, y = r.nextInt(20) + 6;
				// check if it is good
				CorridorPlace check = isValidCorPlace(lvl, x, y);
				if (check != CorridorPlace.FALSE) {
					int length = r.nextInt(40) + 15;
					// finally make make the corridor
					makeCorridor(lvl, x, y, length, check);
					break;
				}
			}
	}

	/**
	 * combines checking if x, y is a valid corridor place with choosing a
	 * direction to go for corridor
	 * 
	 * @param lvl
	 *            level to operate on
	 * @param x
	 *            coord of tile to test
	 * @param y
	 *            coord of tile to test
	 * @return direction if it is true, FALSE if cannot work
	 */
	private static CorridorPlace isValidCorPlace(Level lvl, int x, int y) {
		if (lvl.getlvl()[x][y] == null
				|| lvl.getlvl()[x][y].getBasetile() instanceof Wall)
			return CorridorPlace.FALSE;
		if (lvl.getlvl()[x + 1][y].getBasetile() instanceof Wall)
			return CorridorPlace.EAST;
		else if (lvl.getlvl()[x - 1][y].getBasetile() instanceof Wall)
			return CorridorPlace.WEST;
		else if (lvl.getlvl()[x][y + 1].getBasetile() instanceof Wall)
			return CorridorPlace.NORTH;
		else if (lvl.getlvl()[x + 1][y - 1].getBasetile() instanceof Wall)
			return CorridorPlace.SOUTH;
		return CorridorPlace.FALSE;
	}

	private enum CorridorPlace {
		EAST, WEST, NORTH, SOUTH, FALSE
	}

	/**
	 * 
	 * @param lvl
	 * @param x
	 * @param y
	 * @param length
	 * @param direction
	 */
	private static void makeCorridor(Level lvl, int x, int y, int length,
			CorridorPlace direction) {
		switch (direction) {
		case EAST:
			for (int i = 0; i < length; i++)
				if (x + i < 63)
					lvl.getlvl()[x + i][y].setBaseTile(new Floor());
			break;
		case WEST:
			for (int i = 0; i < length; i++)
				if (x - i > 0)
					lvl.getlvl()[x - i][y].setBaseTile(new Floor());
			break;
		case NORTH:
			for (int i = 0; i < length; i++)
				if (y - i > 0)
					lvl.getlvl()[x][y - i].setBaseTile(new Floor());
				else
					break;
			break;
		case SOUTH:
			for (int i = 0; i < length; i++)
				if (y + i < 31)
					lvl.getlvl()[x][y + i].setBaseTile(new Floor());
				else
					break;
			break;
		case FALSE:// do nothing
		}
	}

	/**
	 * will make stairs
	 * 
	 * @param lvl
	 */
	private static void makeStairs(Level lvl) {
		makeUpStair(lvl);
		detDwStairPlace(lvl);
	}

	/**
	 * makes up stair at random location
	 * 
	 * @param lvl
	 */
	private static void makeUpStair(Level lvl) {
		while (true) {
			xSt = r.nextInt(63) + 1;
			ySt = r.nextInt(31) + 1;
			if (lvl.getlvl()[xSt][ySt].getBasetile() instanceof Floor) {
				lvl.getlvl()[xSt][ySt].setBaseTile(new StairUp());
				lvl.setUpStrX(xSt);
				lvl.setUpStrY(ySt);
				return;
			}
		}
	}

	private static void mkDwnStr(Level lvl) {
		int x = 0, y = 0;
		for (int i = 2000; i > 0; i--) {
			x = r.nextInt(63) + 1;
			y = r.nextInt(31) + 1;
			if (lvl.getlvl()[x][y].getBasetile() instanceof Floor
					&& !(lvl.getlvl()[x][y].getBasetile() instanceof StairUp))
				break;
		}
		lvl.getlvl()[x][y].setBaseTile(new StairDown());
		lvl.setDwStrX(x);
		lvl.setDwStrY(y);
	}

	private static void detDwStairPlace(Level lvl) {
		int xLoc = lvl.getUpStrX(), yLoc = lvl.getUpStrY();
		int iters = r.nextInt(10) + 10;
		outer: for (int i = 0; i < iters; i++) {
			CorridorPlace c = getDir(lvl, xLoc, yLoc);
			int steps = r.nextInt(10) + 1;
			switch (c) {
			case EAST:
				for (int z = 0; z < steps; z++) {
					if (bc(xLoc, yLoc) &&lvl.getlvl()[xLoc + 1][yLoc].getBasetile() instanceof Floor &&
							!(lvl.getlvl()[xLoc - 1][yLoc].getBasetile() instanceof StairUp)) {
						xLoc++;
					} else {
						break;
					}
				}
				break;
			case FALSE:
				break outer;
			case NORTH:
				for (int z = 0; z < steps; z++) {
					if (bc(xLoc, yLoc) &&lvl.getlvl()[xLoc][yLoc - 1].getBasetile() instanceof Floor &&
							!(lvl.getlvl()[xLoc - 1][yLoc].getBasetile() instanceof StairUp)) {
						yLoc--;
					} else {
						break;
					}
				}
				break;
			case SOUTH:
				for (int z = 0; z < steps; z++) {
					if (bc(xLoc, yLoc) && lvl.getlvl()[xLoc][yLoc + 1].getBasetile() instanceof Floor &&
							!(lvl.getlvl()[xLoc - 1][yLoc].getBasetile() instanceof StairUp)) {
						yLoc++;
					} else {
						break;
					}
				}
				break;
			case WEST:
				for (int z = 0; z < steps; z++) {
					if (bc(xLoc, yLoc) && lvl.getlvl()[xLoc - 1][yLoc].getBasetile() instanceof Floor &&
							!(lvl.getlvl()[xLoc - 1][yLoc].getBasetile() instanceof StairUp)) {
						xLoc--;
					} else {
						break;
					}
				}
				break;
			default:
				break;

			}
		}
		if(lvl.getlvl()[xLoc][yLoc].getBasetile() instanceof StairUp){
			mkDwnStr(lvl);
			return;
		}
		int x = xLoc, y = yLoc;
		lvl.getlvl()[x][y].setBaseTile(new StairDown());
		lvl.setDwStrX(x);
		lvl.setDwStrY(y);
	}
	
	private static boolean bc(int x, int y){
		if(x > 0 && x < 63)
			if(y > 0 && y < 63)
				return true;
		return false;
	}

	private static CorridorPlace getDir(Level lvl, int x, int y) {
		for (int i = 0; i < 2000; i++) {
			switch (r.nextInt(4)) {
			case 0: // north
				if (lvl.getlvl()[x - 1][y].getBasetile() instanceof Floor) {
					return CorridorPlace.NORTH;
				}
				break;
			case 1:
				if (lvl.getlvl()[x + 1][y].getBasetile() instanceof Floor) {
					return CorridorPlace.SOUTH;
				}
				break;
			case 2:
				if (lvl.getlvl()[x][y - 1].getBasetile() instanceof Floor) {
					return CorridorPlace.WEST;
				}
				break;
			case 3:
				if (lvl.getlvl()[x][y + 1].getBasetile() instanceof Floor) {
					return CorridorPlace.EAST;
				}
			}
		}
		return CorridorPlace.FALSE;
	}

	private static void mkFountains(Level lvl) {
		int i = new Random().nextInt(4);
		for (int c = 0; c < i; c++)
			while (true) {
				int x = r.nextInt(63) + 1;
				int y = r.nextInt(31) + 1;
				if (lvl.getlvl()[x][y].getBasetile() instanceof Floor) {
					lvl.getlvl()[x][y].setBaseTile(new Fountan());
					break;
				}
			}
	}

	/**
	 * to clean up after level gen, makes a wall around the outside should be
	 * called last
	 * 
	 * @param lvl
	 */
	private static void postProcess(Level lvl) {
		for (int i = 0; i < 64; i += 63)
			for (int c = 0; c < 32; c++)
				lvl.getlvl()[i][c].setBaseTile(new Wall());
		for (int i = 0; i < 64; i++)
			for (int c = 0; c < 32; c += 31)
				lvl.getlvl()[i][c].setBaseTile(new Wall());
	}

}
