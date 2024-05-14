package demo;

import java.time.Duration;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SenderActor extends UntypedAbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ActorRef balancerRef;

	public SenderActor() {
	}

	public static Props createActor() {
		return Props.create(SenderActor.class, () -> {
			return new SenderActor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof ActorRef) {
			balancerRef = (ActorRef)message;
			getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1000), getSelf(), "go1", getContext().system().dispatcher(), ActorRef.noSender());
		}
		if (message instanceof String){
			if ((String)message == "go1"){
				MessageString m1  = new MessageString("t1");
				balancerRef.tell(m1, this.getSelf());
				getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1000), getSelf(), "go2", getContext().system().dispatcher(), ActorRef.noSender());
			}
			if ((String)message == "go2"){
				MessageString m1  = new MessageString("t2");
				balancerRef.tell(m1, this.getSelf());
				getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1000), getSelf(), "go3", getContext().system().dispatcher(), ActorRef.noSender());
			}
			if ((String)message == "go3"){
				MessageString m1  = new MessageString("t3");
				balancerRef.tell(m1, this.getSelf());
			}
		}
		
	}
	
	
}
