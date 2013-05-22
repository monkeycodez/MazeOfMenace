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
package entity.player;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.Serializable;
import java.util.Random;

import javax.imageio.ImageIO;

import dungeon.tile.*;
import dungeon.*;
import entity.player.inventory.*;
import entity.*;
import run.Init;
import run.Util;
import run.turn.Turn;
import terminal.ImageVars;
import terminal.gld.GLDungeonDraw;
import terminal.gld.event.GLDPMoveEvent;
import terminal.gld.event.GLDTurnEvent;

/**
 * @author matthew
 * 
 */
public class Player extends Entity implements Serializable {
	
	private static Image playerim;
	static{
		try{
			playerim =ImageIO.read(new File("./dat/tiles/entity/player.png"));	
		}catch(Exception e){
			
		}
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 */
	private Inventory indv;
	private int regenCounter = 0;
	private final int regenTgt = 3;
	/**
	 * stores the direction to move in
	 */
	private MoveDirection dirToMove = MoveDirection.EAST, facing = MoveDirection.NORTH;
	private int level, exp;
	private double toNext = 15;

	public Player(int x, int y, int z) {
		super(x, y, z);
		this.indv = new Inventory(this);
		super.setSpeed(100);
		super.setHp(100);
		super.setHpmax(100);
		super.setHitchance(90);
		super.setAttack(5);
		super.setDefense(1);
		this.level = 1;
	}

	/**
	 * intended to be called by parseMovement in NormalModeParse this tests the
	 * move, and if it is true, it ques the move
	 * 
	 * @param direc
	 *            direction to move in
	 * @return if move is valid
	 */
	public boolean makeMove(MoveDirection direc) {
		// System.out.println("makeMove called");
		
		if(Init.useGL()){
			switch(facing){
			case EAST:
				direc = direc.rightdir();
				break;
			case NORTH: 
				break;
			case SOUTH:	direc = direc.leftdir().leftdir();
				break;
			case WEST:
				direc = direc.leftdir();
				break;			
			}
			//System.out.println(dirToMove);
		}
		
		super.setMoveFlag(false);
		if (super.canMove(direc)) {
			this.queMove(direc);
			return true;
		} else if (super.canAttack(direc)) {
			this.queMove(direc);
			super.setMoveFlag(true);
			return true;
		}
		return false;
	}

	/**
	 * move for switching levels;
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void moveAnywhere(int x, int y, int z, int oldZ) {
		Location oldLoc = super.getLoc();
		Init
				.getDungeon().getLevel(z);
		Init.getDungeon().getLevel(oldZ).getlvl()[oldLoc.getX()][oldLoc.getY()]
				.setCurrEntity(null);
		Init.getDungeon().getLevel(z).getlvl()[x][y].setCurrEntity(this);
		super.setLoc(new Location(x, y));
	}

	/**
	 * ques the move for makeMove
	 * 
	 * @param direc
	 *            direction to move
	 */
	private void queMove(MoveDirection direc) {
		Turn.addToTurnQue(this);
		dirToMove = direc;
		
	}

	/**
	 * called by Turn.turn
	 */
	@Override
	public void act() {
		if (super.isMoveFlag() && super.canAttack(this.dirToMove)) {
			this.attack(this.dirToMove, this);
			return;
		} else if (super.canMove(this.dirToMove)){
			if(Init.useGL()){
				int ox = getX(), oy = getY();
				int nx = ox, ny = oy;
				switch (dirToMove){
				case EAST: nx--;
					break;
				case NORTH: ny++;
					break;
				case SOUTH: ny--;
					break;
				case WEST: nx++;
					break;
				default:
					break;
				}
				GLDungeonDraw.addEvent(new GLDPMoveEvent(ox, oy, nx, ny));
			}
			this.move(this.dirToMove, this);
		}
//		System.out.println("facing: "+ facing);
	}

	
	public void regenControl() {
		this.regenCounter++;
		if (this.regenCounter > this.regenTgt) {
			if (this.getHp() < this.getHpmax())
				super.setHp(super.getHp() + 1);
			this.regenCounter = 0;
		}
	}

	/**
	 * send char for Tile to draw
	 */
	@Override
	public char draw() {
		// Display.display();
		return '@';
	}

	public Inventory getIndv() {
		return this.indv;
	}

	public void setIndv(Inventory indv) {
		this.indv = indv;
	}

	public void makeLOS() {
		Level lvl = Init.getDungeon().getCurrentLevelObj();
		// start off by making the level unseen
		lvl.hide();
		//
		this.show(lvl);
	}

	/**
	 * 
	 * @param lvl
	 */
	private void show(Level lvl) {
		int x = this.getX(), y = this.getY();
		lvl.getlvl()[x][y].setInView(true);
		lvl.getlvl()[x][y].setSeen(true);
		Tile[][] l = lvl.getlvl();
		for (int i = x - 1; i <= x + 1; i++)
			for (int c = y - 1; c <= y + 1; c++)
				if (i >= 0 && i <= 63 && c >= 0 && c <= 31) {
					lvl.getlvl()[i][c].setInView(true);
					lvl.getlvl()[i][c].setSeen(true);
				}
		// east
		if (lvl.getlvl()[x + 1][y].getBasetile() instanceof Floor) {
			lvl.getlvl()[x + 2][y].setInView(true);
			lvl.getlvl()[x + 2][y].setSeen(true);
			lvl.getlvl()[x + 2][y + 1].setInView(true);
			lvl.getlvl()[x + 2][y + 1].setSeen(true);
			lvl.getlvl()[x + 2][y - 1].setInView(true);
			lvl.getlvl()[x + 2][y - 1].setSeen(true);
			// center
			if (l[x + 2][y].getBasetile() instanceof Floor) {
				lvl.getlvl()[x + 3][y].setInView(true);
				lvl.getlvl()[x + 3][y].setSeen(true);
				lvl.getlvl()[x + 3][y + 1].setInView(true);
				lvl.getlvl()[x + 3][y + 1].setSeen(true);
				lvl.getlvl()[x + 3][y - 1].setInView(true);
				lvl.getlvl()[x + 3][y - 1].setSeen(true);
				if (l[x + 3][y].getBasetile() instanceof Floor) {
					lvl.getlvl()[x + 4][y].setInView(true);
					lvl.getlvl()[x + 4][y].setSeen(true);
				}
				if (l[x + 3][y - 1].getBasetile() instanceof Floor) {
					lvl.getlvl()[x + 4][y - 1].setInView(true);
					lvl.getlvl()[x + 4][y - 1].setSeen(true);
					lvl.getlvl()[x + 4][y - 2].setInView(true);
					lvl.getlvl()[x + 4][y - 2].setSeen(true);
				}
			}// up
			if (l[x + 2][y - 1].getBasetile() instanceof Floor) {
				lvl.getlvl()[x + 3][y - 1].setInView(true);
				lvl.getlvl()[x + 3][y - 1].setSeen(true);
				lvl.getlvl()[x + 3][y - 2].setInView(true);
				lvl.getlvl()[x + 3][y - 2].setSeen(true);
				if (l[x + 3][y - 1].getBasetile() instanceof Floor) {
					lvl.getlvl()[x + 4][y - 2].setInView(true);
					lvl.getlvl()[x + 4][y - 2].setSeen(true);
					lvl.getlvl()[x + 4][y - 1].setInView(true);
					lvl.getlvl()[x + 4][y - 1].setSeen(true);

				}
				if (l[x + 3][y - 2].getBasetile() instanceof Floor) {
					lvl.getlvl()[x + 4][y - 3].setInView(true);
					lvl.getlvl()[x + 4][y - 3].setSeen(true);
					lvl.getlvl()[x + 4][y - 2].setInView(true);
					lvl.getlvl()[x + 4][y - 2].setSeen(true);
				}
			}// down
			if (l[x + 2][y + 1].getBasetile() instanceof Floor) {
				lvl.getlvl()[x + 3][y + 1].setInView(true);
				lvl.getlvl()[x + 3][y + 1].setSeen(true);
				lvl.getlvl()[x + 3][y + 2].setInView(true);
				lvl.getlvl()[x + 3][y + 2].setSeen(true);
				if (l[x + 3][y + 1].getBasetile() instanceof Floor) {
					lvl.getlvl()[x + 4][y + 2].setInView(true);
					lvl.getlvl()[x + 4][y + 2].setSeen(true);
					lvl.getlvl()[x + 4][y + 1].setInView(true);
					lvl.getlvl()[x + 4][y + 1].setSeen(true);

				}
				if (l[x + 3][y + 2].getBasetile() instanceof Floor) {
					lvl.getlvl()[x + 4][y + 3].setInView(true);
					lvl.getlvl()[x + 4][y + 3].setSeen(true);
					lvl.getlvl()[x + 4][y + 2].setInView(true);
					lvl.getlvl()[x + 4][y + 2].setSeen(true);
				}
			}
		}
		// west
		if (lvl.getlvl()[x - 1][y].getBasetile() instanceof Floor) {
			lvl.getlvl()[x - 2][y].setInView(true);
			lvl.getlvl()[x - 2][y].setSeen(true);
			lvl.getlvl()[x - 2][y + 1].setInView(true);
			lvl.getlvl()[x - 2][y + 1].setSeen(true);
			lvl.getlvl()[x - 2][y - 1].setInView(true);
			lvl.getlvl()[x - 2][y - 1].setSeen(true);
			// center
			if (l[x - 2][y].getBasetile() instanceof Floor) {
				lvl.getlvl()[x - 3][y].setInView(true);
				lvl.getlvl()[x - 3][y].setSeen(true);
				lvl.getlvl()[x - 3][y + 1].setInView(true);
				lvl.getlvl()[x - 3][y + 1].setSeen(true);
				lvl.getlvl()[x - 3][y - 1].setInView(true);
				lvl.getlvl()[x - 3][y - 1].setSeen(true);
				if (l[x - 3][y].getBasetile() instanceof Floor) {
					lvl.getlvl()[x - 4][y].setInView(true);
					lvl.getlvl()[x - 4][y].setSeen(true);
				}
				if (l[x - 3][y - 1].getBasetile() instanceof Floor) {
					lvl.getlvl()[x - 4][y - 1].setInView(true);
					lvl.getlvl()[x - 4][y - 1].setSeen(true);
					lvl.getlvl()[x - 4][y - 2].setInView(true);
					lvl.getlvl()[x - 4][y - 2].setSeen(true);
				}
			}// up
			if (l[x - 2][y - 1].getBasetile() instanceof Floor) {
				lvl.getlvl()[x - 3][y - 1].setInView(true);
				lvl.getlvl()[x - 3][y - 1].setSeen(true);
				lvl.getlvl()[x - 3][y - 2].setInView(true);
				lvl.getlvl()[x - 3][y - 2].setSeen(true);
				if (l[x - 3][y - 1].getBasetile() instanceof Floor) {
					lvl.getlvl()[x - 4][y - 2].setInView(true);
					lvl.getlvl()[x - 4][y - 2].setSeen(true);
					lvl.getlvl()[x - 4][y - 1].setInView(true);
					lvl.getlvl()[x - 4][y - 1].setSeen(true);

				}
				if (l[x - 3][y - 2].getBasetile() instanceof Floor) {
					lvl.getlvl()[x - 4][y - 3].setInView(true);
					lvl.getlvl()[x - 4][y - 3].setSeen(true);
					lvl.getlvl()[x - 4][y - 2].setInView(true);
					lvl.getlvl()[x - 4][y - 2].setSeen(true);
				}
			}// down
			if (l[x - 2][y + 1].getBasetile() instanceof Floor) {
				lvl.getlvl()[x - 3][y + 1].setInView(true);
				lvl.getlvl()[x - 3][y + 1].setSeen(true);
				lvl.getlvl()[x - 3][y + 2].setInView(true);
				lvl.getlvl()[x - 3][y + 2].setSeen(true);
				if (l[x - 3][y + 1].getBasetile() instanceof Floor) {
					lvl.getlvl()[x - 4][y + 2].setInView(true);
					lvl.getlvl()[x - 4][y + 2].setSeen(true);
					lvl.getlvl()[x - 4][y + 1].setInView(true);
					lvl.getlvl()[x - 4][y + 1].setSeen(true);

				}
				if (l[x - 3][y + 2].getBasetile() instanceof Floor) {
					lvl.getlvl()[x - 4][y + 3].setInView(true);
					lvl.getlvl()[x - 4][y + 3].setSeen(true);
					lvl.getlvl()[x - 4][y + 2].setInView(true);
					lvl.getlvl()[x - 4][y + 2].setSeen(true);
				}
			}
		}

		// south
		if (lvl.getlvl()[x][y + 1].getBasetile() instanceof Floor) {
			lvl.getlvl()[x][y + 2].setInView(true);
			lvl.getlvl()[x][y + 2].setSeen(true);
			lvl.getlvl()[x - 1][y + 2].setInView(true);
			lvl.getlvl()[x - 1][y + 2].setSeen(true);
			lvl.getlvl()[x + 1][y + 2].setInView(true);
			lvl.getlvl()[x + 1][y + 2].setSeen(true);
			// center
			if (l[x][y + 2].getBasetile() instanceof Floor) {
				lvl.getlvl()[x][y + 3].setInView(true);
				lvl.getlvl()[x][y + 3].setSeen(true);
				lvl.getlvl()[x + 1][y + 3].setInView(true);
				lvl.getlvl()[x + 1][y + 3].setSeen(true);
				lvl.getlvl()[x - 1][y + 3].setInView(true);
				lvl.getlvl()[x - 1][y + 3].setSeen(true);
				if (l[x][y + 3].getBasetile() instanceof Floor) {
					lvl.getlvl()[x][y + 4].setInView(true);
					lvl.getlvl()[x][y + 4].setSeen(true);
				}
				if (l[x - 1][y + 3].getBasetile() instanceof Floor) {
					lvl.getlvl()[x - 1][y + 4].setInView(true);
					lvl.getlvl()[x - 1][y + 4].setSeen(true);
					lvl.getlvl()[x - 2][y + 4].setInView(true);
					lvl.getlvl()[x - 2][y + 4].setSeen(true);
				}
			}// up
			if (l[x - 1][y + 2].getBasetile() instanceof Floor) {
				lvl.getlvl()[x - 1][y + 3].setInView(true);
				lvl.getlvl()[x - 1][y + 3].setSeen(true);
				lvl.getlvl()[x - 2][y + 3].setInView(true);
				lvl.getlvl()[x - 1][y + 3].setSeen(true);
				if (l[x - 1][y + 3].getBasetile() instanceof Floor) {
					lvl.getlvl()[x - 2][y + 4].setInView(true);
					lvl.getlvl()[x - 2][y + 4].setSeen(true);
					lvl.getlvl()[x - 1][y + 4].setInView(true);
					lvl.getlvl()[x - 1][y + 4].setSeen(true);

				}
				if (l[x - 2][y + 3].getBasetile() instanceof Floor) {
					lvl.getlvl()[x - 3][y + 4].setInView(true);
					lvl.getlvl()[x - 3][y + 4].setSeen(true);
					lvl.getlvl()[x - 2][y + 4].setInView(true);
					lvl.getlvl()[x - 2][y + 4].setSeen(true);
				}
			}// down
			if (l[x + 1][y + 2].getBasetile() instanceof Floor) {
				lvl.getlvl()[x + 1][y + 3].setInView(true);
				lvl.getlvl()[x + 1][y + 3].setSeen(true);
				lvl.getlvl()[x + 2][y + 3].setInView(true);
				lvl.getlvl()[x + 2][y + 3].setSeen(true);
				if (l[x + 1][y + 3].getBasetile() instanceof Floor) {
					lvl.getlvl()[x + 2][y + 4].setInView(true);
					lvl.getlvl()[x + 2][y + 4].setSeen(true);
					lvl.getlvl()[x + 1][y + 4].setInView(true);
					lvl.getlvl()[x + 1][y + 4].setSeen(true);

				}
				if (l[x + 2][y + 3].getBasetile() instanceof Floor) {
					lvl.getlvl()[x + 3][y + 4].setInView(true);
					lvl.getlvl()[x + 3][y + 4].setSeen(true);
					lvl.getlvl()[x + 2][y + 4].setInView(true);
					lvl.getlvl()[x + 2][y + 4].setSeen(true);
				}
			}
		}

		// north
		if (lvl.getlvl()[x][y - 1].getBasetile() instanceof Floor) {
			lvl.getlvl()[x][y - 2].setInView(true);
			lvl.getlvl()[x][y - 2].setSeen(true);
			lvl.getlvl()[x - 1][y - 2].setInView(true);
			lvl.getlvl()[x - 1][y - 2].setSeen(true);
			lvl.getlvl()[x + 1][y - 2].setInView(true);
			lvl.getlvl()[x + 1][y - 2].setSeen(true);
			// center
			if (l[x][y - 2].getBasetile() instanceof Floor) {
				lvl.getlvl()[x][y - 3].setInView(true);
				lvl.getlvl()[x][y - 3].setSeen(true);
				lvl.getlvl()[x + 1][y - 3].setInView(true);
				lvl.getlvl()[x + 1][y - 3].setSeen(true);
				lvl.getlvl()[x - 1][y - 3].setInView(true);
				lvl.getlvl()[x - 1][y - 3].setSeen(true);
				if (l[x][y - 3].getBasetile() instanceof Floor) {
					lvl.getlvl()[x][y - 4].setInView(true);
					lvl.getlvl()[x][y - 4].setSeen(true);
				}
				if (l[x - 1][y - 3].getBasetile() instanceof Floor) {
					lvl.getlvl()[x - 1][y - 4].setInView(true);
					lvl.getlvl()[x - 1][y - 4].setSeen(true);
					lvl.getlvl()[x - 2][y - 4].setInView(true);
					lvl.getlvl()[x - 2][y - 4].setSeen(true);
				}
			}// up
			if (l[x - 1][y - 2].getBasetile() instanceof Floor) {
				lvl.getlvl()[x - 1][y - 3].setInView(true);
				lvl.getlvl()[x - 1][y - 3].setSeen(true);
				lvl.getlvl()[x - 2][y - 3].setInView(true);
				lvl.getlvl()[x - 1][y - 3].setSeen(true);
				if (l[x - 1][y - 3].getBasetile() instanceof Floor) {
					lvl.getlvl()[x - 2][y - 4].setInView(true);
					lvl.getlvl()[x - 2][y - 4].setSeen(true);
					lvl.getlvl()[x - 1][y - 4].setInView(true);
					lvl.getlvl()[x - 1][y - 4].setSeen(true);

				}
				if (l[x - 2][y - 3].getBasetile() instanceof Floor) {
					lvl.getlvl()[x - 3][y - 4].setInView(true);
					lvl.getlvl()[x - 3][y - 4].setSeen(true);
					lvl.getlvl()[x - 2][y - 4].setInView(true);
					lvl.getlvl()[x - 2][y - 4].setSeen(true);
				}
			}// down
			if (l[x + 1][y - 2].getBasetile() instanceof Floor) {
				lvl.getlvl()[x + 1][y - 3].setInView(true);
				lvl.getlvl()[x + 1][y - 3].setSeen(true);
				lvl.getlvl()[x + 2][y - 3].setInView(true);
				lvl.getlvl()[x + 2][y - 3].setSeen(true);
				if (l[x + 1][y - 3].getBasetile() instanceof Floor) {
					lvl.getlvl()[x + 2][y - 4].setInView(true);
					lvl.getlvl()[x + 2][y - 4].setSeen(true);
					lvl.getlvl()[x + 1][y - 4].setInView(true);
					lvl.getlvl()[x + 1][y - 4].setSeen(true);

				}
				if (l[x + 2][y - 3].getBasetile() instanceof Floor) {
					lvl.getlvl()[x + 3][y - 4].setInView(true);
					lvl.getlvl()[x + 3][y - 4].setSeen(true);
					lvl.getlvl()[x + 2][y - 4].setInView(true);
					lvl.getlvl()[x + 2][y - 4].setSeen(true);
				}
			}
		}

		// diagonals
		// top left
		if (l[x + 1][y - 1].getBasetile() instanceof Floor) {
			lvl.getlvl()[x + 2][y - 2].setInView(true);
			lvl.getlvl()[x + 2][y - 2].setSeen(true);
			if (l[x + 2][y - 2].getBasetile() instanceof Floor) {
				lvl.getlvl()[x + 3][y - 3].setInView(true);
				lvl.getlvl()[x + 3][y - 3].setSeen(true);
				if (l[x + 3][y - 3].getBasetile() instanceof Floor) {
					lvl.getlvl()[x + 4][y - 4].setInView(true);
					lvl.getlvl()[x + 4][y - 4].setSeen(true);
				}
			}
		}
		// top right
		if (l[x - 1][y + 1].getBasetile() instanceof Floor) {
			lvl.getlvl()[x - 2][y + 2].setInView(true);
			lvl.getlvl()[x - 2][y + 2].setSeen(true);
			if (l[x - 2][y + 2].getBasetile() instanceof Floor) {
				lvl.getlvl()[x - 3][y + 3].setInView(true);
				lvl.getlvl()[x - 3][y + 3].setSeen(true);
				if (l[x - 3][y + 3].getBasetile() instanceof Floor) {
					lvl.getlvl()[x - 4][y + 4].setInView(true);
					lvl.getlvl()[x - 4][y + 4].setSeen(true);
				}
			}
		}
		// botom right
		if (l[x + 1][y + 1].getBasetile() instanceof Floor) {
			lvl.getlvl()[x + 2][y + 2].setInView(true);
			lvl.getlvl()[x + 2][y + 2].setSeen(true);
			if (l[x + 2][y + 2].getBasetile() instanceof Floor) {
				lvl.getlvl()[x + 3][y + 3].setInView(true);
				lvl.getlvl()[x + 3][y + 3].setSeen(true);
				if (l[x + 3][y + 3].getBasetile() instanceof Floor) {
					lvl.getlvl()[x + 4][y + 4].setInView(true);
					lvl.getlvl()[x + 4][y + 4].setSeen(true);
				}
			}
		}
		// botoom left
		if (l[x - 1][y - 1].getBasetile() instanceof Floor) {
			lvl.getlvl()[x - 2][y - 2].setInView(true);
			lvl.getlvl()[x - 2][y - 2].setSeen(true);
			if (l[x - 2][y - 2].getBasetile() instanceof Floor) {
				lvl.getlvl()[x - 3][y - 3].setInView(true);
				lvl.getlvl()[x - 3][y - 3].setSeen(true);
				if (l[x - 3][y - 3].getBasetile() instanceof Floor) {
					lvl.getlvl()[x - 4][y - 4].setInView(true);
					lvl.getlvl()[x - 4][y - 4].setSeen(true);
				}
			}
		}
	}

	public int getLevel() {
		return this.level;
	}

	public void addLevel() {
		this.level += 1;
		super.setHpmax(super.getHpmax() + 2);
		super.setHp(this.getHpmax());
		super.setAttack(this.getAttack() + 2);
		super.setDefense(this.getDefense() + 1);
		Display.addMsg("`cYou Level up!!!`\n");
	}

	@Override
	public String getName() {
		return "you";
	}

	private void addExp(int e) {
		this.exp += e;
		this.chkLvl();
	}

	private void chkLvl() {
		int to = (int) this.toNext;
		if (this.exp >= to) {
			this.addLevel();
			this.exp = 0;
			this.toNext = this.toNext * 1.5;
		}
	}

	@Override
	public void attack(MoveDirection direc, Entity ths) {
		Entity other = super.getTarget(direc, ths);
		ths.getAttack();
		if (ths.getHitchance() < new Random().nextInt(100) + 1) {
			Display.addMsg("`oYou miss the " + other.getName() + "`\n");
			return;
		} else {
			EntityCalcs.createDisplayMsg(this, other,
					EntityCalcs.damage(ths, other));
			if (other.checkDeath(other)) {
				int exp = other.getExp();
				this.addExp(exp);
			}
		}
	}

	@Override
	public int getExp() {
		return 0;
	}

	@Override
	protected void die(Entity e) {
		super.die(e);
		Util.kill("dat/deathMsg", true);
	}

	@Override
	public Color getColor() {
		return Color.blue.brighter().brighter().brighter();
	}

	@Override
	public int defenseAbsorbModifier() {
		return super.defenseAbsorbModifier() + this.indv.getStaticDef();
	}

	@Override
	public int attackStaticModifier() {
		return super.attackStaticModifier() + this.indv.getStaticAtk();
	}
	
	public boolean hasOrb(){
		if(indv.getOrb() != null)
			return true;
		return false;
	}
	
	@Override
	public Image getImage() {
		return playerim;
	}
	
	public Image getfIm(){
		return ImageVars.playerf;
	}

	public void turnLeft(){
		GLDungeonDraw.addEvent(new GLDTurnEvent(facing, facing.leftdir()));
		facing = facing.leftdir();
	}
	
	public void turnRight(){
		GLDungeonDraw.addEvent(new GLDTurnEvent(facing, facing.rightdir()));
		facing = facing.rightdir();
	}
	
	public MoveDirection getFacing(){
		return facing;
	}
	

}
