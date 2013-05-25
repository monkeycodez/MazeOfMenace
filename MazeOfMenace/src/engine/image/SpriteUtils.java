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

public class SpriteUtils {
	
	public static void setSprite(int x, int y, UVSettable toset){
		SpriteTexture t = toset.getSprite();
		float us = 0, vs = 0, ue = 0, ve = 0;
	//	System.out.println(t.getUwidth()+"   "+ t.getVheight());
		us = x * (float) t.getUwidth() ;
		ue = us + t.getUwidth();
		vs = (float)y * t.getVheight();
		ve = vs + t.getVheight();
	//	System.out.println("us: "+us+" ue: "+ue+" vs: "+vs+" ve: "+ve);
		
		toset.setUV(us, ue, vs, ve, x, y);
		
	}

}
