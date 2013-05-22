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
package items;

import java.awt.Color;

/**
 * @author matthew
 * 
 */
public abstract class AbstractConsumeable extends AbstractItem {

	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public AbstractConsumeable(int x, int y, int z) {
		super(x, y, z);
	}

	/**
	 * 
	 */
	public AbstractConsumeable() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dungeon.tile.AbstractObject#draw()
	 */
	@Override
	public char draw() {
		return '!';
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dungeon.tile.AbstractObject#getColor()
	 */
	@Override
	public Color getColor() {
		return Color.PINK;
	}

	/**
	 * The purpose of this method is to preform an action. it will then be set
	 * to null by inventory
	 */
	public abstract void consume();

}
