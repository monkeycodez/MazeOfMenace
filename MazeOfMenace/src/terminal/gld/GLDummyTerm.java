/**
 * 
 */
package terminal.gld;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import run.Init;
import terminal.Terminal;

/**
 * @author 0200565
 * 
 */
public class GLDummyTerm extends Terminal {

	public char[][] term = new char[80][80];
	private static Color[][] colarr = new Color[80][80];
	private int ptrx = 0, ptry = 0;
	private static boolean changed = true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see terminal.Terminal#enterPrivateMode()
	 */
	@Override
	public void enterPrivateMode() {
		GLDisplay.startup();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see terminal.Terminal#exitPrivateMode()
	 */
	@Override
	public void exitPrivateMode() {
		System.exit(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see terminal.Terminal#putCharacter(char)
	 */

	@Override
	public void putCharacter(char c) {
		term[ptrx][ptry] = c;
		ptrx++;
		if (ptrx == 80) {
			ptrx = 0;
			ptry++;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see terminal.Terminal#moveCursor(int, int)
	 */
	@Override
	public void moveCursor(int x, int y) {
		ptrx = x;
		ptry = y;
	}

	@Override
	public void clearScreen() {
		for (int i = 0; i < 80; i++) {
			for (int c = 0; c < 80; c++) {
				colarr[c][i] = Color.white;
				term[c][i] = ' ';
			}
		}
		changed = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see terminal.Terminal#putCharacter(char, java.awt.Color)
	 */
	@Override
	public void putCharacter(char c, Color col) {
		colarr[ptrx][ptry] = col;
		putCharacter(c);
	}
	
	public static Color[] toColArr(){
		Color[] tmp = new Color[81*80];
		int ptr = 0;
		for(int i = 0; i < 80; i++){
			for(int c = 0; c<80; c++){
				tmp[ptr] = colarr[c][i];
				ptr++;
			}
			tmp[ptr] = Color.WHITE;
			ptr++;
		}
		return tmp;
	}
	
	public static boolean changed(){
		return changed;
	}
	
	public static void donechanged(){
		changed = false;
	}

}
