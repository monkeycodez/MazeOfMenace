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

/**
 * @author matthew
 *
 */
public class OrcWarlord extends GeneralMonster {

	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public OrcWarlord(int x, int y, int z) {
		super(x, y, z);
		super.setAttack(15);
		super.setDefense(7);
		super.setHp(80);
		super.setHpmax(80);
		super.setSpeed(130);
		super.setHitchance(90);
	}

	/**
	 * 
	 */
	public OrcWarlord() {
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
	 * @see entity.mons.GeneralMonster#difficulty()
	 */
	@Override
	public int difficulty() {
		// TODO Auto-generated method stub
		return 8;
	}

	/* (non-Javadoc)
	 * @see entity.Entity#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Orc Warlord";
	}

	/* (non-Javadoc)
	 * @see entity.Entity#getExp()
	 */
	@Override
	public int getExp() {
		// TODO Auto-generated method stub
		return 30;
	}

	/* (non-Javadoc)
	 * @see entity.Entity#draw()
	 */
	@Override
	public char draw() {
		// TODO Auto-generated method stub
		return 'O';
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return Color.RED;
	}

	@Override
	public Image getfIm() {
		// TODO Auto-generated method stub
		return ImageVars.orcwf;
	}

}
