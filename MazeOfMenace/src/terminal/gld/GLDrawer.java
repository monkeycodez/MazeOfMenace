package terminal.gld;

import java.awt.Button;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import run.Init;
import run.Util;
import run.input.InputParse;

public class GLDrawer {

	public static void draw() {
		inputparse();
		switch (Init.getState()) {
			case DEAD: otherDraw();
				break;
			case HELP: otherDraw();
				break;
			case INVENTORY: otherDraw();
				break;
			case NORMAL:
				GLDungeonDraw.draw();
				break;
			case OTHER:
				break;
			case START:
				otherDraw();
				break;
			default:
				break;

		}
	}

	static TrueTypeFont fnt = new TrueTypeFont(new Font("Monospaced",
			 Font.PLAIN, 40), true);

	public static void otherDraw() {
		String s = "";
		char[][] charr = ((GLDummyTerm) Init.terminal).term;
		for (int x = 0; x < 80; x++) {
			for (int y = 0; y < 80; y++) {
				s += charr[y][x];
			}
			s += '\n';
		}
		
		System.out.println(GLDummyTerm.changed());
		
		// System.out.println(s);
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 600, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		glLoadIdentity();
		if(GLDummyTerm.changed()){
			strdispl = glGenLists(1);
			glNewList(strdispl, GL_COMPILE);
			fnt.drawString(000, 600, s, 0, s.length()-1, .35f, .35f, TrueTypeFont.ALIGN_LEFT, GLDummyTerm.toColArr());
			glEndList();
			GLDummyTerm.donechanged();
		}
		glCallList(strdispl);
		glPopMatrix();
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
	}
	
	private static int strdispl = 0;
	

	static int count = 0, p = 3;

	private static Button btn = new Button();

	@SuppressWarnings("deprecation")
	private static void inputparse() {
		if (GLDungeonDraw.inEvent())
			return;
		count++;
		if (count != p)
			return;
		count = 0;
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_SPACE));
		} else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_LEFT));
		} else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_RIGHT));
		} else if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_UP));
		} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_DOWN));
		} else if (Keyboard.isKeyDown(Keyboard.KEY_N)) {
			Init.getDungeon().getPlayer().turnLeft();
		} else if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
			Init.getDungeon().getPlayer().turnRight();
		} else if (Keyboard.isKeyDown(Keyboard.KEY_EQUALS)) {
			System.out.println("1");
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_EQUALS));

		} else if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_MINUS));

		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)
				&& (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
				|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_S, 'S'));

		} else if (Keyboard.isKeyDown(Keyboard.KEY_R)
				&& (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
				|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			GLDungeonDraw.updatedgn();
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_R, 'R'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_H, 'h'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_B, 'b'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_Q, 'q'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_A, 'a'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_B, 'b'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_C, 'c'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_D, 'd'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_E, 'e'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_F, 'f'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_G, 'g'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_H, 'h'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_I, 'i'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_J)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_J, 'j'));
		}else if (Keyboard.isKeyDown(Keyboard.KEY_K)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_K, 'k'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_L, 'l'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_O, 'o'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_P, 'p'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_R, 'r'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_S, 's'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_T, 't'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_U)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_U, 'u'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_V, 'v'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_W, 'w'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_X, 'x'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_Y)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_Y, 'y'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_Z, 'z'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_0)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_0, '0'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_1, '1'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_2, '2'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_3, '3'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_4)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_4, '4'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_5)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_5, '5'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_6)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_6, '6'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_7)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_7, '7'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_8)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_8, '8'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_9)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0,
					KeyEvent.VK_9, '9'));

		}

	}

}
