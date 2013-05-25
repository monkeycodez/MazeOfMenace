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

public interface UVSettable {

	public void setUV(float ustart, float uend, float vstart, float vend, int x, int y);
	
	public SpriteTexture getSprite();

}
