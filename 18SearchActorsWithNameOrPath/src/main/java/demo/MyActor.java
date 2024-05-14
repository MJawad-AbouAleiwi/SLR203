package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import akka.actor.ActorIdentity;
import akka.actor.ActorSelection;
import akka.actor.Identify;
import java.util.*;

public class MyActor extends UntypedAbstractActor{

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    int count = 1;
    ActorIdentity actorIdentity;

	public MyActor() {
    }

	public static Props createActor() {
		return Props.create(MyActor.class, () -> {
			return new MyActor();
		});
	}

    @Override
	public void onReceive(Object message) throws Throwable {
        if(message instanceof String){
            String msg = (String) message;
            if(msg.equals("create"))
                getContext().actorOf(MyActor.createActor(), "actor"+count);
                log.info("["+getSelf().path().name()+"] created actor" + count);
                count++;
        }
        if(message instanceof ActorIdentity) {
            this.actorIdentity = (ActorIdentity) message;
            try{
                ActorRef actor = this.actorIdentity.getActorRef().get();
                log.info("["+getSelf().path().name()+"] returned this path: " + actor.path());
            }catch(Exception e){}
            
        }
    }
} 