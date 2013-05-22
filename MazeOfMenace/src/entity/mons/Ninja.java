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
package entity.mons;
import java.awt.Color;
import java.awt.Image;

import run.turn.Turn;
import terminal.ImageVars;

import entity.*;

/**
 * @author Matthew Gruda
 *
 */
public class Ninja extends GeneralMonster {

	private static final long serialVersionUID = 1L;
	private MoveDirection direct;
	public final String name = "Goblin";
	private int exp = 10;
	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public Ninja(int x, int y, int z) {
		super(x, y, z);
		super.setHp(Integer.MAX_VALUE);
		super.setHpmax(Integer.MAX_VALUE);
		super.setSpeed(10);
		super.setAttack(Integer.MAX_VALUE);
		super.setHitchance(1000);
		exp = 1000000;
	}

	/**
	 * 
	 */
	public Ninja() {
	}

	/* (non-Javadoc)
	 * @see entity.mons.GeneralMonster#queMove()
	 */
	@Override
	public void queMove() {
		Turn.addToTurnQue(this);
	}

	/* (non-Javadoc)
	 * @see entity.mons.GeneralMonster#act()
	 */
	@Override
	public void act() {
		super.act(this);
	}

	/* (non-Javadoc)
	 * @see entity.Entity#getExp()
	 */
	@Override
	public int getExp() {
		return exp;
	}

	@Override
	public String getName() {
		return "Ninja";
	}
	
	public char draw(){
		return 'N';
	}
	
	public int difficulty(){
		return 25;
	}

	@Override
	public Color getColor() {
		return Color.DARK_GRAY;
	}

	@Override
	public Image getfIm() {
		// TODO Auto-generated method stub
		return ImageVars.floorf;
	}

}
