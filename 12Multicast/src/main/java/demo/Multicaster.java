package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Multicaster extends UntypedAbstractActor{


	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	private ArrayList<MessageGroup> listOfGroups = new ArrayList<MessageGroup>();

	public Multicaster() {}


	public static Props createActor() {
		return Props.create(Multicaster.class, () -> {
			return new Multicaster();
		});
	}


	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MessageGroup){
			log.info("["+getSelf().path().name()+"] received '"+((MessageGroup)message).groupName +"' from ["+ getSender().path().name() +"]");

			listOfGroups.add((MessageGroup)message);
		}


		if(message instanceof MessageGroupData){
			log.info("["+getSelf().path().name()+"] received message '"+((MessageGroupData)message).data + " to " + ((MessageGroupData)message).groupName + "' from ["+ getSender().path().name() +"]");
			
			if (getSender().path().name()=="sender") {
				for (MessageGroup group : listOfGroups){
					if (((MessageGroupData)message).groupName == group.groupName){
						for (ActorRef a : group.actorList){
							String m = new String(((MessageGroupData)message).data);
							a.tell(m, getSelf());
						}
					}
				}
			}
		}
	}


}
