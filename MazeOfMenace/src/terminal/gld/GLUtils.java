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
package terminal.gld;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class GLUtils {
	
	public static FloatBuffer toBuf(float[] f){
		return (FloatBuffer) BufferUtils.createFloatBuffer(f.length).put(f).flip();
	}

}
