package entity.mons;

import java.io.*;
import java.util.*;
import json.*;
import json.parser.*;

public class MonsLoader{

	public static List<MonsTemplate> read_tmpls(String path){
		JSONObj mons = null;
		try{
			mons = JSONParser.parse_from_path(path);
		}catch(FileNotFoundException e){
			e.printStackTrace();
			return null;
		}catch(JSONParseException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		if(mons == null){
			return null;
		}
		List<MonsTemplate> mlist = new ArrayList<>();

		JSONArray monarr = mons.geta("monster_list");

		for(JSONVal monsv : monarr){
			if(!monsv.is_obj()){
				//TODO: some sort of error system
				return null;
			}
			mlist.add(MonsTemplate.build_mons(monsv.as_obj()));
		}

		return mlist;
	}

}
