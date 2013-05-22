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
package entity.mons;

import java.awt.Color;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

import run.turn.Turn;
import terminal.ImageVars;

public class AncientDragon extends GeneralMonster {
	private static Image im;
	static {
		try {
			im = ImageIO.read(new File("./dat/tiles/entity/olddragon.png"));
		} catch (Exception e) {

		}
	}
	public Image getImage(){
		return im;
	}

	public AncientDragon(int x, int y, int z) {
		super(x, y, z);
		super.setAttack(100);
		super.setDefense(40);
		super.setHp(350);
		super.setHpmax(350);
		super.setSpeed(95);
		super.setHitchance(95);
	}

	public AncientDragon() {
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
	public int difficulty() {
		return 15;
	}

	@Override
	public String getName() {
		return "Ancient Dragon";
	}

	@Override
	public int getExp() {
		return 500;
	}

	@Override
	public char draw() {
		return 'D';
	}

	@Override
	public Color getColor() {
		return Color.RED.brighter().brighter();
	}

	@Override
	public Image getfIm() {
		// TODO Auto-generated method stub
		return ImageVars.adragf;
	}

}
