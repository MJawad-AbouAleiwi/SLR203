package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ThirdActor extends UntypedAbstractActor {
    
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	public ThirdActor() {}

	public static Props createActor() {
		return Props.create(ThirdActor.class, () -> {
			return new ThirdActor();
		});
	}

    @Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MyMessage){
			MyMessage temp = (MyMessage) message;
			log.info("["+getSelf().path().name()+"] received message '"+temp.data+"' from ["+ getSender().path().name() +"]");
		}
    }
}


