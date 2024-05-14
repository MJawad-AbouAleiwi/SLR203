package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class FirstActor extends UntypedAbstractActor {


    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private ActorRef ref;

    public FirstActor() {}

    public static Props createActor() {
        return Props.create(FirstActor.class, () -> {
            return new FirstActor();
        });
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof MyMessage) {
            MyMessage temp = (MyMessage) message;
            if (temp.message != null)
            {
                log.info("["+getSelf().path().name()+"] received a response from ["+ getSender().path().name() +"] with data: ["+temp.message+"]");
            }
            if (temp.ref != null)
            {
                log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");
                ref = temp.ref;
                for (int i = 1; i < 31; i++) {
                    String request = "Request " + Integer.toString(i);
                    MyMessage req = new MyMessage(request);
                    ref.tell(req, getSelf());
                }  
            }
        }
    }
}