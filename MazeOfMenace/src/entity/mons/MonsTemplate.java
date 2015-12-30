package entity.mons;

import json.JSONObj;
import util.*;
import entity.StatComponent;

public class MonsTemplate{

	private DiceRoll hp;

	public EMonster create_instance(){
		StatComponent st = new StatComponent(0, 0, hp.roll(), 0, 0, 0, null);
		EMonster nmons = new EMonster(null, st, null, null);
		return nmons;
	}

	public static MonsTemplate build_mons(JSONObj mons){
		MonsTemplate tmpl = new MonsTemplate();
		tmpl.hp = Utils.get_dice_roll(mons.gets("HP"));

		return tmpl;
	}

}
