package run;


public class Settings {

	private static String fnt;
	private static boolean gl, ft, tls;
	private static int fps, fsz;
	
	
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
			}

		}
	}


	
	private Settings(){}
}
