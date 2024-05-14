package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;


public class RespondTo {

	public static void main(String[] args) {
		
		final ActorSystem system = ActorSystem.create("system");
		
	    final ActorRef a = system.actorOf(FirstActor.createActor(), "a");
		final ActorRef b = system.actorOf(SecondActor.createActor(), "b");
		final ActorRef c = system.actorOf(ThirdActor.createActor(), "c");
	    MyMessage m = new MyMessage(b,c);
        
		a.tell(m, ActorRef.noSender());  
	
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
