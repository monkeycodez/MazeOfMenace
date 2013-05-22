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
package dungeon.tile;

import run.Init;
import terminal.FancyImageBuffer;
import terminal.XTerm;
import items.AbstractItem;

import java.awt.Color;
import java.awt.Image;
import java.io.Serializable;
import java.util.*;
import entity.Entity;
import entity.player.Player;
import entity.mons.GeneralMonster;

/**
 * 
 * @author Matthew Gruda. this will contain everything on tile the basetype
 *         (floor/wall) in basetile & mons, player, items in objects
 * 
 */

/**
 * @author matthew
 * 
 */
public class Tile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BaseTileType basetile;
	private AbstractItem object;
	private TileState state;
	private Position place;
	private Entity currEntity;
	private boolean seen, inView;
	public boolean genFlag1;

	/**
	 * used to help the draw() method
	 * 
	 * @author matthew
	 * 
	 */
	public enum TileState {
		FLOOR, WALL, PLAYER, ITEM, MONS, OTHER
	}

	public Tile(int x, int y, BaseTileType b) {
		this.currEntity = null;
		this.seen = false;
		this.inView = false;
		this.place = new Position(x, y);
		this.basetile = b;
		this.genFlag1 = false;
		object = null;
	}

	/**
	 * will remove object o in the objects array
	 * 
	 * @param o
	 */
	public void removeObject(AbstractItem o) {
		object = null;
	}

	public void drawf(int x, int y) {
		this.updateState();
		if (this.inView) {
			switch (state) {
			case FLOOR:
			case WALL:
				drawf(basetile.getfImage(), x, y);
				break;
			case MONS:
			case OTHER:
			case PLAYER:
				drawf(currEntity.getfIm(), x, y);
				break;
			case ITEM:
				drawf(object.getfIm(), x, y);
				break;
			default:
				break;
			}
		} else {
			if (seen)
				drawf(basetile.getfImageOutOfSight(), x, y);
		}
	}

	private void drawf(Image i, int x, int y) {
		FancyImageBuffer.buffer.addImage(x, y, i);
	}

	/**
	 * detirmines what char to draw
	 */
	public void draw() {
		this.updateState();
		if (this.inView) {
			// if(basetile instanceof Fountan){
			// place.draw(basetile.draw(), Color.BLUE, basetile.getImage());
			// }
			switch (this.state) {
			case WALL:
			case FLOOR:
				this.place.draw(this.basetile.draw(), this.basetile.getImage());
				break;
			case ITEM:
				this.place.draw(object.draw(), object.getColor(),
						object.getImage());
				break;
			case MONS:
				this.place.draw(this.currEntity.draw(),
						this.currEntity.getColor(), currEntity.getImage());
				break;
			case OTHER:
				this.place.draw('"');
				break;
			case PLAYER:
				this.place.draw(this.currEntity.draw(),
						this.currEntity.getColor(), currEntity.getImage());
				break;
			default:
				break;
			}
		} else if (this.seen)
			this.place.draw(this.basetile.draw(), Color.GRAY,
					this.basetile.getImageOutOfSight());
	}

	/**
	 * sets the basetile
	 * 
	 * @param t
	 */
	public void setBaseTile(BaseTileType t) {
		this.basetile = t;
		if (this.basetile instanceof Wall)
			this.state = TileState.WALL;
		else
			this.state = TileState.FLOOR;
	}

	/**
	 * updates state of tile. used in drawing
	 */
	public void updateState() {
		if (this.basetile instanceof Wall)
			this.state = TileState.WALL;
		else if (this.basetile instanceof Floor) {
			this.state = TileState.FLOOR;
			if (this.currEntity != null) {
				if (this.currEntity instanceof Player)
					this.state = TileState.PLAYER;
				else if (this.currEntity instanceof GeneralMonster)
					this.state = TileState.MONS;
			} else if (object != null)
				this.state = TileState.ITEM;
		}
	}

	public AbstractItem getObject() {
		return object;
	}

	public void setObject(AbstractItem o) {
		object = o;
	}

	/**
	 * 
	 * @param o
	 *            item to add
	 */
	public void addObject(AbstractItem o) {
		object = o;
		this.updateState();
	}

	public TileState getState() {
		return this.state;
	}

	public void setState(TileState state) {
		this.state = state;
	}

	public BaseTileType getBasetile() {
		return this.basetile;
	}

	public Position getPlace() {
		return this.place;
	}

	public Entity getCurrEntity() {
		return this.currEntity;
	}

	public void setCurrEntity(Entity currEntity) {
		this.currEntity = currEntity;
	}

	/**
	 * this both checks the tilestate && modifies it to correct value
	 * 
	 * @return TileState will be used in drawing and player/ai movement
	 */
	public TileState getTopState() {
		if (this.basetile instanceof Wall)
			return TileState.WALL;
		else
			// Detirmine mons item, || player
			return TileState.FLOOR;

	}

	/**
	 * used for keeping location of tile and draw method
	 * 
	 * @author Matthew Gruda
	 * 
	 */
	private class Position implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int x, y;

		private Position(int x, int y) {
			this.x = x;
			this.y = y;
		}

		private void draw(char c) {
			Init.terminal.moveCursor(this.x, this.y);
			Init.terminal.putCharacter(c);
		}

		private void draw(char c, Color col) {
			Init.terminal.moveCursor(this.x, this.y);
			Init.terminal.putCharacter(c, col);
		}

		private void draw(char c, Color col, Image i) {
			Init.terminal.moveCursor(this.x, this.y);
			Init.terminal.putCharacter(c, col);
			XTerm.putImage(i, x, y);
		}

		private void draw(char c, Image i) {
			Init.terminal.moveCursor(this.x, this.y);
			Init.terminal.putCharacter(c);
			XTerm.putImage(i, x, y);
		}

		@Override
		public String toString() {
			return "Position [x=" + this.x + ", y=" + this.y + "]";
		}

	}

	/**
	 * some setters & getters
	 * 
	 * @return
	 */
	public boolean isSeen() {
		return this.seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	public boolean isInView() {
		return this.inView;
	}

	public void setInView(boolean inView) {
		this.inView = inView;
	}
	
	public int getX(){
		return place.x;
	}
	
	public int getY(){
		return place.y;
	}

}
