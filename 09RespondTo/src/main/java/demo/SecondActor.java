package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SecondActor extends UntypedAbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ActorRef c;

	public SecondActor() {
	}

	public static Props createActor() {
		return Props.create(SecondActor.class, () -> {
			return new SecondActor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof MyMessage) {
			MyMessage temp = (MyMessage) message;
			String r = null;
			log.info("[" + getSelf().path().name() + "] received message '" + temp.data +"' from ["+ getSender().path().name() + "]");
			if (temp.data == "Req1") {
				r = "Res1";
			}
			c = temp.ref1;
			MyMessage m = new MyMessage(r);
			c.tell(m, this.getSelf());
		}
	}
}
