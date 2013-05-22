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
		case DEAD:
			break;
		case HELP:
			break;
		case INVENTORY:
			break;
		case NORMAL:
			GLDungeonDraw.draw();
			break;
		case OTHER:
			break;
		case START:
			break;
		default:
			break;

		}
	}
	static TrueTypeFont fnt = new TrueTypeFont(new Font(Util.getTxtMsg("config/font.cfg")
			.trim(), Font.PLAIN, Integer.parseInt(Util.getTxtMsg(
			"config/fontsize.cfg").trim())), true);
	public static void otherDraw(){
		String s = "";
		for(char[] c: ((GLDummyTerm)Init.terminal).term){
			s += Arrays.toString(c) + '\n';
		}
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();
		GL11.glOrtho(0, 680, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		glLoadIdentity();
		fnt.drawString(0, 0, s, 10, 10);
		glPopMatrix();
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
	}

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
			InputParse
					.inputParse(new KeyEvent(btn, 0, 0, 0, KeyEvent.VK_SPACE));
		} else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0, KeyEvent.VK_LEFT));
		} else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			InputParse
					.inputParse(new KeyEvent(btn, 0, 0, 0, KeyEvent.VK_RIGHT));
		} else if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0, KeyEvent.VK_UP));
		} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0, KeyEvent.VK_DOWN));
		} else if (Keyboard.isKeyDown(Keyboard.KEY_N)) {
			Init.getDungeon().getPlayer().turnLeft();
		} else if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
			Init.getDungeon().getPlayer().turnRight();
		}else if (Keyboard.isKeyDown(Keyboard.KEY_EQUALS)) {
			System.out.println("1");
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0, KeyEvent.VK_EQUALS));

		} else if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0, KeyEvent.VK_MINUS));

		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)
				&& (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
				|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0, KeyEvent.VK_S, 'S'));

		}else if (Keyboard.isKeyDown(Keyboard.KEY_R)
				&& (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
				|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			GLDungeonDraw.updatedgn();
			InputParse.inputParse(new KeyEvent(btn, 0, 0, 0, KeyEvent.VK_R, 'R'));

		}

	}

}
