package terminal.gld.event;

import entity.Entity;

public interface GLDEvent {

	public void applyPLocStart();
	public void applyPLocEnd();
	
	public void applyEntChStart(Entity e);
	public void applyEnrChEnd(Entity e);
	
	public void drawFinalChange();
	
	public boolean done();
	
}
