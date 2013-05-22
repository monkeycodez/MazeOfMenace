package terminal.gld;

import java.awt.Button;
import java.awt.event.KeyEvent;

import org.lwjgl.input.Keyboard;

import run.Init;
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
