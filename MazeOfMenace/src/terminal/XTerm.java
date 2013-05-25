/*******************************************************************************
 * Copyright (c) 2012, 2013 Matthew Gruda
 * 
 *    This file is part of Maze Of Menace.
 * 
 *     Maze Of Menace is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     Maze Of Menace is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *     All rights reserved. This program and the accompanying materials
 *     are made available under the terms of the GNU Public License v3.0
 *     which accompanies this distribution, and is available at
 *     http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Matthew Gruda- initial API and implementation
 ******************************************************************************/
package terminal;

import java.awt.*;
import javax.swing.*;
import run.*;
import run.input.GameState;
import run.input.InputParse;

import java.awt.event.*;
import java.awt.image.*;

/**
 * These are the classes for handling the window and graphics when running in a
 * GUI environment. There can only exist one of this class It uses one thread to
 * draw to the buffer, one thread to handle repaints, and a Key Listener to
 * handle input. A double buffer is used for rendering
 * 
 * Other classes draw with a "cursor" on terminal, calling moveCursor() and the
 * putChar()
 * 
 * @author Matthew Gruda
 */
public class XTerm extends Terminal {

	private static XTermRenderer winFrame;
	protected static char[][] charArray = new char[90][50];
	protected static Color[][] colorArray = new Color[90][50];
	private static Image[][] imageArray = new Image[90][50];
	private static boolean[][] boolImageArray = new boolean[90][50];
	protected static int x, y, w, h;
	private static BufferedImage backbuffer;
	private static Graphics2D g2d;
	private static volatile boolean done = false;
	private static Thread gl, render;
	protected static boolean one = false, started = false, changed = true,
			fullScreen = false;
	private static XTerm xterm;
	private static boolean fullScreenWanted, isTilesWanted = false;
	protected static Font Xfont = new Font(Settings.getFnt(), Font.PLAIN, Settings.getFsz());
	private static boolean choosen = false;
	protected static Object osync = new Object(), sync = new Object(),
			synobj = new Object();
	private static Image icon = Toolkit.getDefaultToolkit().getImage(
			"dat/MazeOfMenace.png");

	static {
		isTilesWanted = Settings.isTls();
	}

	/**
	 * creates 1 and only 1 XTerm
	 * 
	 */
	public static XTerm mkTerm() {
		if (!one) {
			xterm = new XTerm();
			return xterm;
		} else
			return xterm;
	}

	private XTerm() {
	}

	/**
	 * starts up the terminal, creates and initalizes threads and various values
	 * 
	 */
	@Override
	public void enterPrivateMode() {
		if (started)
			return;
		winFrame = new XTermRenderer();
		Dimension d;
		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		d = env.getMaximumWindowBounds().getSize();
		h = d.height;
		w = d.width;
		backbuffer = new BufferedImage(h, w, BufferedImage.TYPE_INT_RGB);
		g2d = backbuffer.createGraphics();
		winFrame.addKeyListener(new inputListener());
//		this.clearScreen();
		gl = new Thread(new GraphicsDraw(this), "Drawing Thread");
		gl.start();
		started = true;
//		this.clearScreen();
		render = new Thread(new RepaintControl(), "Render Thread");
		render.start();
	}

	/**
	 * ends the game, closing files and exits with 0
	 */
	@Override
	public void exitPrivateMode() {
		Util.closeStreams();
		done = true;
		try {
			winFrame.dispose();
		} catch (Exception e) {
		}
		try {
			gl.stop();
		} catch (Exception e) {
		}
		System.exit(0);
	}

	public void exitPrivateMode(int exitCode) {
		Util.closeStreams();
		done = true;
		winFrame.dispose();
		gl.stop();
		render.stop();
		System.exit(exitCode);
	}

	/**
	 * puts a char in the array
	 * 
	 * @param c
	 *            - char to draw
	 */
	@Override
	public void putCharacter(char c) {
		if (c != charArray[x][y]) {
			changed = true;
		}
		charArray[x][y] = c;
		x++;
		if (x == 90) {
			x = 0;
			y++;
		}
	}

	/**
	 * adds a char, but with color
	 */
	@Override
	public void putCharacter(char c, Color col) {
		if (c != charArray[x][y])
			changed = true;
		charArray[x][y] = c;
		colorArray[x][y] = col;
		x++;
		if (x == 90) {
			x = 0;
			y++;
		}
	}

	/**
	 * moves the virtual "cursor"
	 */
	@Override
	public void moveCursor(int xm, int ym) {
		x = xm;
		y = ym;
	}

	public static void putImage(Image i, int x, int y) {
		imageArray[x][y] = i;
	}

	@Override
	public void clearScreen() {
		for (int i = 0; i < 90; i++)
			for (int c = 0; c < 40; c++) {
				charArray[i][c] = ' ';
				colorArray[i][c] = Color.WHITE;
				boolImageArray[i][c] = false;
				imageArray[i][c] = null;
			}
		changed = true;
	}

	/**
	 * class to handle input, send it to InputParse.inputParse() uses
	 * KeyListener
	 * 
	 * @author matthew
	 * 
	 */
	private static class inputListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent arg0) {
			synchronized (synobj) {
				if (arg0.getKeyCode() == KeyEvent.VK_END)
					xterm.exitPrivateMode();
				InputParse.inputParse(arg0);
			}
		}

		public void keyReleased(KeyEvent arg0) {
		}

		public void keyTyped(KeyEvent arg0) {
		}

	}

	/**
	 * this class creates the window
	 * 
	 * @author matthew
	 * 
	 */
	private class XTermRenderer extends JFrame {
		private static final long serialVersionUID = 1L;
		private boolean f = true;

		public XTermRenderer() {
			super("The Maze of Menace");
			super.setExtendedState(MAXIMIZED_BOTH);
			super.addWindowListener(new WindowEventHandler());
			super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			GraphicsEnvironment grd = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			GraphicsDevice dev = grd.getDefaultScreenDevice();
			if (dev.isFullScreenSupported() && xterm.isFullScreenWanted())
				try {
					super.setIgnoreRepaint(true);
					super.setUndecorated(true);
					dev.setFullScreenWindow(this);
					fullScreen = true;
				} finally {
				}
			this.setIconImage(icon);
			super.setFocusable(true);
			super.setVisible(true);
		}

		/**
		 * draw in full screen mode
		 * 
		 * @param g
		 */
		public void render(Graphics g) {
			g.setColor(Color.BLACK);
			if (this.f) {
				g.fillRect(0, 0, w, h);
				this.f = false;
			}
			g.drawImage(backbuffer, 0, 0, null);
		}

		/**
		 * paints the image from the buffer
		 */
		@Override
		public void paint(Graphics g) {
			g.setColor(Color.BLACK);
			if (this.f) {
				g.fillRect(0, 0, w, h);
				this.f = false;
			}
			g.drawImage(backbuffer, 0, 0, null);
		}

	}

	protected static void partialClear() {
		for (int i = 0; i < 64; i++) {
			for (int c = 0; c < 32; c++) {
				charArray[i][c] = ' ';
				imageArray[i][c] = null;
			}
		}
	}

	/**
	 * this class handles the drawing of the double buffer as a thread.
	 * 
	 * @author matthew
	 * 
	 */
	private static class GraphicsDraw implements Runnable {
		private GraphicsDraw(XTerm x) {
		}

		/**
		 * Thread method, only calls graphicsUpdate() if needed
		 */
		@Override
		public void run() {
			Thread.currentThread();
			while (!done)
				try {
					if (changed) {
						if (FancyImageBuffer.isFancyWanted
								&& Init.getState() == GameState.NORMAL) {
							FancyImageBuffer.drawToIm(g2d);
							partialClear();
						} else {
							g2d.setColor(Color.BLACK);
							g2d.fillRect(0, 0, w, h);
						}
						this.graphicsUpdate();
					}
					Thread.sleep(35);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}

		/**
		 * creates the buffered image and makes a repaint request
		 */
		private void graphicsUpdate() {
			changed = false;
			g2d.setFont(Xfont);
			FontMetrics ftm = g2d.getFontMetrics(Xfont);
			int charWidth = ftm.charWidth(' ');
			int charHeight = ftm.getHeight();
			for (int i = 0; i < 90; i++)
				for (int c = 0; c < 50; c++) {
					if (isTilesWanted && imageArray[i][c] != null) {
						g2d.drawImage(imageArray[i][c], (i + 1) * charWidth,
								(c + 1) * charHeight, null);
						continue;
					}
					g2d.setColor(colorArray[i][c]);
					if (charArray[i][c] == ' ')
						continue;
					g2d.drawString(charArray[i][c] + "", (i + 1) * charWidth,
							(c + 2) * charHeight);
				}
			synchronized (sync) {
				sync.notify();
			}
		}

	}

	/**
	 * thread for repainting the window
	 * 
	 * @author matthew
	 * 
	 */
	private static class RepaintControl implements Runnable {
		/**
		 * Thread method. only updates in there is a repaint request
		 */
		@Override
		public void run() {
			while (!done) {
				try {
					synchronized (sync) {
						sync.wait(33l);
					}
				} catch (InterruptedException e) {}
				if (!fullScreen)
					winFrame.repaint();
				else
					winFrame.render(winFrame.getGraphics());
			}
		}
	}

	/**
	 * Class for changing the exit button behavior. Makes it call
	 * exitPrivateMode()
	 * 
	 * @author matthew
	 * 
	 */
	private static class WindowEventHandler extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent evt) {
			xterm.exitPrivateMode();
		}
	}

	private static class FullScreenChooser extends JFrame {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		FullScreenChooser() {
			super("Settings");
			JButton full = new JButton("FullScreen Mode");
			JButton windwd = new JButton("Windowed Mode");
			full.setActionCommand("y");
			windwd.setActionCommand("n");
			super.setSize(175, 200);
			FullScreenDecide decider = new FullScreenDecide();
			full.addActionListener(decider);
			windwd.addActionListener(decider);
			JPanel radioPanel = new JPanel(new GridLayout(0, 1));
			radioPanel.add(windwd);
			radioPanel.add(full);
			this.setIconImage(icon);
			this.add(radioPanel);
			super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			super.addWindowListener(new WindowEventHandler());
			super.setLocationRelativeTo(null);
			this.setVisible(true);
		}

		private static class FullScreenDecide implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				char c = arg0.getActionCommand().charAt(0);
				switch (c) {
				case 'y':
					synchronized (osync) {
						osync.notify();
						fullScreenWanted = true;
						choosen = true;
					}
					break;
				case 'n':
					synchronized (osync) {
						osync.notify();
						fullScreenWanted = false;
						choosen = true;
					}
					break;
				case 'd':

					break;
				}
			}

		}
	}

	private boolean isFullScreenWanted() {
		FullScreenChooser f = new FullScreenChooser();
		try {
			synchronized (osync) {
				while (!choosen)
					osync.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		f.dispose();
		return fullScreenWanted;
	}
}
