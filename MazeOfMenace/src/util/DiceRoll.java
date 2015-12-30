package util;

import java.util.Random;

/**
 * A dice roll in the XdY+Z format.
 * 	X - number of dice.
 * 	Y - number of faces of each dice
 * 	Z - plus value added at the end
 * @author matthew
 *
 */
public class DiceRoll{

	private int numdice, faces, plus;

	private Random r;

	public DiceRoll(int numdice, int faces, int plus, Random rg) {
		this.numdice = numdice;
		this.faces = faces;
		this.plus = plus;
		r = rg;
	}

	public int roll(){
		int total = 0;
		int i = 0;
		while(i < numdice){
			total += r.nextInt(faces) + 1;
			i++;
		}
		return total + plus;
	}

}
