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
package run.input;

import run.Init;
import run.SaveAndLoad;
import run.turn.*;
import terminal.gld.GLDungeonDraw;
import items.*;
import java.awt.event.*;
import dungeon.Dungeon;
import dungeon.Level;
import dungeon.tile.*;
import entity.player.*;
import entity.player.inventory.InventoryDisplay;
import entity.*;

/**
 * this class is used in parisng during normal mode
 * 
 * @author Matthew Gruda
 * 
 */
public final class NormalModeParse {

	/**
	 * parses the normal mode input
	 * 
	 * @param k
	 */
	protected static void inputParse(KeyEvent k) {
		// System.out.println(k);
		switch (k.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				parseMovement(MoveDirection.SOUTH);
				break;
			case KeyEvent.VK_UP:
				parseMovement(MoveDirection.NORTH);
				break;
			case KeyEvent.VK_LEFT:
				parseMovement(MoveDirection.WEST);
				break;
			case KeyEvent.VK_RIGHT:
				parseMovement(MoveDirection.EAST);
				break;
			case KeyEvent.VK_MINUS:
				switchLvl();
				break;
			case KeyEvent.VK_EQUALS:
				switchLvl();
				break;
			case KeyEvent.VK_PERIOD:
				Turn.turn();
				break;
			case KeyEvent.VK_G:
				grab();
				break;
			case KeyEvent.VK_D:
				drink();
				break;
			case KeyEvent.VK_H:
				helpStart();
				break;
			default:
				normalKeyParse(k);

		}
	}

	private static void helpStart() {
		Init.setState(GameState.HELP);
		Init.terminal.clearScreen();
		HelpParse.drawHelpScreen();
	}

	private static void drink() {
		if (Init.getDungeon().getPlayer().getLoc().getTile()
				.getBasetile() instanceof Fountan) {
			if (Init.getDungeon().getPlayer().getHp() + 10 >= Init
					.getDungeon().getPlayer().getHpmax()) {
				Turn.turn();
				Init.getDungeon()
						.getPlayer()
						.setHp(Init.getDungeon()
								.getPlayer()
								.getHpmax());
				Display.addMsg("`bYou are not very thirsty`");
			} else {
				Turn.turn();
				Init.getDungeon()
						.getPlayer()
						.setHp(Init.getDungeon()
								.getPlayer()
								.getHp() + 10);
				Display.addMsg("`bYou drink some water!`");
			}
		}
		
	}

	private static void grab() {
		Player p = Init.getDungeon().getPlayer();
		Location l = p.getLoc();
		if (l.getTile().getObject() != null) {
			AbstractItem o = l.getTile().getObject();
			l.getTile().setObject(null);
			o.pickUp();
			if (o instanceof AbstractWeapon) {
				p.getIndv().add((AbstractWeapon) o);
				Display.addMsg("You grabed the `r"
						+ o.getName());
			} else if (o instanceof AbstractArmor) {
				p.getIndv().add((AbstractArmor) o);
				Display.addMsg("You grabed the `g"
						+ o.getName());
			} else if (o instanceof AbstractConsumeable) {
				p.getIndv().add((AbstractConsumeable) o);
			} else if (o instanceof OrbOfYendor) {
				OrbOfYendor or = (OrbOfYendor) o;
				Display.addMsg("`fYou Have The +10 Orb of Yendor\nEscape The Dungeon`");
				p.getIndv().setorb(or);
				Init.terminal.clearScreen();
				Init.getDungeon().draw();
			}
		}
	}

	/**
	 * sends the direction to move to player
	 * 
	 * @param direct
	 *                to move
	 */
	private static void parseMovement(MoveDirection direct) {
		boolean b = Init.getDungeon().getPlayer().makeMove(direct);
		if (!b) {
			return;
		}
		Turn.turn();
	}

	private static void normalKeyParse(KeyEvent c) {
		switch (c.getKeyChar()) {
			case 'i':
				goToInventory();
				break;
			case 'g':
				grabItem();
				break;
			case 'S':
				SaveAndLoad.save();
				break;
			case 'R':
				SaveAndLoad.restart();
				break;
			case '.':
				Turn.turn();
				break;
		}
	}

	/**
	 * moves player up/down one level
	 */
	private static void switchLvl() {
		GLDungeonDraw.updatedgn();
		Player p = Init.getDungeon().getPlayer();
		Level lvl = Init.getDungeon().getCurrentLevelObj();
		Dungeon d = Init.getDungeon();
		Init.terminal.clearScreen();
		if (p.getX() == lvl.getUpStrX() && p.getY() == lvl.getUpStrY()) {
			if (Init.getDungeon().levelDown())
				p.moveAnywhere(d.getCurrentLevelObj()
						.getDwStrX(), d
						.getCurrentLevelObj()
						.getDwStrY(), d
						.getCurrentLevelObj().depth,
						lvl.depth);
			else
				return;
		}
		if (p.getX() == lvl.getDwStrX() && p.getY() == lvl.getDwStrY()) {
			Init.getDungeon().levelUp();
			p.moveAnywhere(d.getCurrentLevelObj().getUpStrX(), d
					.getCurrentLevelObj().getUpStrY(), d
					.getCurrentLevelObj().depth, lvl.depth);
		}
		Init.getDungeon().draw();
	}

	private static void grabItem() {

	}

	private static void goToInventory() {
		Init.setState(GameState.INVENTORY);
		InventoryDisplay.draw();
	}
}
