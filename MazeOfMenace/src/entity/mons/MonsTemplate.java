package entity.mons;

import render.draw.DrawComponent;
import entity.StatComponent;

public class MonsTemplate{

	private StatComponent from;

	public MonsTemplate(StatComponent fr) {
		from = fr;
	}

	public StatComponent getStats(){
		return from.clone();
	}

	public DrawComponent getDraw(){
		return null;
	}

}
