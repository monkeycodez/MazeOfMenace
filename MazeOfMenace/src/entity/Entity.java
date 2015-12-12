/*******************************************************************************
 * Copyright (c) 2012, 2013 Matthew Gruda
 * 
 * This file is part of Maze Of Menace.
 * 
 * Maze Of Menace is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Maze Of Menace is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 * Matthew Gruda- initial API and implementation
 ******************************************************************************/
/**
 * 
 */
package entity;

import java.io.Serializable;
import java.util.Random;
import run.Init;
import run.turn.Turn;
import dungeon.Level;
import dungeon.tile.AbstractObject;
import dungeon.tile.Floor;
import dungeon.tile.Location;
import engine.image.TextureDB;
import entity.player.Display;

/**
 * monsters and player extend this provides basic movement with move()
 * 
 * @author matthew
 * 
 */
public abstract class Entity extends AbstractObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int speed = 100000;

	private boolean moveFlag = false, notDead = true;

	private StatComponent stat = new StatComponent();

	public Entity(int x, int y, Level lvl) {
		lvl.getlvl()[x][y]
			.setCurrEntity(this);
	}

	public Entity() {
	}

	public static void foo(){
	}

	/**
	 * the basic move method. it changes the Tile.objects array and the
	 * Tile.currEntity any class extending this should test direction first to
	 * ensure there is no wall, ect in way.
	 * 
	 * @param direction
	 *            direction to move in 
	 */
	public void move( MoveDirection direction, AbstractObject obj ){
		Location oldLoc = super.getLoc(), newLoc;
		switch(direction){
			case NORTH:
				newLoc = new Location(
					super.getX(),
					super.getY() - 1);
				break;
			case SOUTH:
				newLoc = new Location(
					super.getX(),
					super.getY() + 1);
				break;
			case WEST:
				newLoc = new Location(
					super.getX() - 1,
					super.getY());
				break;
			case EAST:
				newLoc = new Location(
					super.getX() + 1,
					super.getY());
				break;
			default:
				newLoc = new Location(
					super.getX(),
					super.getY());
		}
		super.setLoc(newLoc);
		Init.getDungeon().getLevel(getZ()).getlvl()[oldLoc.getX()][oldLoc
		                                                           .getY()].setCurrEntity(null);
		Init.getDungeon().getLevel(getZ()).getlvl()[newLoc.getX()][newLoc
		                                                           .getY()].setCurrEntity(this);
	}

	/**
	 * used to test the direction of travel for obsacles
	 * 
	 * @param direction
	 *            the direction to move in
	 */
	public boolean canMove( MoveDirection direction ){
		//		System.out.println("testing: "+direction);
		switch(direction){
			case EAST:
				if(Init.getDungeon().getCurrentLevelObj()
					.getlvl()[super.getX() + 1][super
					.getY()].getBasetile() instanceof Floor
					&&
					                            Init.getDungeon().getCurrentLevelObj()
					                            .getlvl()[super
					                                      .getX() + 1][super.getY()]
					                                	      .getCurrEntity() == null){
					return true;
				}
				break;
			case NORTH:
				if(Init.getDungeon().getCurrentLevelObj()
					.getlvl()[super.getX()][super
					.getY() - 1].getBasetile() instanceof Floor
					&&
					                        Init.getDungeon().getCurrentLevelObj()
					                        .getlvl()[super
					                                  .getX()][super.getY() - 1]
					                                	  .getCurrEntity() == null){
					return true;
				}
				break;
			case SOUTH:
				if(Init.getDungeon().getCurrentLevelObj()
					.getlvl()[super.getX()][super
					.getY() + 1].getBasetile() instanceof Floor
					&&
					                        Init.getDungeon().getCurrentLevelObj()
					                        .getlvl()[super
					                                  .getX()][super.getY() + 1]
					                                	  .getCurrEntity() == null){
					return true;
				}
				break;
			case WEST:
				if(Init.getDungeon().getCurrentLevelObj()
					.getlvl()[super.getX() - 1][super
					.getY()].getBasetile() instanceof Floor
					&&
					                            Init.getDungeon().getCurrentLevelObj()
					                            .getlvl()[super
					                                      .getX() - 1][super.getY()]
					                                	      .getCurrEntity() == null){
					return true;
				}
				break;
			default:
				break;
		}
		//	System.out.println("cant move:"+direction);
		return false;
	}

	public abstract String getName();

	/**
	 * 
	 * @param direction
	 * @return
	 */
	public boolean canAttack( MoveDirection direction ){
		switch(direction){
			case EAST:
				if(Init.getDungeon().getCurrentLevelObj()
					.getlvl()[super.getX() + 1][super
					.getY()].getBasetile() instanceof Floor
					&&
					                            Init.getDungeon().getCurrentLevelObj()
					                            .getlvl()[super
					                                      .getX() + 1][super.getY()]
					                                	      .getCurrEntity() != null){
					return true;
				}
			case NORTH:
				if(Init.getDungeon().getCurrentLevelObj()
					.getlvl()[super.getX()][super
					.getY() - 1].getBasetile() instanceof Floor
					&&
					                        Init.getDungeon().getCurrentLevelObj()
					                        .getlvl()[super
					                                  .getX()][super.getY() - 1]
					                                	  .getCurrEntity() != null){
					return true;
				}
			case SOUTH:
				if(Init.getDungeon().getCurrentLevelObj()
					.getlvl()[super.getX()][super
					.getY() + 1].getBasetile() instanceof Floor
					&&
					                        Init.getDungeon().getCurrentLevelObj()
					                        .getlvl()[super
					                                  .getX()][super.getY() + 1]
					                                	  .getCurrEntity() != null){
					return true;
				}
				break;
			case WEST:
				if(Init.getDungeon().getCurrentLevelObj()
					.getlvl()[super.getX() - 1][super
					.getY()].getBasetile() instanceof Floor
					&&
					                            Init.getDungeon().getCurrentLevelObj()
					                            .getlvl()[super
					                                      .getX() - 1][super.getY()]
					                                	      .getCurrEntity() != null){
					return true;
				}
				break;
			default:
				break;
		}
		return false;
	}

	public void attack( MoveDirection direc, Entity ths ){
		Entity other = getTarget(direc, ths);
		if(other == null){
			return;
		}
		if(ths.getHitchance() < new Random().nextInt(100) + 1){
			Display.addMsg("`bthe " + ths.getName() +
				" misses you!`\n");
			return;
		}else{
			EntityCalcs.createDisplayMsg(ths, other,
				EntityCalcs.damage(ths, other));
			other.checkDeath(other);
		}
	}

	public Entity getTarget( MoveDirection d, Entity ths ){
		Entity e = null;
		switch(d){
			case EAST:
				e = Init.getDungeon().getCurrentLevelObj()
				.getlvl()[ths.getX() + 1][ths
					.getY()].getCurrEntity();
				break;
			case NORTH:
				e = Init.getDungeon().getCurrentLevelObj()
				.getlvl()[ths.getX()][ths
					.getY() - 1].getCurrEntity();
				break;
			case SOUTH:
				e = Init.getDungeon().getCurrentLevelObj()
				.getlvl()[ths.getX()][ths
					.getY() + 1].getCurrEntity();
				break;
			case WEST:
				e = Init.getDungeon().getCurrentLevelObj()
				.getlvl()[ths.getX() - 1][ths
					.getY()].getCurrEntity();
				break;
			default:
				break;
		}
		return e;
	}

	public boolean checkDeath( Entity e ){
		if(e.stat.getHp() <= 0){
			die(e);
			return true;
		}
		return false;
	}

	public abstract int getExp();

	protected void die( Entity e ){
		notDead = false;
		e.getLoc().getTile().setCurrEntity(null);
		Init.getDungeon().getCurrentLevelObj().monsters.remove(e);
		Display.addMsg("`cThe " + e.getName() + " has Died!`\n");
	}

	public boolean notDead(){
		return notDead;
	}

	/**
	 * adds this entity to the move que subclasses should override this method
	 * to determine what action to take
	 */
	public void queMove(){
		Turn.addToTurnQue(this);
	}

	/**
	 * this will be called by turn to execute an action subclasses must override
	 * this to be able to act
	 */
	public abstract void act();

	/**
	 * returns speed of entity. used by Turn to determine move order higher is
	 * better
	 * 
	 * @return speed
	 */
	public int getSpeed(){
		return speed;
	}

	/**
	 * setter for speed
	 * 
	 * @param speed
	 *            = speed to set
	 */
	public void setSpeed( int speed ){
		this.speed = speed;
	}

	/**
	 * subclasses should override this for custom tiles
	 * 
	 * @return char for tile class to draw
	 */
	@Override
	public abstract char draw();

	public boolean isMoveFlag(){
		return moveFlag;
	}

	public void setMoveFlag( boolean moveFlag ){
		this.moveFlag = moveFlag;
	}

	/**
	 * 
	 * @return val to reduce dmg by
	 */
	public int defenseAbsorbModifier(){
		return 0;
	}

	public int defensePercentModifier(){
		return 100;
	}

	public int attackStaticModifier(){
		return 0;
	}

	public int attackPercentModifier(){
		return 100;
	}

	public int getDefense(){
		return stat.getDefense();
	}

	public void setDefense( int defense ){
		stat.setDefense(defense);
	}

	public int getAttack(){
		return stat.getAttack();
	}

	public void setAttack( int attack ){
		stat.setAttack(attack);
	}

	public int getHitchance(){
		return stat.getHitchance();
	}

	public void setHitchance( int hitchance ){
		stat.setHitchance(hitchance);
	}

	public int getHp(){
		return stat.getHp();
	}

	public void setHp( int hp ){
		stat.setHp(hp);
	}

	public int getHpmax(){
		return stat.getHpmax();
	}

	public void setHpmax( int hpmax ){
		stat.setHpmax(hpmax);
	}

	public int getTexId(){
		if(Init.useGL()){
			return TextureDB.getTexture(
				"./dat/tiles/fancy/entity/goblin.png")
				.getTextureID();
		}
		return 0;
	}

	public void notifyXY(){
	}

}
