package entity;

public interface Updateable{

	public boolean update();

	/*
	 * Cooldown mechanic:
	 * 	every move, time gets added to the cooldown in relation to each move
	 * 	every tick, 10 gets removed from the cooldown for each updateable
	 * 	when cooldown is >=0, it gets added to the turn queue, with the least cooldown 
	 * 		going first.  If there is a cd colision, highest speed goes first
	 */

	public void reduce_cooldown( int time );

	public int get_cooldown();

	public int get_speed();

}
