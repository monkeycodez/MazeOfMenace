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

/**
 * @author 0200565
 *
 */
public class AdmantineSword extends AbstractWeapon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public AdmantineSword(int x, int y, int z) {
		super(x, y, z);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public AdmantineSword() {
	}

	public int getWeaponStaticMod() {
		return 50+plus;
	}

	public int getWeaponPercentMod() {
		return 0;
	}

	public String getName() {
		if(plus > 0){
			return "+"+plus+" *Adamantine Sword";
		}else if(plus < 0){
			return plus+"*Adamantine Sword";
		}
			return "*Adamantine Sword*";
	}

}
