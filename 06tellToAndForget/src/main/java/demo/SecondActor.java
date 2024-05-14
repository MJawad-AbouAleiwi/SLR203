package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SecondActor extends UntypedAbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public SecondActor() {}

    public static Props createActor() {
		return Props.create(SecondActor.class, () -> {
			return new SecondActor();
		});
	}

    public void onReceive(Object message) throws Throwable {
        if (message instanceof MyMessage){
            MyMessage m = (MyMessage) message;
            log.info("[" + getSelf().path().name() + "] received message from [" + getSender().path().name() + "] with data: [" + m.message + "]");
            MyMessage reply = new MyMessage("hi!");
            getSender().tell(reply, self());
        }
    }
}
