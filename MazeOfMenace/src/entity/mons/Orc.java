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

import run.Init;
import run.turn.Turn;
import terminal.ImageVars;

/**
 * @author matthew
 *
 */
public class Orc extends GeneralMonster {
	private static Image im;
	static {
		try {
			im = ImageIO.read(new File("./dat/tiles/entity/orcwarlord.png"));
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
	public Orc(int x, int y, int z) {
		super(x, y, z);		
		super.setAttack(10);
		super.setHp(50);
		super.setHpmax(50);
		super.setSpeed(200);
		super.setHitchance(85);
	}
	

	

	@Override
	public int getAttack() {
		return 10;
	}




	/**
	 * 
	 */
	public Orc() {
		// TODO Auto-generated constructor stub
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
	
	public char draw(){
		return 'o';
	}

	/* (non-Javadoc)
	 * @see entity.Entity#getName()
	 */
	@Override
	public String getName() {
		return "Orc";
	}

	/* (non-Javadoc)
	 * @see entity.Entity#getExp()
	 */
	@Override
	public int getExp() {
		// TODO Auto-generated method stub
		return 20;
	}
	
	public int difficulty(){
		return 5;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return Color.RED;
	}

	@Override
	public Image getfIm() {
		// TODO Auto-generated method stub
		return ImageVars.orcf;
	}
	public int getTexId(){
		if(Init.useGL()) return TextureDB.getTexture("./dat/tiles/fancy/entity/orc.png").getTextureID();
		return 0;
	}
}
