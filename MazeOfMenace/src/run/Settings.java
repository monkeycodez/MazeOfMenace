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
package run;


public class Settings {

	private static String fnt;
	private static boolean gl, ft, tls;
	private static int fps, fsz, glX, glY, glp, gle;
	
	
	public static String getFnt() {
		return fnt;
	}



	
	public static boolean isGl() {
		return gl;
	}



	
	public static boolean isFt() {
		return ft;
	}



	
	public static boolean isTls() {
		return tls;
	}



	
	public static int getFps() {
		return fps;
	}



	
	public static int getFsz() {
		return fsz;
	}



	public static void initSettings(){
		String s = Util.getTxtMsg("config/mm.cfg");
		String[] sarr = s.split("\n");
		for(String str : sarr){
			String st1 = str.split("=")[0];
			String st2 = str.split("=")[1];
			if(st1.equals("gl")){
				gl = Boolean.parseBoolean(st2);
			}else if("fnt".equals(st1)){
				fnt = st2;
			}else if("ftiles".equals(st1)){
				ft = Boolean.parseBoolean(st2);
			}else if("btiles".equals(st1)){
				tls = Boolean.parseBoolean(st2);
			}else if("fps".equals(st1)){
				fps = Integer.parseInt(st2);
			}else if("fsize".equals(st1)){
				fsz = Integer.parseInt(st2);
			}else if("glx".equals(st1)){
				glX = Integer.parseInt(st2);
			}else if("gly".equals(st1)){
				glY = Integer.parseInt(st2);
			}else if("glinpause".equals(st1)){
				glp = Integer.parseInt(st2);
			}else if("glep".equals(st1)){
				gle = (Integer.parseInt(st2));
			}

		}
	}


	
	private Settings(){}




	public static int getGlY() {
		return glY;
	}



	public static int getGlX() {
		return glX;
	}




	public static int getGlp() {
		return glp;
	}




	public static int getGle() {
		return gle;
	}

}
