/*******************************************************************************
 * Copyright (c) 2013 Matthew Gruda.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     matthew - initial API and implementation
 ******************************************************************************/
package engine.image;

import org.lwjgl.opengl.GL11;

public class ColorRGBA {
	
	public static final ColorRGBA white = new ColorRGBA(1f, 1f, 1f, 1f);

	public final float r, g, b, a;
	
	public ColorRGBA(float r, float g, float b){
		this(r, g, b, 1f);
	}
	
	public ColorRGBA(float r, float g, float b, float a){
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public void bindColor(){
		GL11.glColor4f(r, g, b, a);
	}
	
}
