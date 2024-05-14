package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class FirstActor extends UntypedAbstractActor{

	
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef b;
	private ActorRef c;
	

	public FirstActor() {}

	public static Props createActor() {
		return Props.create(FirstActor.class, () -> {
			return new FirstActor();
		}); 
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MyMessage){
			MyMessage temp = (MyMessage) message;
			if (temp.ref1!=null && temp.ref2!=null)
			{
				log.info("["+getSelf().path().name()+"] received message with two references from ["+ getSender().path().name() +"]");
			
				b = temp.ref1;
				c = temp.ref2;
				MyMessage m = new MyMessage("Req1", c);
				b.tell(m, this.getSelf());
			}
		}
	}
}
