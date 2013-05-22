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
package dungeon.tile;

import java.awt.Image;

import terminal.ImageVars;

/**
 * down stair. nothing fancy here either
 * @author matthew
 *
 */
public class StairDown extends Floor {

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return ImageVars.stairdown;
	}

	@Override
	public Image getImageOutOfSight() {
		// TODO Auto-generated method stub
		return ImageVars.stairdown2;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StairDown() {
		// TODO Auto-generated constructor stub
	}
	
	public char draw(){
		return '>';
	}
	public Image getfImage(){
		return ImageVars.stairdownf;
	}
	public Image getfImageOutOfSight(){
		return ImageVars.stairdownf2;
	}

}
