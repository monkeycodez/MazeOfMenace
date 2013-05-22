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
package entity;

import items.*;

import java.util.*;
import entity.player.*;
import run.Init;
import dungeon.tile.*;

/**
 * @author matthew
 * 
 */
public class EntityCalcs {

	private EntityCalcs() {
	}

	/**
	 * 
	 * @param attkr
	 * @param def
	 * @return severity of attack
	 */
	public static int damage(Entity attkr, Entity def) {
		int dmg = 0, ret = 0;
		dmg += attkr.getAttack();// * (double) attkr.attackPercentModifier()
		// 100.0;
		dmg += attkr.attackStaticModifier();
		if (r.nextInt(100) > 90) {
			dmg *= dmg;
			ret = 1;
		}
		dmg -= def.defenseAbsorbModifier();
		if (dmg < 0)
			return ret;
		def.setHp(def.getHp() - dmg);
		return ret;
	}

	public static void createDisplayMsg(Entity atk, Entity def, int sev) {
		switch (sev) {
		case 0:
			if (def instanceof Player)
				Display.addMsg("`oThe " + atk.getName() + " Hits you`\n");
			else
				Display.addMsg("`gYou hit the " + def.getName() + "`\n");
			break;
		case 1:
			if (def instanceof Player)
				Display.addMsg("`rThe " + atk.getName()
						+ " hits you, you are in pain!`\n");
			else
				Display.addMsg("`bYou hit the " + def.getName()
						+ " very hard`\n");
			break;
		}
	}

	private static Random r = new Random();

	public static void createDropWeapon(int dif, Location l) {
		if (r.nextInt(100) > 75) {
			AbstractItem o = null;
			int i = r.nextInt(100) + 1;
			if (i == 1)
				o = new DiamondSword(l.getX(), l.getY(), Init.getDungeon()
						.getCurrentLevelObj().depth);
			if (i == 3 || i == 4)
				o = new BattleArmor(l.getX(), l.getY(), Init.getDungeon()
						.getCurrentLevelObj().depth);
			if (i > 4 && i < 10)
				o = new DragonScaleArmor(l.getX(), l.getY(), Init.getDungeon()
						.getCurrentLevelObj().depth);
			if (i > 10 && i < 16)
				o = new VorpalBlade(l.getX(), l.getY(), Init.getDungeon()
						.getCurrentLevelObj().depth);
			if (i > 16 && i < 25)
				o = new IronArmor(l.getX(), l.getY(), Init.getDungeon()
						.getCurrentLevelObj().depth);
			if (i > 25 && i < 34)
				o = new IronArmor(l.getX(), l.getY(), Init.getDungeon()
						.getCurrentLevelObj().depth);
			if (i > 34 && i < 67)
				o = new RustyKnife(l.getX(), l.getY(), Init.getDungeon()
						.getCurrentLevelObj().depth);
			if (i > 67)
				o = new BasicArmor(l.getX(), l.getY(), Init.getDungeon()
						.getCurrentLevelObj().depth);

			l.getTile().setObject(o);

		}
	}
}
