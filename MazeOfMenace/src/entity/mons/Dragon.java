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

import engine.image.TextureDB;
import entity.Entity;
import entity.player.Display;

import run.Init;
import run.Util;
import run.turn.Turn;
import terminal.ImageVars;

/**
 * @author matthew
 *
 */
public class Dragon extends GeneralMonster {
	private static Image im;
	static {
		try {
			im = ImageIO.read(new File("./dat/tiles/entity/dragon.png"));
		} catch (Exception e) {

		}
	}
	public Image getImage(){
		return im;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public Dragon(int x, int y, int z) {
		super(x, y, z);
		super.setAttack(40);
		super.setDefense(20);
		super.setHp(250);
		super.setHpmax(250);
		super.setSpeed(90);
		super.setHitchance(90);
	}

	/**
	 * 
	 */
	public Dragon() {	}

	/* (non-Javadoc)
	 * @see entity.mons.GeneralMonster#queMove()
	 */
	@Override
	public void queMove() {
		Turn.addToTurnQue(this);
	}

	@Override
	protected void die(Entity e) {
		Display.addMsg("`rYou Absorb the dragon's !!soul!!`\n");
		super.die(e);
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
		return 11;
	}

	/* (non-Javadoc)
	 * @see entity.Entity#getName()
	 */
	@Override
	public String getName() {
		return "Dragon";
	}

	/* (non-Javadoc)
	 * @see entity.Entity#getExp()
	 */
	@Override
	public int getExp() {
		return 250;
	}

	/* (non-Javadoc)
	 * @see entity.Entity#draw()
	 */
	@Override
	public char draw() {
		return 'D';
	}

	@Override
	public Color getColor() {
		return Color.RED.darker().darker();
	}

	@Override
	public Image getfIm() {
		// TODO Auto-generated method stub
		return ImageVars.dragf;
	}
	public int getTexId(){
		if(Init.useGL()) return TextureDB.getTexture("./dat/tiles/fancy/entity/dragon.png").getTextureID();
		return 0;
	}
}
