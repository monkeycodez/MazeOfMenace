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
package generator;

import java.util.Random;
import dungeon.Level;
import dungeon.tile.Tile;
import dungeon.tile.TileTemplate;
import dungeon.tile.template.StairDownTmpl;
import dungeon.tile.template.StairUpTmpl;

/**
 * class makes randomly generated levels as there is no need at present to make
 * an object, all methods will be static and class wil be final
 * 
 * @author Matthew Gruda
 */
public final class GenerateLevel{

	private static int xSt, ySt;

	private static Random r = new Random();

	/**
	 * this is the main function for making a random level object only level
	 * constructor should call this
	 * 
	 * @param lvl
	 *            the level obj to operate on
	 * @return level the generated level object
	 */
	public static Level generateLevel( Level lvl ){

		makeRooms(lvl);
		makeCorridors(lvl);
		makeStairs(lvl);
		mkFountains(lvl);
		return lvl;
	}

	/**
	 * makes randomly spaced & sized rooms this method only determines where to
	 * place, how big and how many. calls makeRoom() for room creation
	 * 
	 * @param lvl
	 *            the level to operate on
	 */
	private static void makeRooms( Level lvl ){
		// first make num rooms. will be 4-8 for now
		int numRooms = r.nextInt(9) + 4;
		// now randomly gen each room
		for(int i = 0; i < numRooms; i++){
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
	private static void makeRoom(
		Level lvl,
		int x,
		int y,
		int xSize,
		int ySize ){
		// System.out.println(x + " "+y+" "+xSize+" "+ySize );
		for(int i = x; i < xSize + x; i++){
			for(int c = y; c < ySize + y; c++){
				try{
					lvl.getT(i, c)
					.setTemp(TileTemplate.floor);
				}catch(ArrayIndexOutOfBoundsException e){
					// System.out.println(e);
				}
			}
		}
	}

	/**
	 * will detirmine where, size, num to make corridors
	 * 
	 * @param lvl
	 *            is the level to operate on
	 */
	private static void makeCorridors( Level lvl ){
		// gen num
		int numCors = r.nextInt(18) + 9;
		for(int i = 0; i < numCors; i++){
			// generate place. make sure is valid (next to room)
			for(; true;){
				// make random location
				int x = r.nextInt(50) + 6, y = r.nextInt(20) + 6;
				// check if it is good
				CorridorPlace check = isValidCorPlace(lvl, x, y);
				if(check != CorridorPlace.FALSE){
					int length = r.nextInt(40) + 15;
					// finally make make the corridor
					makeCorridor(lvl, x, y, length, check);
					break;
				}
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
	private static CorridorPlace isValidCorPlace( Level lvl, int x, int y ){
		if(lvl.getT(x, y) == null
			|| lvl.getT(x, y).getTemp().can_walk()){
			return CorridorPlace.FALSE;
		}
		if(lvl.getT(x + 1, y).getTemp().can_walk()){
			return CorridorPlace.EAST;
		}else if(lvl.getT(x - 1, y).getTemp().can_walk()){
			return CorridorPlace.WEST;
		}else if(lvl.getT(x, y + 1).getTemp().can_walk()){
			return CorridorPlace.NORTH;
		}else if(lvl.getT(x, y - 1).getTemp().can_walk()){
			return CorridorPlace.SOUTH;
		}
		return CorridorPlace.FALSE;
	}

	private enum CorridorPlace{
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
	private static void makeCorridor( Level lvl, int x, int y, int length,
		CorridorPlace direction ){
		switch(direction){
			case EAST:
				for(int i = 0; i < length; i++){
					if(x + i < 63){
						lvl
						.getT(x + i, y)
							.setTemp(
							TileTemplate.floor);
						;
					}
				}
				break;
			case WEST:
				for(int i = 0; i < length; i++){
					if(x - i > 0){
						lvl
						.getT(x - i, y)
							.setTemp(
							TileTemplate.floor);
						;

					}
				}
				break;
			case NORTH:
				for(int i = 0; i < length; i++){
					if(y - i > 0){
						lvl
						.getT(x, y - i)
							.setTemp(
							TileTemplate.floor);
						;

					}

				}
				break;
			case SOUTH:
				for(int i = 0; i < length; i++){
					if(i + y < 31){
						lvl
						.getT(x, y + i)
							.setTemp(
							TileTemplate.floor);
					}

				}
				break;
			case FALSE:// do nothing
		}
	}

	/**
	 * will make stairs
	 * 
	 * @param lvl
	 */
	private static void makeStairs( Level lvl ){
		makeUpStair(lvl);
		detDwStairPlace(lvl);
	}

	/**
	 * makes up stair at random location
	 * 
	 * @param lvl
	 **/
	private static void makeUpStair( Level lvl ){
		while(true){
			xSt = r.nextInt(63) + 1;
			ySt = r.nextInt(31) + 1;
			if(lvl.getT(xSt, ySt).can_walk()){
				Tile to = null;
				if(lvl.getDepth() != 0){
					Level up = lvl.get_from().getLevel(
						lvl.getDepth() - 1);
					to = up.getT(
						up.getDwStrX(),
						up.getDwStrY());
				}
				lvl.getT(xSt, ySt).setTemp(new StairUpTmpl(to));
				lvl.setUpStrX(xSt);
				lvl.setUpStrY(ySt);
				return;
			}
		}
	}

	private static void mkDwnStr( Level lvl ){
		int x = 0, y = 0;
		Tile at = null;
		for(int i = 2000; i > 0; i--){
			x = r.nextInt(63) + 1;
			y = r.nextInt(31) + 1;
			at = lvl.getT(x, y);
			if(at.can_walk()
				&&
				at.getType() != StairUpTmpl.STAIRUP){
				break;
			}
		}
		at.setTemp(new StairDownTmpl());
		lvl.setDwStrX(at.getX());
		lvl.setDwStrY(at.getY());
	}

	private static void detDwStairPlace( Level lvl ){
		int xLoc = lvl.getUpStrX(), yLoc = lvl.getUpStrY();
		int iters = r.nextInt(10) + 10;
		Tile at = lvl.getT(xLoc, yLoc);
		outer: for(int i = 0; i < iters; i++){
			CorridorPlace c = getDir(lvl, xLoc, yLoc);
			int steps = r.nextInt(10) + 1;
			Tile next = null;
			switch(c){
				case EAST:
					next = at.east();
					for(int z = 0; z < steps; z++){
						if(bc(xLoc, yLoc) &&
							next.can_walk() &&
							next.getType() != StairUpTmpl.STAIRUP){
							xLoc++;
							at = next;
						}else{
							break;
						}
					}
					break;
				case FALSE:
					break outer;
				case NORTH:
					next = at.north();
					for(int z = 0; z < steps; z++){
						if(bc(xLoc, yLoc) &&
							next.can_walk() &&
							next.getType() != StairUpTmpl.STAIRUP){
							yLoc--;
							at = next;
						}else{
							break;
						}
					}
					break;
				case SOUTH:
					next = at.south();
					for(int z = 0; z < steps; z++){
						if(bc(xLoc, yLoc) &&
							next.can_walk() &&
							next.getType() != StairUpTmpl.STAIRUP){
							yLoc++;
							at = next;
						}else{
							break;
						}
					}
					break;
				case WEST:
					next = at.west();
					for(int z = 0; z < steps; z++){
						if(bc(xLoc, yLoc) &&
							next.can_walk() &&
							next.getType() != StairUpTmpl.STAIRUP){
							xLoc--;
							at = next;
						}else{
							break;
						}
					}
					break;
				default:
					break;

			}
		}
		if(at.getType() == StairUpTmpl.STAIRUP){
			mkDwnStr(lvl);
			return;
		}
		int x = xLoc, y = yLoc;
		at.setTemp(new StairDownTmpl());
		lvl.setDwStrX(at.getX());
		lvl.setDwStrY(at.getY());
	}

	private static boolean bc( int x, int y ){
		if(x > 0 && x < 63){
			if(y > 0 && y < 63){
				return true;
			}
		}
		return false;
	}

	private static CorridorPlace getDir( Level lvl, int x, int y ){
		for(int i = 0; i < 2000; i++){
			switch(r.nextInt(4)){
				case 0: // north
					if(bc(x, y) &&
						lvl.getT(x - 1, y)
							.getTemp().can_walk()){
						return CorridorPlace.NORTH;
					}
					break;
				case 1:
					if(bc(x, y) &&
						lvl.getT(x + 1, y)
							.getTemp().can_walk()){
						return CorridorPlace.SOUTH;
					}
					break;
				case 2:
					if(bc(x, y) &&
						lvl.getT(x, y - 1)
							.getTemp().can_walk()){
						return CorridorPlace.WEST;
					}
					break;
				case 3:
					if(bc(x, y) &&
						lvl.getT(x, y + 1)
							.getTemp().can_walk()){
						return CorridorPlace.EAST;
					}
			}
		}
		return CorridorPlace.FALSE;
	}

	private static void mkFountains( Level lvl ){
		int i = new Random().nextInt(4);
		for(int c = 0; c < i; c++){
			while(true){
				int x = r.nextInt(63) + 1;
				int y = r.nextInt(31) + 1;
				if(lvl.getT(x, y).getTemp().can_walk()){
					lvl.getT(x, y).setTemp(
						TileTemplate.fountain);
					break;
				}
			}
		}
	}

}
