package entity;

public class StatComponent implements Cloneable{

	private int attack;

	private int hitchance, hp, hpmax, defense, speed;

	private String name;

	public boolean can_walk = true, can_swim, can_fly;

	public StatComponent(int attack, int hitchance, int hp, int hpmax,
		int defense, int speed, String name) {
		super();
		this.attack = attack;
		this.hitchance = hitchance;
		this.hp = hp;
		this.hpmax = hpmax;
		this.defense = defense;
		setSpeed(speed);
		setName(name);
	}

	@Override
	public StatComponent clone(){
		return new StatComponent(
			attack,
			hitchance,
			hp,
			hpmax,
			defense,
			speed,
			name);
	}

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

	public int getSpeed(){
		return speed;
	}

	public void setSpeed( int speed ){
		this.speed = speed;
	}

	public String getName(){
		return name;
	}

	public void setName( String name ){
		this.name = name;
	}

}
