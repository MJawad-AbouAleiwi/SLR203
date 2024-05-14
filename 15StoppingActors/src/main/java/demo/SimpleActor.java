package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;


public class SimpleActor extends UntypedAbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public SimpleActor() {
    }

	public static Props createActor() {
		return Props.create(SimpleActor.class, () -> {
			return new SimpleActor();
		});
    }

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof String)
			log.info("[" + getSelf().path().name() +"]" + " received message from [" + getSender().path().name() + "]");
	}
	
	
}
