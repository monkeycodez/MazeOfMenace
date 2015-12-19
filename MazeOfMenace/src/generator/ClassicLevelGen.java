package generator;

import java.util.Random;
import dungeon.Dungeon;
import dungeon.Level;
import dungeon.tile.Tile;
import dungeon.tile.TileTemplate;
import dungeon.tile.template.StairDownTmpl;
import dungeon.tile.template.StairUpTmpl;

public class ClassicLevelGen implements LevelGenStrategy{

	private static final int xsz = 64, ysz = 32;

	private static int xup, yup, xd, yd;

	@Override
	public Level form_dungeon( Dungeon dgn, int depth ){
		TileTemplate[][] lvl = new TileTemplate[xsz][ysz];
		for(int x = 0; x < lvl.length; x++){
			for(int y = 0; y < lvl[0].length; y++){
				lvl[x][y] = TileTemplate.wall;
			}
		}
		makeRooms(lvl);
		makeCorridors(lvl);
		makeStairs(lvl);
		mkFountains(lvl);
		Level l = new Level(depth, dgn, lvl);
		l.setUpStrX(xup);
		l.setUpStrY(yup);
		l.setDwStrX(xd);
		l.setDwStrY(yd);
		return l;
	}

	private static int xSt, ySt;

	private static Random r = new Random();

	/**
	 * makes randomly spaced & sized rooms this method only determines where to
	 * place, how big and how many. calls makeRoom() for room creation
	 * 
	 * @param lvl
	 *            the level to operate on
	 */
	private static void makeRooms( TileTemplate[][] lvl ){
		// first make num rooms. will be 4-8 for now
		int numRooms = r.nextInt(9) + 4;
		// now randomly gen each room
		for(int i = 0; i < numRooms; i++){
			// now determine location
			int x = r.nextInt(55) + 1, y = r.nextInt(23);
			// make dimensions
			int xSize = r.nextInt(7) + 3, ySize = r.nextInt(7) + 3;
			// now the actual room
			makeRoom(lvl, x, y, xSize, ySize);
		}
	}

	/**
	 * makes a room from upper left corner. rooms have all floors for tile
	 * 
	 * @param lvl
	 *            - level obj to act on
	 * @param x
	 *            upper left corner x coord
	 * @param y
	 *            upper left corner y coord
	 * @param xSize
	 *            width
	 * @param ySize
	 *            height
	 */
	private static void makeRoom(
		TileTemplate[][] lvl,
		int x,
		int y,
		int xSize,
		int ySize ){
		// System.out.println(x + " "+y+" "+xSize+" "+ySize );
		for(int i = x; i < xSize + x; i++){
			for(int c = y; c < ySize + y; c++){
				try{
					lvl[i][c] = TileTemplate.floor;
				}catch(ArrayIndexOutOfBoundsException e){
					// System.out.println(e);
				}
			}
		}
	}

	/**
	 * will detirmine where, size, num to make corridors
	 * 
	 * @param lvl
	 *            is the level to operate on
	 */
	private static void makeCorridors( TileTemplate[][] lvl ){
		// gen num
		int numCors = r.nextInt(18) + 9;
		for(int i = 0; i < numCors; i++){
			// generate place. make sure is valid (next to room)
			for(; true;){
				// make random location
				int x = r.nextInt(50) + 6, y = r.nextInt(20) + 6;
				// check if it is good
				CorridorPlace check = isValidCorPlace(lvl, x, y);
				if(check != CorridorPlace.FALSE){
					int length = r.nextInt(40) + 15;
					// finally make make the corridor
					makeCorridor(lvl, x, y, length, check);
					break;
				}
			}
		}
	}

	/**
	 * combines checking if x, y is a valid corridor place with choosing a
	 * direction to go for corridor
	 * 
	 * @param lvl
	 *            level to operate on
	 * @param x
	 *            coord of tile to test
	 * @param y
	 *            coord of tile to test
	 * @return direction if it is true, FALSE if cannot work
	 */
	private static CorridorPlace isValidCorPlace(
		TileTemplate[][] lvl,
		int x,
		int y ){
		if(lvl[x][y] == null
			|| lvl[x][y].can_walk()){
			return CorridorPlace.FALSE;
		}
		if(lvl[x + 1][y].can_walk()){
			return CorridorPlace.EAST;
		}else if(lvl[x - 1][y].can_walk()){
			return CorridorPlace.WEST;
		}else if(lvl[x][y + 1].can_walk()){
			return CorridorPlace.NORTH;
		}else if(lvl[x][y - 1].can_walk()){
			return CorridorPlace.SOUTH;
		}
		return CorridorPlace.FALSE;
	}

	private enum CorridorPlace{
		EAST, WEST, NORTH, SOUTH, FALSE
	}

	/**
	 * 
	 * @param lvl
	 * @param x
	 * @param y
	 * @param length
	 * @param direction
	 */
	private static void makeCorridor(
		TileTemplate[][] lvl,
		int x,
		int y,
		int length,
		CorridorPlace direction ){
		switch(direction){
			case EAST:
				for(int i = 0; i < length; i++){
					if(x + i < 63){
						lvl[x + i][y] = TileTemplate.floor;
					}
				}
				break;
			case WEST:
				for(int i = 0; i < length; i++){
					if(x - i > 0){
						lvl[x - i][y] = TileTemplate.floor;

					}
				}
				break;
			case NORTH:
				for(int i = 0; i < length; i++){
					if(y - i > 0){
						lvl[x][y - i] = TileTemplate.floor;

					}

				}
				break;
			case SOUTH:
				for(int i = 0; i < length; i++){
					if(i + y < 31){
						lvl[x][y + i] = TileTemplate.floor;

					}

				}
				break;
			case FALSE:// do nothing
		}
	}

	/**
	 * will make stairs
	 * 
	 * @param lvl
	 */
	private static void makeStairs( TileTemplate[][] lvl ){
		makeUpStair(lvl);
		detDwStairPlace(lvl);
	}

	/**
	 * makes up stair at random location
	 * 
	 * @param lvl
	 **/
	private static void makeUpStair( TileTemplate[][] lvl ){
		while(true){
			xSt = r.nextInt(63) + 1;
			ySt = r.nextInt(31) + 1;
			if(lvl[xSt][ySt].can_walk()){
				Tile to = null;
				lvl[xSt][ySt] = new StairUpTmpl(null);
				xup = xSt;
				yup = ySt;
				return;
			}
		}
	}

	private static void mkDwnStr( TileTemplate[][] lvl ){
		int x = 0, y = 0;
		Tile at = null;
		for(int i = 2000; i > 0; i--){
			x = r.nextInt(63) + 1;
			y = r.nextInt(31) + 1;
			if(lvl[x][y].can_walk()
				&&
				lvl[x][y].type() != StairUpTmpl.STAIRUP){
				break;
			}
		}
		lvl[x][y] = new StairDownTmpl();
		xd = x;
		yd = y;
	}

	private static void detDwStairPlace( TileTemplate[][] lvl ){
		int xLoc = xup, yLoc = yup;
		int iters = r.nextInt(10) + 10;
		TileTemplate at = lvl[xLoc][yLoc];
		outer: for(int i = 0; i < iters; i++){
			CorridorPlace c = getDir(lvl, xLoc, yLoc);
			int steps = r.nextInt(10) + 1;
			switch(c){
				case EAST:
					for(int z = 0; z < steps; z++){
						if(bc(xLoc, yLoc) &&
							lvl[xLoc + 1][yLoc]
								.can_walk() &&
							lvl[xLoc + 1][yLoc]
								.type() != StairUpTmpl.STAIRUP){
							xLoc++;
						}else{
							break;
						}
					}
					break;
				case FALSE:
					break outer;
				case NORTH:
					for(int z = 0; z < steps; z++){
						if(bc(xLoc, yLoc) &&
							lvl[xLoc][yLoc - 1]
								.can_walk() &&
							lvl[xLoc][yLoc - 1]
								.type() != StairUpTmpl.STAIRUP){
							yLoc--;
						}else{
							break;
						}
					}
					break;
				case SOUTH:
					for(int z = 0; z < steps; z++){
						if(bc(xLoc, yLoc) &&
							lvl[xLoc][yLoc + 1]
								.can_walk() &&
							lvl[xLoc][yLoc + 1]
								.type() != StairUpTmpl.STAIRUP){
							yLoc++;
						}else{
							break;
						}
					}
					break;
				case WEST:
					for(int z = 0; z < steps; z++){
						if(bc(xLoc, yLoc) &&
							lvl[xLoc - 1][yLoc]
								.can_walk() &&
							lvl[xLoc - 1][yLoc]
								.type() != StairUpTmpl.STAIRUP){
							xLoc--;
						}else{
							break;
						}
					}
					break;
				default:
					break;

			}
		}
		if(lvl[xLoc][yLoc].type() == StairUpTmpl.STAIRUP){
			mkDwnStr(lvl);
			return;
		}
		int x = xLoc, y = yLoc;
		lvl[x][y] = new StairDownTmpl();
		xd = x;
		yd = y;
	}

	private static boolean bc( int x, int y ){
		if(x > 0 && x < 63){
			if(y > 0 && y < 63){
				return true;
			}
		}
		return false;
	}

	private static CorridorPlace
	getDir( TileTemplate[][] lvl, int x, int y ){
		for(int i = 0; i < 2000; i++){
			switch(r.nextInt(4)){
				case 0: // north
					if(bc(x, y) &&
						lvl[x - 1][y].can_walk()){
						return CorridorPlace.NORTH;
					}
					break;
				case 1:
					if(bc(x, y) &&
						lvl[x + 1][y].can_walk()){
						return CorridorPlace.SOUTH;
					}
					break;
				case 2:
					if(bc(x, y) &&
						lvl[x][y - 1].can_walk()){
						return CorridorPlace.WEST;
					}
					break;
				case 3:
					if(bc(x, y) &&
						lvl[x][y + 1].can_walk()){
						return CorridorPlace.EAST;
					}
			}
		}
		return CorridorPlace.FALSE;
	}

	private static void mkFountains( TileTemplate[][] lvl ){
		int i = new Random().nextInt(4);
		for(int c = 0; c < i; c++){
			while(true){
				int x = r.nextInt(63) + 1;
				int y = r.nextInt(31) + 1;
				if(lvl[x][y].can_walk()){
					lvl[x][y] = TileTemplate.fountain;
					break;
				}
			}
		}
	}

}
