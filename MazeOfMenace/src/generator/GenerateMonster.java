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
package generator;

import java.util.ArrayList;
import java.util.Random;
import dungeon.Level;
import dungeon.tile.Floor;
import entity.mons.AncientDragon;
import entity.mons.Daemon;
import entity.mons.Dragon;
import entity.mons.GeneralMonster;
import entity.mons.Goblin;
import entity.mons.Kobold;
import entity.mons.Ninja;
import entity.mons.Orc;
import entity.mons.OrcWarlord;

/**
 * @author Matthew Gruda
 * 
 */
public final class GenerateMonster{

	private static ArrayList<GeneralMonster> monsToGen;

	private static boolean initalized = false;

	private static Random r = new Random();

	/**
	 * creates monsters during use
	 * 
	 * @param lvl
	 */
	public static void generateDynamicMonster( Level lvl ){
		int mons = new Random().nextInt(lvl.getDepth() + 3) + 1;
		for(int i = 0; i < mons; i++){
			int[] loc = randLoc(lvl);
			genNewMons(loc[0], loc[1], lvl);
		}
	}

	/**
	 * generate more than normal monsters for lvl start
	 * 
	 * @param lvl
	 */
	public static void generateStartMonster( Level lvl ){
		initialize();
		int mons = r.nextInt(9 + lvl.getDepth()) + 8;
		for(int i = 0; i < mons; i++){
			int[] loc = randLoc(lvl);
			genNewMons(loc[0], loc[1], lvl);
		}
	}

	/**
	 * determines what monster to generate
	 * 
	 * @param x
	 * @param y
	 * @param lvl
	 */
	private static void genNewMons( int x, int y, Level lvl ){
		while(true){
			int i = r.nextInt(monsToGen.size());
			int maxBound = lvl.getDepth() + r.nextInt(5);
			int minBound = lvl.getDepth() - r.nextInt(4);
			GeneralMonster g = monsToGen.get(i);
			if((g.difficulty() > minBound && g.difficulty() <= maxBound)
				||
				(g.difficulty() == 42 && r.nextBoolean())){
				detAndGen(g, x, y, lvl);
				return;
			}
		}
	}

	/**
	 * Determines what monster to generate
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param lvl
	 */
	private static void
	detAndGen( GeneralMonster g, int x, int y, Level lvl ){
		if(g instanceof Goblin){
			lvl.monsters.add(new Goblin(x, y, lvl.getDepth()));
		}else if(g instanceof Orc){
			lvl.monsters.add(new Orc(x, y, lvl.getDepth()));
		}else if(g instanceof OrcWarlord){
			lvl.monsters.add(new OrcWarlord(x, y, lvl.getDepth()));
		}else if(g instanceof Kobold){
			lvl.monsters.add(new Kobold(x, y, lvl.getDepth()));
		}else if(g instanceof AncientDragon){
			lvl.monsters
				.add(new AncientDragon(x, y, lvl.getDepth()));
		}else if(g instanceof Dragon){
			lvl.monsters.add(new Dragon(x, y, lvl.getDepth()));
		}else if(g instanceof Ninja){
			lvl.monsters.add(new Ninja(x, y, lvl.getDepth()));
		}else if(g instanceof Daemon){
			if((lvl.getDepth() > 2)){
				lvl.monsters.add(new Daemon(x, y, lvl
					.getDepth()));
			}
		}
	}

	/**
	 * must be initalized for monsters to be generated
	 */
	public static void initialize(){
		if(initalized){
			return;
		}
		monsToGen = new ArrayList<GeneralMonster>();
		monsToGen.add(new Goblin());
		monsToGen.add(new Orc());
		monsToGen.add(new OrcWarlord());
		monsToGen.add(new Kobold());
		monsToGen.add(new AncientDragon());
		monsToGen.add(new Dragon());
		monsToGen.add(new Ninja());
		monsToGen.add(new Daemon());
	}

	/**
	 * @return x,y of valid floor tile
	 */
	private static int[] randLoc( Level lvl ){
		int c = 1;
		while(true){
			c++;
			int x = r.nextInt(63) + 1, y = r.nextInt(31) + 1;
			if(c > 1000){
				return new int[]{ x, y };
			}
			if(isValidPlace(x, y, lvl)){
				return new int[]{ x, y };
			}
		}
	}

	/**
	 * 
	 * @param x
	 *            x location
	 * @param y
	 *            y location
	 * @return true if the tile is a floor tile
	 */
	private static boolean isValidPlace( int x, int y, Level lvl ){
		if(lvl.getlvl()[x][y].getBasetile() instanceof Floor){
			if(lvl.getlvl()[x][y].getCurrEntity() == null){
				return true;
			}
		}
		return false;
	}

}
