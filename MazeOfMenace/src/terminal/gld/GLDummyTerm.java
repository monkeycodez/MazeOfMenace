/**
 * 
 */
package terminal.gld;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import terminal.Terminal;

/**
 * @author 0200565
 *
 */
public class GLDummyTerm extends Terminal {
	
	private char[][] term = new char[80][80];
	private int ptrx =0, ptry = 0;

	/* (non-Javadoc)
	 * @see terminal.Terminal#enterPrivateMode()
	 */
	@Override
	public void enterPrivateMode() {
		GLDisplay.startup();
	}

	/* (non-Javadoc)
	 * @see terminal.Terminal#exitPrivateMode()
	 */
	@Override
	public void exitPrivateMode() {
		System.exit(0);
	}

	/* (non-Javadoc)
	 * @see terminal.Terminal#putCharacter(char)
	 */
	
	
	
	@Override
	public void putCharacter(char c) {
		term[ptrx][ptry] = c;
		ptrx++;
		if(ptrx == 80){
			ptrx = 0;
			ptry++;
		}
	}

	/* (non-Javadoc)
	 * @see terminal.Terminal#moveCursor(int, int)
	 */
	@Override
	public void moveCursor(int x, int y) {
		ptrx = x;
		ptry = y;
	}

	/* (non-Javadoc)
	 * @see terminal.Terminal#clearScreen()
	 */
	
	BufferedWriter out;
	
	{
		try {
			out = new BufferedWriter(new FileWriter("logs"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Override
	public void clearScreen()  {
		for(int i = 0; i < 80; i++){
			for(int c = 0; c < 80; c++){
				try {
					out.append(term[i][c]);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				term[c][i] = ' ';
			}
			try {
				out.append('\n');
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see terminal.Terminal#putCharacter(char, java.awt.Color)
	 */
	@Override
	public void putCharacter(char c, Color col) {
		putCharacter(c);
	}

}
