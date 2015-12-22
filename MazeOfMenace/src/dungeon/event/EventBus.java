package dungeon.event;

import java.util.LinkedList;
import java.util.List;

public class EventBus{

	private static List<EventListener> listeners = new LinkedList<>();

	public static void post_event( DEvent event ){
		for(EventListener l : listeners){
			l.recieve_event(event);
		}
	}

	public static void add_listener( EventListener ler ){
		listeners.add(ler);
	}

}
