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
package terminal;

import java.awt.Color;
import java.awt.HeadlessException;

/**
 * @author Matthew Gruda
 *
 */
public class TtyTerm extends Terminal {
	
	private char[][] displayArray = new char[65][65];
	private int x, y;
	//private boolean b = false;
	/**
	 * 
	 */
	public TtyTerm() {
		//if(!b)
		throw new HeadlessException("No GUI. Sorry but tty mode not implemented");
		//x = 0;
		//y = 0;
	}

	/* (non-Javadoc)
	 * @see terminal.Terminal#putCharacter(char)
	 */
	@Override
	public void putCharacter(char c) {
		try{
		displayArray[x][y] = c;
		} catch (Exception e){
			e.printStackTrace();
		}
		if (x++ == 65){
			x = 0;
			y++;
		}else{
			x++;
		}
		draw();
		//super.putCharacter(c);
	}

	/* (non-Javadoc)
	 * @see terminal.Terminal#moveCursor(int, int)
	 */
	@Override
	public void moveCursor(int x, int y) {
		this.x = x;
		this.y = y;
		//super.moveCursor(x, y);
	}

	/* (non-Javadoc)
	 * @see terminal.Terminal#clear()
	 */
	@Override
	public void clearScreen() {
		try{
			Runtime.getRuntime().exec("clear");
		} catch (Exception e){
			
		}
		//super.clear();
	}
	
	private void draw(){
		for(char[] c: displayArray){
			for(char x: c){
				System.out.print(x);
			}
			System.out.println();
		}
	}

	/* (non-Javadoc)
	 * @see terminal.Terminal#enterPrivateMode()
	 */
	@Override
	public void enterPrivateMode() {
		clearScreen();
		//super.enterPrivateMode();
	}

	/* (non-Javadoc)
	 * @see terminal.Terminal#exitPrivateMode()
	 */
	@Override
	public void exitPrivateMode() {
		// TODO Auto-generated method stub
		//super.exitPrivateMode();
	}

	@Override
	public void putCharacter(char c, Color col) {
		// TODO Auto-generated method stub
		
	}

}
