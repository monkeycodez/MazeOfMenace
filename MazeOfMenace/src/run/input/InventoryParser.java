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

import java.awt.event.KeyEvent;

import entity.player.inventory.*;

import run.Init;

/**
 * @author matthew
 * 
 */
public final class InventoryParser {

	private static boolean inSelection = false;

	protected static void inventoryParse(KeyEvent k) {
		if (k.getKeyCode() == KeyEvent.VK_Q) {
			quitInv();
		}
		if (inSelection) {
			parseInSel(k);
		}
			parseInNSel(k);
		
	}
	
	private static void parseInSel(KeyEvent k){
		switch(k.getKeyCode()){
		case KeyEvent.VK_R: destroy(); desel(); break;
		case KeyEvent.VK_W:
		case KeyEvent.VK_U:
		case KeyEvent.VK_E:equip(); 
		case KeyEvent.VK_D: desel();
		default:System.out.println(k.getKeyChar());;
		}
	}
	
	private static void destroy(){
		if(armor){
			Init.getDungeon().getPlayer().getIndv().drop(chsna);
		}else if(wep){
			Init.getDungeon().getPlayer().getIndv().drop(chosen);
		}
		
	}
	
	private static void equip(){
		Inventory i = Init.getDungeon().getPlayer().getIndv();
		if(armor){
			i.equip(chsna);
			desel();
		}else if(wep){
			i.equip(chosen);
			desel();
		}else if (cons){
			i.use(chsna);
			desel();
			
		}
	}
	
	private static void desel(){
		wep = false;
		armor = false;
		cons = false;
		inSelection = false;
		chosen = 0;
		chsna= 'a';
		Init.terminal.clearScreen();
		InventoryDisplay.draw();
	}
	

	public static boolean wep = false, armor = false, cons = false;
	private static int chosen;
	private static char chsna;

	private static void parseInNSel(KeyEvent k) {
		switch (k.getKeyCode()) {
		case KeyEvent.VK_1:
			parseInNSel(1);
			break;
		case KeyEvent.VK_2:
			parseInNSel(2);
			break;
		case KeyEvent.VK_3:
			parseInNSel(3);
			break;
		case KeyEvent.VK_4:
			parseInNSel(4);
			break;
		case KeyEvent.VK_5:
			parseInNSel(5);
			break;
		case KeyEvent.VK_6:
			parseInNSel(6);
			break;
		case KeyEvent.VK_7:
			parseInNSel(7);
			break;
		case KeyEvent.VK_8:
			parseInNSel(8);
			break;
		case KeyEvent.VK_9:
			parseInNSel(9);
			break;
		case KeyEvent.VK_0:
			parseInNSel(0);
			break;
		case KeyEvent.VK_A:
			parseInNSel('a');
			break;
		case KeyEvent.VK_B:
			parseInNSel('b');
			break;
		case KeyEvent.VK_C:
			parseInNSel('c');
			break;
		case KeyEvent.VK_D:
			parseInNSel('d');
			break;
		case KeyEvent.VK_E:
			parseInNSel('e');
			break;
		case KeyEvent.VK_F:
			parseInNSel('f');
			break;
		case KeyEvent.VK_G:
			parseInNSel('g');
			break;
		case KeyEvent.VK_H:
			parseInNSel('h');
			break;
		case KeyEvent.VK_I:
			parseInNSel('i');
			break;
		case KeyEvent.VK_J:
			parseInNSel('g');
			break;
		/*case KeyEvent.VK_O:
			parseInNSel('o');
			break;
		case KeyEvent.VK_R:
			parseInNSel('r');
			break;
		case KeyEvent.VK_S:
			parseInNSel('s');
			break;
		case KeyEvent.VK_T:
			parseInNSel('t');
			break;
		case KeyEvent.VK_U:
			parseInNSel('u');
			break;
		case KeyEvent.VK_V:
			parseInNSel('v');
			break;
		case KeyEvent.VK_X:
			parseInNSel('x');
			break;
		case KeyEvent.VK_Y:
			parseInNSel('y');
			break;
		case KeyEvent.VK_Z:
			parseInNSel('z');
			break;
			default: System.out.println("no");*/
		}//orstuvwxyz
	}

	private static void parseInNSel(int i) {
		if (InventoryDisplay.has(i)) {
			wep = true;
			armor = false;
			cons = false;
			inSelection = true;
			chosen = i;
			Init.terminal.clearScreen();
			InventoryDisplay.draw();
		}
	}

	private static void parseInNSel(char c) {
		if ("orstuvwxyz".indexOf(c) != -1) {
			if(!InventoryDisplay.hasC(c))
				return;
			wep = false;
			System.out.println("has");
			//if("orstuvxyz".indexOf(c) == -1)
			armor = false;
			//else
				cons = true;
			inSelection = true;
			chsna = c;
			Init.terminal.clearScreen();
			InventoryDisplay.draw();
		}
		if(!InventoryDisplay.hasA(c))
			return;
		wep = false;
		//if("orstuvxyz".indexOf(c) == -1)
		armor = true;
		//else
		cons = false;
		inSelection = true;
		chsna = c;
		Init.terminal.clearScreen();
		InventoryDisplay.draw();
	}

	private static void quitInv() {
		Init.setState(GameState.NORMAL);
		Init.terminal.clearScreen();
		Init.getDungeon().draw();
	}

}
