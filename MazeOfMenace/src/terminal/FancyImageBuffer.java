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

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;

import run.Settings;
import run.Util;

public class FancyImageBuffer {

	public static final FancyImageBuffer buffer = new FancyImageBuffer();
	private static Image[][] imageArray = new Image[90][50];
	private static char[][] charArray = new char[90][50];
	private int x, y;
	public static boolean isFancyWanted = true;
	public boolean changed;
	static{			
		isFancyWanted = Settings.isFt();
			
	}

	public FancyImageBuffer() {
		
	}

	public void addImage(int x, int y, Image i) {
		if (!isFancyWanted)
			return;
		if (i != imageArray[x][y])
			XTerm.changed = true;
		imageArray[x][y] = i;
	}

	public void putCharacter(char c) {
		if (!isFancyWanted)
			return;
		charArray[this.x][this.y] = c;
		this.x++;
		if (this.x == 90) {
			this.x = 0;
			this.y++;
		}

	}

	public void moveCursor(int xm, int ym) {
		this.x = xm;
		this.y = ym;
	}

	public static void clearPics() {
		for (int i = 0; i < 90; i++)
			for (int c = 0; c < 50; c++)
				imageArray[i][c] = null;
	}

	protected static boolean drawToIm(Graphics2D g2d) {
		if (!isFancyWanted)
			return false;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, 2000, 2000);
		g2d.setFont(XTerm.Xfont);
		FontMetrics ftm = g2d.getFontMetrics(XTerm.Xfont);
		ftm.charWidth(' ');
		ftm.getHeight();
		for (int i = 0; i < 90; i++)
			for (int c = 0; c < 50; c++)
				if (imageArray[i][c] != null) {
					g2d.drawImage(imageArray[i][c], (i + 1) * 24, (c + 1) * 24,
							null);
					continue;
				}

		
		return true;
	}

}
