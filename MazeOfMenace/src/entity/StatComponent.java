package entity;

public class StatComponent{

	private int attack;

	private int hitchance, hp, hpmax, defense;

	public int getAttack(){
		return attack;
	}

	public void setAttack( int attack ){
		this.attack = attack;
	}

	public int getHitchance(){
		return hitchance;
	}

	public void setHitchance( int hitchance ){
		this.hitchance = hitchance;
	}

	public int getHp(){
		return hp;
	}

	public void setHp( int hp ){
		this.hp = hp;
	}

	public int getHpmax(){
		return hpmax;
	}

	public void setHpmax( int hpmax ){
		this.hpmax = hpmax;
	}

	public int getDefense(){
		return defense;
	}

	public void setDefense( int defense ){
		this.defense = defense;
	}

}
