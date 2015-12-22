package dungeon.event;

public interface EventListener{

	public void recieve_event( DEvent event );

	public static EventListener null_listener = new EventListener(){

		@Override
		public void recieve_event( DEvent event ){
			//Do nothing
		}
	};
}
