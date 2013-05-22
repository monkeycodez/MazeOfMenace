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
package dungeon.tile;

import java.awt.Image;

import terminal.ImageVars;

/**
 * simply a wall object
 * 
 * @author Matthew Gruda
 * 
 */
public class Wall extends BaseTileType {

	@Override
	public Image getImage() {
		return ImageVars.wall;
	}

	@Override
	public Image getImageOutOfSight() {
		return ImageVars.wall2;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public Wall() {	}

	/**
	 * @return the wall character
	 */
	public char draw() {
		return '#';
	}
	public Image getfImage(){
		return ImageVars.wallf;
	}
	public Image getfImageOutOfSight(){
		return ImageVars.wallf2;
	}

}
