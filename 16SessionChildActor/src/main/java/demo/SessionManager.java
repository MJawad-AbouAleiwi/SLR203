package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.*;
import demo.MyMessage;

public class SessionManager extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef session;
    private ActorRef client;

	public SessionManager() {
    }

	// Static function creating actor
	public static Props createActor() {
		return Props.create(SessionManager.class, () -> {
			return new SessionManager();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof String){
			String msg = (String) message;
            if(msg.equals("createSession")){
                session = getContext().actorOf(Session.createActor(getSender()), "session");
                MyMessage msg2 = new MyMessage(session);
                getSender().tell(msg2, getSelf());
                log.info("["+getSelf().path().name()+"] created a session required by ["+ getSender().path().name()+"]");
            }
            else if(msg.equals("endSession")){
                getContext().stop(session);
                log.info("["+getSelf().path().name()+"] closed the session demanded by ["+ getSender().path().name()+"]");
            }
		}
		
	}

}