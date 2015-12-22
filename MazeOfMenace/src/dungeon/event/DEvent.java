package dungeon.event;

import dungeon.tile.Tile;
import entity.Entity;

public class DEvent{

	private String type;

	private Entity from, to;

	private Tile at, tfrom;

	public DEvent(String t) {
		type = t;
	}

	public DEvent(String type, Entity from, Entity to) {
		this.type = type;
		this.from = from;
		this.to = to;
	}

	public String get_type(){
		return type;
	}

	public Entity getFrom(){
		return from;
	}

	public void setFrom( Entity from ){
		this.from = from;
	}

	public Entity getTo(){
		return to;
	}

	public void setTo( Entity to ){
		this.to = to;
	}

	public Tile getAt(){
		return at;
	}

	public void setAt( Tile at ){
		this.at = at;
	}

	public Tile getTfrom(){
		return tfrom;
	}

	public void setTfrom( Tile tfrom ){
		this.tfrom = tfrom;
	}

}
