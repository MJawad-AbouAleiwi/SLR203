package demo;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class FirstActor extends UntypedAbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private ActorRef destination;
    private ActorRef transmitter;

    public FirstActor() {
    }

    public static Props createActor() {
        return Props.create(FirstActor.class, () -> new FirstActor());
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof MyMessage) 
        {
            MyMessage temp = (MyMessage)message;
            // if start is received
            if (temp.destination == null && temp.transmitter == null)
            {
                log.info("[" + getSelf().path().name() + "] received message from [" + getSender().path().name() + "] with data: [" + temp.message + "]");
                if (temp.message.equals("start"))
                {
                    // send to the transmitter the message + ref of b
                    MyMessage r = new MyMessage("hello", destination);
                    transmitter.tell(r, self());
                }
            }
            // two references are received
            if (temp.destination != null && temp.transmitter != null)
            {
                log.info("[" + getSelf().path().name() + "] received message from [" + getSender().path().name() + "]");
                transmitter = temp.transmitter;
                destination = temp.destination;
            }
        }
    }

}