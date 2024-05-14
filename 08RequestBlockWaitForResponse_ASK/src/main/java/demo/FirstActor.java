package demo;

import akka.actor.Props;
import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.dispatch.*;
import akka.pattern.*;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.Await;
import scala.concurrent.Promise;
import akka.util.*;
import java.time.Duration;


public class FirstActor extends UntypedAbstractActor{

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public FirstActor(ActorRef rec) {
		MyMessage m1 = new MyMessage("Req 1");
		Timeout t = Timeout.create(Duration.ofSeconds(10));
		Future<Object> future = Patterns.ask(rec, m1, t);
		MyMessage res1 = null;
		try {
			res1 = (MyMessage) Await.result(future, t.duration());
			log.info("["+getSelf().path().name()+"] received message with data: ["+res1.data+"]");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MyMessage m2 = new MyMessage("Req 2");
		Timeout t2 = Timeout.create(Duration.ofSeconds(10));
		Future<Object> future2 = Patterns.ask(rec, m2, t2);
		MyMessage res2 = null;
		try {
			res2 = (MyMessage) Await.result(future2, t.duration());
			log.info("["+getSelf().path().name()+"] received message with data: ["+res2.data+"]");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Props createActor(ActorRef rec) {
		return Props.create(FirstActor.class, () -> {
			return new FirstActor(rec);
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MyMessage){
			MyMessage m = (MyMessage) message;
			log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"] with data: ["+m.data+"]");
		}
	}
}