package demo;
import akka.actor.ActorRef;

public class MyMessage {

    public final ActorRef transmitter;
    public final ActorRef destination;
    public final String message; 

    public MyMessage(String data) {
        this.transmitter = null;
        this.destination = null;
        this.message = data;  
    }
    public MyMessage(ActorRef t, ActorRef d) {
        this.transmitter = t;
        this.destination = d;
        this.message = null;  
    }

    public MyMessage(String m, ActorRef d) {
        this.transmitter = null; 
        this.destination = d;
        this.message = m;
    }
}