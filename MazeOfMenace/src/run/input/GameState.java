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
/**
 * 
 */
package run.input;

import java.awt.event.KeyEvent;
import run.Init;
import run.SaveAndLoad;
import run.Startup;
import run.Util;

/**
 * states of game
 * for InputParse to use 
 * very important for controling input
 * @author Matthew Gruda
 */
public enum GameState{
	START() {

		@Override
		public void accept_input( KeyEvent k ){
			int c = k.getKeyCode();
			if(c == KeyEvent.VK_SPACE){
				Startup.start();
			}
		}

	},
	NORMAL() {

		@Override
		public void accept_input( KeyEvent k ){
			NormalModeParse.inputParse(k);
		}

	},

	DEAD() {

		@Override
		public void accept_input( KeyEvent k ){
			if(k.getKeyCode() == KeyEvent.VK_R){
				SaveAndLoad.restart();
			}
		}

	},
	INVENTORY() {

		@Override
		public void accept_input( KeyEvent k ){
			InventoryParser.inventoryParse(k);
		}

	},
	HELP() {

		private int page = 0;

		private static final int maxpage = 4;

		@Override
		public void accept_input( KeyEvent k ){
			switch(k.getKeyCode()){
				case KeyEvent.VK_Q:
					quit();
					break;
				case KeyEvent.VK_SPACE:
					pageUp();
					break;
				case KeyEvent.VK_B:
					pageDown();
					break;
			}
		}

		private void quit(){
			page = 0;
			Init.setState(GameState.NORMAL);
			Init.terminal.clearScreen();
			Init.getDungeon().draw();
		}

		private void pageUp(){
			page++;
			if(!(page > maxpage)){
				drawHelpScreen();
			}else{
				page--;
			}
		}

		private void pageDown(){
			page--;
			if(!(page < 0)){
				drawHelpScreen();
			}else{
				page++;
			}
		}

		@Override
		public String toString(){
			return page + "";
		}

	};

	public abstract void accept_input( KeyEvent k );

	public static void drawHelpScreen(){
		String s = "dat/HELP" + HELP + ".hl";
		s = Util.getTxtMsg(s);
		Init.terminal.clearScreen();
		Util.printStringWithLocNoCs(s, Init.terminal, 1, 1, 1);
	}
}
