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
package entity.player.inventory;
import java.util.*;
import items.*;
import entity.player.*;
import java.io.Serializable;

/**
 * @author matthew
 *
 */
public class Inventory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbstractWeapon[] wbag = new AbstractWeapon[10];
	private AbstractArmor[] abag = new AbstractArmor[10];
	private AbstractConsumeable[] cbag  = new AbstractConsumeable[10];
	private OrbOfYendor orb = null;
	private AbstractWeapon w;
	private AbstractArmor a;
	private Player p;
	/**
	 * 
	 */
	public Inventory(Player p) {
		cbag = new AbstractConsumeable[10];
		w = null;
		a =null;
		this.p = p;
	}
	public AbstractArmor[] getABag() {
		return abag;
	}
	public AbstractWeapon[] getWBag() {
		return wbag;
	}
	
	public AbstractConsumeable[] getCbag(){
		return cbag;
	}
	
	public void setWeapon(AbstractWeapon wep){
		AbstractWeapon tmpw = w;
		w = wep;
		tmpw.setLoc(p.getLoc());
		
	}
	
	public void add(AbstractWeapon w){
		for(int i = 0; i < 10; i++){
			if (wbag[i] == null){
				wbag[i] = w;
				break;
			}
		}
	}
	
	public void add(AbstractArmor a){
		for(int i = 0; i < 10; i++){
			if (abag[i] == null){
				abag[i] = a;
				break;
			}
		}
	}
	
	public void add(AbstractConsumeable a){
		for(int i = 0; i < 10; i++){
			if (cbag[i] == null){
				cbag[i] = a;
				break;
			}
		}
	}
	
	public void equip(char c){
		String locs = "abcdefghij";
		int l = locs.indexOf(c);
		AbstractArmor tmp = abag[l];
		abag[l] = a;
		a = tmp;
	}
	
	public void drop(char c){
		remove(abag["abcdefghij".indexOf(c)]);
	}
	
	public void drop(int i){
		remove(wbag[i]);
	}
	
	public void equip(int c){
		AbstractWeapon tmp = wbag[c];
		wbag[c] = w;
		w = tmp;
	}
	
	public void remove(AbstractArmor a){
		for(int i = 0; i < 10; i++){
			if(abag[i] == a){
				abag[i] = null;
			}
		}
	}
	
	public void remove(AbstractWeapon a){
		for(int i = 0; i < 10; i++){
			if(wbag[i] == a){
				wbag[i] = null;
			}
		}
	}
	
	public void remove(AbstractConsumeable a){
		for(int i = 0; i < 10; i++){
			if(cbag[i] == a){
				cbag[i] = null;
			}
		}
	}
	
	public AbstractWeapon getW(){
		return w;
	}
	
	public AbstractArmor getA(){
		return a;
	}
	
	public int getStaticAtk(){
		if(w != null){
			return w.getWeaponStaticMod();
		}
		return 0;
	}
	
	public int getStaticDef(){
		if(a != null){
			return a.getStaticDefenseVal();
		}
		return 0;
	}
	
	public void use(char c){
		int i = "orstuvxyz".indexOf(c);
		System.out.println(i+"");
		if(cbag[i] != null){
			System.out.println("consumed");
			cbag[i].consume();
			cbag[i] = null;
		}
	}
	
	public OrbOfYendor getOrb(){
		return orb;
	}
	
	public void setorb(OrbOfYendor o){
		orb = o;
	}
	

}
