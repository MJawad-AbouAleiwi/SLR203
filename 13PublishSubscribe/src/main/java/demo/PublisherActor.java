package demo;

import java.time.Duration;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class PublisherActor extends UntypedAbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	private ActorRef topicBroadcaster;

	private String m0;
	private int count=1;

	public PublisherActor() {
	}

	public static Props createActor() {
		return Props.create(PublisherActor.class, () -> {
			return new PublisherActor();
		});
	}

	private void content(){
		String name = this.getSelf().path().name();
		switch (name) 
		{
			case "publisher1":
				if (count==1){
					m0 = "hello";
					getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1000), getSelf(), "go1", getContext().system().dispatcher(), ActorRef.noSender());
				} else {
					m0 = "hello2";
					getContext().system().scheduler().scheduleOnce(Duration.ofMillis(3000), getSelf(), "go2", getContext().system().dispatcher(), ActorRef.noSender());
				}
				break;
			case "publisher2":
				if (count==1){
					m0 = "world";
					getContext().system().scheduler().scheduleOnce(Duration.ofMillis(2000), getSelf(), "go1", getContext().system().dispatcher(), ActorRef.noSender());
				}
				break;
			default:
				break;
		}
		count--;
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof ActorRef) {
			topicBroadcaster = ((ActorRef) message);
			content();
		}
		if (message instanceof String ){
			if ((String)message == "go1"){
				MessageString m1 = new MessageString(m0);
				topicBroadcaster.tell(m1, this.getSelf());

				content();
			}
			else if ((String)message == "go2"){
				MessageString m1 = new MessageString(m0);
				topicBroadcaster.tell(m1, this.getSelf());
			}
		}
		
	}
	
	
}
