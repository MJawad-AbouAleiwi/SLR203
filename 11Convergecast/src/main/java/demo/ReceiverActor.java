package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ReceiverActor extends UntypedAbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ActorRef mergerRef; 
	
	public ReceiverActor() {}

	public static Props createActor() {
		return Props.create(ReceiverActor.class, () -> {
			return new ReceiverActor();
		});
	}


	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof ActorRef) {
			mergerRef = ((ActorRef) message);
			mergerRef.tell("hello", this.getSelf());
		}
		
		else if (message instanceof String){
			log.info("["+getSelf().path().name()+"] received message '"+((String)message)+"' from ["+ getSender().path().name() +"]");
		}
	}
	
	
}
