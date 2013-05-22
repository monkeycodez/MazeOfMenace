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
package entity.player.inventory;

import run.input.*;
import run.*;
import items.*;

public class InventoryDisplay {

	public static void draw() {
		Init.terminal.clearScreen();
		Util.printStringWithLocNoCs(getDisplayString(), Init.terminal, 1, 1, 1);
	}

	public static boolean has(char c) {
		try {
			if (Init.getDungeon().getPlayer().getIndv().getABag()[lookup
					.indexOf(c)] != null) {
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			if (Init.getDungeon().getPlayer().getIndv().getABag()[lk.indexOf(c)] != null) {
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return false;
	}

	public static boolean hasA(char c) {
		return Init.getDungeon().getPlayer().getIndv().getABag()[lookup
				.indexOf(c)] != null;
	}

	public static boolean hasC(char c) {
		return Init.getDungeon().getPlayer().getIndv().getABag()[lk.indexOf(c)] != null;
	}

	public static boolean has(int i) {
		return Init.getDungeon().getPlayer().getIndv().getWBag()[i] != null;
	}

	private static String getDisplayString() {
		String s = "";
		s += "`c---------------------Inventory-----------------------`\n";
		s += getInvString();
		s += '\n';
		s += '\n';
		s += getInfoLine();
		s += '\n';
		s += '\n';
		s += getOrbString();
		s += '\n';
		s += getEquip();
		return s;
	}

	private static String getOrbString() {
		if (Init.getDungeon().getPlayer().hasOrb()) {
			return "`f     You Have The +10 Orb Of Yendor`";
		}

		return "";
	}

	private static String lookup = "abcdefghij", lk = "orstuvwxyz";

	private static String getEquip() {
		String s = "";
		Inventory i = Init.getDungeon().getPlayer().getIndv();
		if (i.getA() != null) {
			s += "     Armor: `p" + i.getA().getName() + "`\n";
		} else {
			s += "       \n";
		}
		if (i.getW() != null) {
			s += "     Weapon: `r" + i.getW().getName() + "`\n";
		}
		return s;
	}

	private static char[] cha = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
			'j' };
	private static char[] cha2 = { 'o', 'r', 's', 't', 'u', 'v', 'y', 'w', 'x',
			'y' };

	private static String getInvString() {

		String s = "";
		s += "`c-----------Armor------------------Weapons------------`\n";
		AbstractArmor[] arma = Init.getDungeon().getPlayer().getIndv()
				.getABag();
		AbstractWeapon[] wepa = Init.getDungeon().getPlayer().getIndv()
				.getWBag();
		AbstractConsumeable[] consa = Init.getDungeon().getPlayer().getIndv()
				.getCbag();
		for (int i = 0; i < 10; i++) {
			String tmp = "";
			if (arma[i] != null) {
				tmp += "`p" + cha[i] + " - " + arma[i].getName() + "`";
			} else {
				tmp += "             ";
			}
			tmp += "                ";
			if (wepa[i] != null) {
				tmp += "`r" + i + " - " + wepa[i].getName() + "`";
			} else {
				tmp += "                ";
			}
			tmp += "      ";
			/*
			 * if(consa[i] != null){ tmp +=
			 * "`g"+cha2[i]+" - "+consa[i].getName()+"`"; }
			 */

			tmp += '\n';
			s += tmp;
			tmp = "";
		}
		return s;
	}

	private static boolean inDisplay = false;

	private static String getInfoLine() {
		if (InventoryParser.cons) {
			return "        (u)se, (d)select, dest(r)oy, (q)uit";
		}
		if (InventoryParser.armor) {
			return "       (w)ear, (d)eselect, dest(r)oy (q)uit";
		}
		if (InventoryParser.wep) {
			return "       (e)quip, (d)select, dest(r)oy, (q)uit";
		}
		return "     Choose an item to use or (q)uit";
	}

}
