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
package run;

import java.io.IOException;
import json.JSONObj;
import json.parser.JSONParseException;
import json.parser.JSONParser;

public class Settings{

	private static String fnt;

	private static boolean gl, ft, tls;

	private static int fps, fsz, glX, glY, glp, gle;

	public static String getFnt(){
		return fnt;
	}

	public static boolean isGl(){
		return gl;
	}

	public static boolean isFt(){
		return ft;
	}

	public static boolean isTls(){
		return tls;
	}

	public static int getFps(){
		return fps;
	}

	public static int getFsz(){
		return fsz;
	}

	public static void initSettings(){
		JSONObj j = null;
		try{
			j = JSONParser.parse_from_path("config/config.json");
		}catch(JSONParseException | IOException e){
			e.printStackTrace();
			return;
		}
		fps = j.geti("fps");
		fnt = j.gets("font.name");
		fsz = j.geti("font.size");
		glX = j.geti("gl.x");
		glY = j.geti("gl.y");
		glp = j.geti("gl.ipause");
		gle = j.geti("gl.ep");
		switch(j.gets("disp")){
			case "term":
				break;
			case "GL":
				gl = true;
			case "ftiles":
				ft = true;
			case "btiles":
				tls = true;
		}
	}

	private Settings() {
	}

	public static int getGlY(){
		return glY;
	}

	public static int getGlX(){
		return glX;
	}

	public static int getGlp(){
		return glp;
	}

	public static int getGle(){
		return gle;
	}

}
