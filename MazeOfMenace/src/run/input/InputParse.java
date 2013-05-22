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
package run.input;

import run.*;

import java.awt.*;
import java.awt.event.*;

/**
 * level 1 input parsing.
 * 
 * this class tells what to use in parsing the input, then sends the input to
 * the appropriate method
 * 
 * @author Matthew Gruda
 * 
 */
public final class InputParse {

	/**
	 * k is parsed based opon the gamestate
	 * 
	 * @param k
	 */
	public static void inputParse(KeyEvent k) {
		switch (Init.getState()) {
		case NORMAL:
			NormalModeParse.inputParse(k);
			break;
		case OTHER:
			break;
		case START:
			Startup.startManager(k);
			break;
		case DEAD:
			deadParse(k);
			break;
		case INVENTORY:
			InventoryParser.inventoryParse(k);
			break;
		case HELP: HelpParse.HelpStateParse(k);
			break;
		default:
			break;

		}
	}

	/**
	 * adds a restart ability to the DEAD mode
	 * 
	 * @param k
	 */
	private static void deadParse(KeyEvent k) {
		if (k.getKeyCode() == KeyEvent.VK_R)
			SaveAndLoad.restart();
	}
}
