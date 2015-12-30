package util;

import java.util.Random;
import java.util.concurrent.*;

public class Utils{

	public static ExecutorService bgthread = Executors
		.newSingleThreadExecutor();

	private static Random r = new Random();

	public static DiceRoll get_dice_roll( String s ){
		int d = s.indexOf('d');
		int nd = 0, faces = 0, plus = 0;
		if(d == 0){
			if(s.charAt(0) == '+'){
				s = s.substring(1);
			}
			plus = Integer.parseInt(s);
		}else{
			int pl = s.indexOf('+');
			if(pl != 0){
				plus = Integer.parseInt(s.substring(plus + 1));
				s = s.substring(0, plus);
			}
			String[] its = s.split("d");
			nd = Integer.parseInt(its[0]);
			faces = Integer.parseInt(its[1]);
		}
		return new DiceRoll(nd, faces, plus, r);
	}

}
