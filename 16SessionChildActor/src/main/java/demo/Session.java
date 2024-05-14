package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.*;
import demo.MyMessage;

public class Session extends UntypedAbstractActor{

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef client;

	public Session() {}
  
  	public Session(ActorRef client) {
		this.client = client;
	}


	public static Props createActor(ActorRef client) {
		return Props.create(Session.class, () -> {
			return new Session(client);
		});
	}

 

        	@Override
	public void onReceive(Object message) throws Throwable {
        if(message instanceof String){
            String msg = (String) message;
            if(msg.equals("m1")){
                client.tell("m2", getSelf());
                log.info("["+getSelf().path().name()+"] received message "+msg +" from ["+ getSender().path().name()+ "]");
            }
            else if(msg.equals("m3")){
                client.tell("m4", getSelf());
                log.info("["+getSelf().path().name()+"] received message "+msg +" from ["+ getSender().path().name()+ "]");
            }
        }
	}

} 