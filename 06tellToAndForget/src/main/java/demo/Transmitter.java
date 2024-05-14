package demo;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Transmitter extends UntypedAbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private ActorRef source;
    private ActorRef destination;
    private String message;

    public Transmitter() {
    }
    
    public static Props createActor() {
        return Props.create(Transmitter.class, () -> new Transmitter());
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof MyMessage) 
        {
            MyMessage temp = (MyMessage) message;
            log.info("[" + getSelf().path().name() + "] received message from [" + getSender().path().name() + "] with data: [" + temp.message + "]");
            source = getSender();
            destination = temp.destination;
            this.message = temp.message;
            MyMessage r = new MyMessage(this.message);
            destination.tell(r, source);
        }
        
    }
}