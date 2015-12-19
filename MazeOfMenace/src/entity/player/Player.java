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
package entity.player;

import java.awt.Color;
import render.draw.FixedDrawComp;
import dungeon.tile.Tile;
import entity.Entity;
import entity.StatComponent;

/**
 * @author matthew
 * 
 */
public class Player extends Entity{

	private int regenCounter = 0;

	private final int regenTgt = 3;

	private PlayerUpdateable pup = new PlayerUpdateable();

	public Player(Tile t, StatComponent stat) {
		super(t, stat, new FixedDrawComp('@', Color.BLUE),
			null);
		super.setUpdate(pup);
		t.getLat().get_from().setPlayer(this);
	}

	public void regenControl(){
		regenCounter++;
		StatComponent st = super.getStat();
		if(regenCounter > regenTgt){
			if(st.getHp() < st.getHpmax()){
				st.setHp(st.getHp() + 1);
			}
			regenCounter = 0;
		}
	}

	public String getName(){
		return "you";
	}

	public PlayerUpdateable get_p_up(){
		return pup;
	}

}
