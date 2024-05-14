package demo;

import java.time.Duration;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SenderActor extends UntypedAbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ActorRef mergerRef;

	private String currentMessageToSend = "hi";
	private int count = 1; 

	
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
			
			mergerRef = ((ActorRef) message);
			mergerRef.tell("join", this.getSelf());

			getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1000), getSelf(), "go", getContext().system().dispatcher(), ActorRef.noSender());
		}
		if (message instanceof String){
			log.info("["+getSelf().path().name()+"] received message '"+((String)message) +"' from ["+ getSender().path().name() +"]");
			if ((String)message == "go"){
				mergerRef.tell(currentMessageToSend, this.getSelf());

				if (this.getSelf().path().name()=="c"){
					mergerRef.tell("unjoin", this.getSelf());
				}

				else if (count!=0){
					currentMessageToSend = "hi2";
					getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1000), getSelf(), "go", getContext().system().dispatcher(), ActorRef.noSender());
					count--;
				}
			}
		}
		
	}
	
	
}
