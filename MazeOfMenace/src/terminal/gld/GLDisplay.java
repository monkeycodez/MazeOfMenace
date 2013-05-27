/*******************************************************************************
 * Copyright (c) 2013 Matthew Gruda.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 * matthew - initial API and implementation
 ******************************************************************************/
package terminal.gld;

import java.awt.Button;
import java.awt.event.KeyEvent;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.glu.GLU;
import run.Settings;
import run.input.InputParse;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.*;

public class GLDisplay {

	private static int fps = 50;

	public static void startup() {
		fps = Settings.getFps();
		run();
	}

	public static void run() {
		try {
			Display.setDisplayMode(new DisplayMode(Settings
					.getGlX(), Settings.getGlY()));
			Display.setFullscreen(true);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		try {
			Keyboard.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		glEnable(GL_DEPTH_TEST);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(80,
				Settings.getGlX() / (float) Settings.getGlY(),
				1f, 500f);
		glMatrixMode(GL_MODELVIEW);
		exit: {
			while (!Display.isCloseRequested()) {
				glClear(GL_COLOR_BUFFER_BIT
						| GL_DEPTH_BUFFER_BIT);
				GLDrawer.draw();
				Display.update();

				Display.sync(fps);

				int e = glGetError();
				if (e != GL11.GL_NO_ERROR)
					System.out.println(e
							+ " f "
							+ org.lwjgl.opengl.Util
									.translateGLErrorString(e));
				if (Keyboard.isKeyDown(Keyboard.KEY_END))
					break exit;
			}
			Display.destroy();
			Keyboard.destroy();
		}
		Display.destroy();
		Keyboard.destroy();
		System.exit(9999);
	}

}
