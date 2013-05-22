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
package generator;
import dungeon.*;
import dungeon.tile.*;
import run.*;
import items.*;
import entity.mons.*;
/**
 * @author matthew
 *
 */
final class GenerateFixedLevel {
	
	private static int[] lvls;
	private static boolean b = false;
	
	protected static void genFixedLevel(Level lvl){
		String fs = getLvlString(lvl);
		String[] lvlArr = fs.split("\n"); 
		for(int i = 0; i < lvlArr.length; i++){
			String[] tileArr = lvlArr[i].split(";");
			for (int c = 0; c < 63; c++){
				switch(tileArr[c].charAt(0)){
				case '#':lvl.getlvl()[c][i].setBaseTile(new Wall()); break;
				case '.':lvl.getlvl()[c][i].setBaseTile(new Floor()); break;
				case '%':lvl.getlvl()[c][i].setBaseTile(new Fountan()); break;
				case '<':lvl.getlvl()[c][i].setBaseTile(new StairUp());
				lvl.setUpStrX(c);
				lvl.setUpStrY(i);
				break;
				case '>':lvl.getlvl()[c][i].setBaseTile(new StairDown()); 
				lvl.setDwStrX(c);
				lvl.setDwStrY(i);
				break;
				case 'A': lvl.getlvl()[c][i].setBaseTile(new Floor());
				lvl.monsters.add(new AncientDragon(c, i, lvl.depth));
				break;
				case 'D': lvl.getlvl()[c][i].setBaseTile(new Floor());
				lvl.monsters.add(new Dragon(c, i, lvl.depth));
				break;
				case 'O': lvl.getlvl()[c][i].setBaseTile(new Floor());
				lvl.getlvl()[c][i].setObject(new OrbOfYendor(c, i, lvl.depth));
				break;
				case 'a': lvl.getlvl()[c][i].setBaseTile(new Floor());
				lvl.getlvl()[c][i].setObject(new IronArmor(c, i, lvl.depth));
				break;
				case 'S': lvl.getlvl()[c][i].setBaseTile(new Floor());
				lvl.getlvl()[c][i].setObject(new IronSword(c, i, lvl.depth));
				break;
				case 'd': lvl.getlvl()[c][i].setBaseTile(new Floor());
				lvl.getlvl()[c][i].setObject(new DiamondSword(c, i, lvl.depth));
				break;
				case 'b': lvl.getlvl()[c][i].setBaseTile(new Floor());
				lvl.getlvl()[c][i].setObject(new BattleArmor(c, i, lvl.depth));
				break;
				}
			}
		}
	}
	
	private static String getLvlString(Level lvl){
		int depth = lvl.getDepth();
		return Util.getTxtMsg("dat/LVL"+depth+".lvl").trim();
	}
	
	private static void initalize(){
		String s = Util.getTxtMsg("config/fixedLvl.cfg");
		String[] sa = s.split(";");
		int[] tmp = new int[sa.length-1];
		for(int i = 0; i < sa.length-1; i++){
			try{
				tmp[i] = Integer.parseInt(sa[i]);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		lvls = tmp;
	}
	protected static boolean has(Level lvl){
		if(!b)
			initalize();
		for(int a: lvls){
			if(a == lvl.depth)
				return true;
		}
		return false;
	}
	

}
