package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class TopicBroadcastActor extends UntypedAbstractActor{

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ArrayList<ActorRef> listOfReceivers = new ArrayList<ActorRef>();

	public TopicBroadcastActor() {}

	public static Props createActor() {
		return Props.create(TopicBroadcastActor.class, () -> {
			return new TopicBroadcastActor();
		});
	}


	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MessageSubscribe){
			if (((MessageSubscribe)message).sub){
				log.info("["+ getSender().path().name() +"]" + "subscribed to " + "["+getSelf().path().name()+"]");
				listOfReceivers.add(getSender());
			}
			else{
				log.info("["+ getSender().path().name() +"]" + "unsubscribed from " + "["+getSelf().path().name()+"]");
				listOfReceivers.remove(getSender());
			}
		}

		if(message instanceof MessageString){
			log.info("["+getSelf().path().name()+"] received message '"+((MessageString)message).data +"' from ["+ getSender().path().name() +"]");

			for (ActorRef subscriber : listOfReceivers){
				subscriber.tell(message, getSender());
			}
			
		}
			
	}

}
