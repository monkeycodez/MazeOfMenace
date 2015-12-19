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

	private static JSONObj settings;

	public static void initSettings(){
		JSONObj j = null;
		try{
			j = JSONParser.parse_from_path("config/config.json");
			settings = j;
		}catch(JSONParseException | IOException e){
			e.printStackTrace();
			return;
		}
	}

	public static boolean getb( String key, boolean def ){
		return settings.getb(key, def);
	}

	public static boolean getb( String key ){
		return settings.getb(key);
	}

	public static double getd( String key, double def ){
		return settings.getd(key, def);
	}

	public static double getd( String key ){
		return settings.getd(key);
	}

	public static int geti( String key, int def ){
		return settings.geti(key, def);
	}

	public static int geti( String key ){
		return settings.geti(key);
	}

	public static String gets( String key, String def ){
		return settings.gets(key, def);
	}

	public static String gets( String key ){
		return settings.gets(key);
	}

}
