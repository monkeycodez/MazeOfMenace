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
import java.io.File;

import javax.imageio.ImageIO;

import run.turn.Turn;
import terminal.ImageVars;
import entity.*;

/**
 * @author Matthew Gruda
 * 
 */
public class Goblin extends GeneralMonster {

	private static Image gobIm;
	static {
		try {
			gobIm = ImageIO.read(new File("./dat/tiles/entity/goblin.png"));
		} catch (Exception e) {

		}
	}

	@Override
	public Image getImage() {
		return gobIm;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final String name = "Goblin";
	private int exp = 10;

	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public Goblin(int x, int y, int z) {
		super(x, y, z);
		super.setAttack(5);
		super.setHp(15);
		super.setHpmax(15);
		super.setSpeed(80);
		super.setHitchance(80);
		this.exp = 10;
	}

	@Override
	public int getExp() {
		return this.exp;
	}

	/**
	 * 
	 */
	public Goblin() {
	}

	@Override
	public void queMove() {
		Turn.addToTurnQue(this);
	}

	@Override
	public void act() {
		super.act(this);
	}

	@Override
	public char draw() {
		return 'g';
	}

	@Override
	public String getName() {
		return "goblin";
	}

	@Override
	public int difficulty() {
		return 2;
	}

	@Override
	public Color getColor() {
		return Color.cyan;
	}

	@Override
	public Image getfIm() {
		return ImageVars.goblinf;
	}

}
