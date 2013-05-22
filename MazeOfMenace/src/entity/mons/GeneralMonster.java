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

import run.Init;
import run.turn.Turn;
import dungeon.Level;
import dungeon.tile.Floor;
import entity.*;
import entity.player.*;
import java.io.Serializable;
import java.util.*;

/**
 * @author Matthew Gruda
 * 
 */
public abstract class GeneralMonster extends Entity implements Serializable {

	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int xPlayer, yPlayer;
	private MoveDirection direc;
	private static final Random r = new Random();

	/**
	 * 
	 */
	public GeneralMonster(int x, int y, int z) {
		super(x, y, z);
	}

	public GeneralMonster() {
	}

	private boolean findPlayer(int x, int y, Level lvl) {
		Player p = Init.getDungeon().getPlayer();
		xPlayer = p.getX();
		yPlayer = p.getY();
		if (xPlayer - getX() >= -4 && xPlayer - getX() <= 4)
			if (yPlayer - getY() >= -4 && yPlayer - getY() <= 4)
				return true;
		/*
		 * Unused junk for(int i = x - 4; i < x+4; i++){ for(int c = y - 4; c <
		 * y+4; c++){ if(i >= 0 && i <= 63 && c >= 0 && c <= 31){
		 * if(lvl.getlvl()[i][c].getCurrEntity() instanceof Player){ xPlayer =
		 * i; yPlayer = c; return true; } } } }
		 */
		return false;
	}

	/**
	 * @return the direc
	 */
	public MoveDirection getDirec() {
		return direc;
	}

	/**
	 * @param direc
	 *            the direc to set
	 */
	public void setDirec(MoveDirection direc) {
		this.direc = direc;
	}

	private MoveDirection playerDirection() {
		int xDiff = super.getX() - xPlayer, yDiff = super.getY() - yPlayer;
		if (yDiff > 0) {
			return MoveDirection.NORTH;
		} else if (yDiff < 0) {
			return MoveDirection.SOUTH;
		} else if (xDiff < 0) {
			return MoveDirection.EAST;
		}
		return MoveDirection.WEST;
	}

	private MoveDirection randomDirection() {
		int c = 0;
		while (true) {
			c++;
			switch (r.nextInt(4)) {
			case 0:
				direc = MoveDirection.EAST;
				break;
			case 1:
				direc = MoveDirection.NORTH;
				break;
			case 2:
				direc = MoveDirection.SOUTH;
				break;
			case 3:
				direc = MoveDirection.WEST;
				break;
			}
			if (super.canMove(direc) || c > 100) {
				return direc;
			}
		}
	}

	public abstract void queMove();

	public abstract void act();

	public void act(Entity e) {
		if (!super.notDead()) {
			return;
		}
		super.setMoveFlag(false);
		if (!findPlayer(super.getX(), super.getY(), Init.getDungeon()
				.getCurrentLevelObj())) {
			direc = randomDirection();
		} else {
			direc = playerDirection();
			if (canAttack(direc)) {
				super.setMoveFlag(true);
			} else if (!super.canMove(direc)) {
				direc = randomDirection();
			}
		}
		if (super.isMoveFlag() && super.canAttack(direc)) {
			super.attack(getDirec(), e);
		} else if (super.canMove(direc)) {
			super.move(getDirec(), e);
		}

	}

	public boolean canAttack(MoveDirection direction) {
		switch (direction) {
		case EAST:
			if (Init.getDungeon().getCurrentLevelObj().getlvl()[super.getX() + 1][super
					.getY()].getBasetile() instanceof Floor
					&& Init.getDungeon().getCurrentLevelObj().getlvl()[super
							.getX() + 1][super.getY()].getCurrEntity() instanceof Player)
				return true;
		case NORTH:
			if (Init.getDungeon().getCurrentLevelObj().getlvl()[super.getX()][super
					.getY() - 1].getBasetile() instanceof Floor
					&& Init.getDungeon().getCurrentLevelObj().getlvl()[super
							.getX()][super.getY() - 1].getCurrEntity() instanceof Player)
				return true;
		case SOUTH:
			if (Init.getDungeon().getCurrentLevelObj().getlvl()[super.getX()][super
					.getY() + 1].getBasetile() instanceof Floor
					&& Init.getDungeon().getCurrentLevelObj().getlvl()[super
							.getX()][super.getY() + 1].getCurrEntity() instanceof Player)
				return true;
			break;
		case WEST:
			if (Init.getDungeon().getCurrentLevelObj().getlvl()[super.getX() - 1][super
					.getY()].getBasetile() instanceof Floor
					&& Init.getDungeon().getCurrentLevelObj().getlvl()[super
							.getX() - 1][super.getY()].getCurrEntity() instanceof Player)
				return true;
			break;
		default:
			break;
		}
		return false;
	}

	public abstract int difficulty();

	protected void die(Entity e) {
		if (e instanceof GeneralMonster) {
			GeneralMonster g = (GeneralMonster) e;
			EntityCalcs.createDropWeapon(g.difficulty(), g.getLoc());
		}
		super.die(e);
	}

}
