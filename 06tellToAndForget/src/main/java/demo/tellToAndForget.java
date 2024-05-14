package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class tellToAndForget {

	public static void main(String[] args) {
		
		final ActorSystem system = ActorSystem.create("system");

		final ActorRef a = system.actorOf(FirstActor.createActor(), "a");
		final ActorRef b = system.actorOf(SecondActor.createActor(), "b");
		final ActorRef t = system.actorOf(Transmitter.createActor(), "trasmitter");

		MyMessage m = new MyMessage(t,b);
		MyMessage start = new MyMessage("start");
		//send the references of b and transmitter to a
	    a.tell(m, ActorRef.noSender());
		//inform a to start
	    a.tell(start, ActorRef.noSender());
	    
	    try {
			waitBeforeTerminate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			system.terminate();
		}
	}
	
	public static void waitBeforeTerminate() throws InterruptedException {
		Thread.sleep(5000);
	}
}
