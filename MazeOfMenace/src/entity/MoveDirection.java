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
package entity;

/**
 * provides direction for move method
 * @author matthew
 */
public enum MoveDirection{
	NORTH {
		@Override
		public MoveDirection leftdir() {
			// TODO Auto-generated method stub
			return WEST;
		}
		
		public String toString(){
			return "NORTH";
		}

		@Override
		public MoveDirection rightdir() {
			// TODO Auto-generated method stub
			return EAST;
		}
	}, SOUTH {
		@Override
		public MoveDirection leftdir() {
			// TODO Auto-generated method stub
			return EAST;
		}

		public String toString(){
			return "SOUTH";
		}
		@Override
		public MoveDirection rightdir() {
			// TODO Auto-generated method stub
			return WEST;
		}
	}, EAST {
		@Override
		public MoveDirection leftdir() {
			// TODO Auto-generated method stub
			return NORTH;
		}
		public String toString(){
			return "EAST";
		}
		@Override
		public MoveDirection rightdir() {
			// TODO Auto-generated method stub
			return SOUTH;
		}
	}, WEST {

		@Override
		public MoveDirection leftdir() {
			// TODO Auto-generated method stub
			return SOUTH;
		}
		public String toString(){
			return "WEST";
		}
		@Override
		public MoveDirection rightdir() {
			// TODO Auto-generated method stub
			return NORTH;
		}
		
	};
	public abstract MoveDirection rightdir();
	public abstract MoveDirection leftdir();
}
