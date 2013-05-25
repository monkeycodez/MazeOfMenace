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
import run.input.InputParse;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.*;

public class GLDisplay {
	
	public static final int fps = 50;

	private static Thread mloop;
	
	public static void startup(){
		try{
			mloop = new Thread(new Loop(), "MAIN_LOOP");
			mloop.start();
		}catch (Exception e){
			
		}
	}
	
	
	
	
	private static class Loop implements Runnable{

		public void run() {
			try{
			Display.setDisplayMode(new DisplayMode(1366, 786));
			Display.setFullscreen(true);
			Display.create();
			}catch(LWJGLException e){		
				e.printStackTrace();
			}
			try {
				Keyboard.create();
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
//			InputParse.inputParse(new KeyEvent(new Button() , 0, 0, 0, KeyEvent.VK_SPACE));

			//Init stuff
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			glEnable(GL_DEPTH_TEST);
			glEnable(GL_DEPTH_CLAMP);
			
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			GLU.gluPerspective(80, 1366/ 786, 1f, 500f);
			glMatrixMode(GL_MODELVIEW);
		
			while(!Display.isCloseRequested()){
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		//		long t = System.currentTimeMillis();
				GLDrawer.draw();
				Display.update();
		//		System.out.println("  "+(System.currentTimeMillis() - t)/ 100.0);

				Display.sync(30);
		//		System.out.println((System.currentTimeMillis() - t)/ 100.0);
				if(Keyboard.isKeyDown(Keyboard.KEY_END)) System.exit(9999);
			}
		}
		
	}
}
