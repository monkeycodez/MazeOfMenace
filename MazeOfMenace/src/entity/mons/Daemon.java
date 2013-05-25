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
import java.util.*;

import javax.imageio.ImageIO;
import engine.image.TextureDB;

import run.Init;
import run.turn.Turn;
import terminal.ImageVars;

/**
 * @author matthew
 * 
 */
public class Daemon extends GeneralMonster {

	private static Image im0, im1, im2, im3, im4, im5, im6, im7;
	static {
		try {
			im0 = ImageIO.read(new File("./dat/tiles/entity/demon/0.png"));
			im1 = ImageIO.read(new File("./dat/tiles/entity/demon/1.png"));
			im2 = ImageIO.read(new File("./dat/tiles/entity/demon/2.png"));
			im3 = ImageIO.read(new File("./dat/tiles/entity/demon/3.png"));
			im4 = ImageIO.read(new File("./dat/tiles/entity/demon/4.png"));
			im5 = ImageIO.read(new File("./dat/tiles/entity/demon/5.png"));
			im6 = ImageIO.read(new File("./dat/tiles/entity/demon/6.png"));
			im7 = ImageIO.read(new File("./dat/tiles/entity/demon/7.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Color col;
	private static Random r = new Random();
	private int exp;
	private Image im;

	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public Daemon(int x, int y, int z) {
		super(x, y, z);
		super.setAttack(r.nextInt(35));
		super.setDefense(r.nextInt(25));
		int hp = r.nextInt(200);
		super.setHp(r.nextInt(hp));
		super.setHpmax(hp);
		super.setSpeed(90);
		super.setHitchance(50 + r.nextInt(50));
		switch (r.nextInt(8)) {
		case 0:
			im = im0;
			break;
		case 1:
			im = im1;
			break;
		case 2:
			im = im2;
			break;
		case 3:
			im = im3;
			break;
		case 4:
			im = im4;
			break;
		case 5:
			im = im5;
			break;
		case 6:
			im = im6;
			break;
		case 7:
			im = im7;
			break;
		}
		switch (r.nextInt(11)) {
		case 0:
			col = Color.BLUE;
			break;
		case 1:
			col = Color.CYAN;
			break;
		case 2:
			col = Color.gray;
			break;
		case 3:
			col = Color.darkGray;
			break;
		case 4:
			col = Color.GREEN;
			break;
		case 5:
			col = Color.LIGHT_GRAY;
			break;
		case 6:
			col = Color.MAGENTA;
			break;
		case 7:
			col = Color.ORANGE;
			break;
		case 8:
			col = Color.PINK;
			break;
		case 9:
			col = Color.RED;
			break;
		case 10:
			col = Color.YELLOW;
			break;
		}
		exp = r.nextInt(300);
	}

	/**
	 * 
	 */
	public Daemon() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see entity.mons.GeneralMonster#queMove()
	 */
	@Override
	public void queMove() {
		Turn.addToTurnQue(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see entity.mons.GeneralMonster#act()
	 */
	@Override
	public void act() {
		super.act(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see entity.mons.GeneralMonster#difficulty()
	 */
	@Override
	public int difficulty() {
		return 42;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see entity.Entity#getName()
	 */
	@Override
	public String getName() {
		return "Daemon";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see entity.Entity#getExp()
	 */
	@Override
	public int getExp() {
		return exp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see entity.Entity#draw()
	 */
	@Override
	public char draw() {
		return '&';
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dungeon.tile.AbstractObject#getColor()
	 */
	@Override
	public Color getColor() {
		return col;
	}
	
	public Image getImage(){
		return im;
	}

	@Override
	public Image getfIm() {
		// TODO Auto-generated method stub
		return ImageVars.daemonf;
	}
	public int getTexId(){
		if(Init.useGL()) return TextureDB.getTexture("./dat/tiles/fancy/entity/daemon.png").getTextureID();
		return 0;
	}
}
