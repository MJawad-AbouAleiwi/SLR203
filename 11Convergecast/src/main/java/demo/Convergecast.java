package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Convergecast {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("system");
		
		final ActorRef merger = system.actorOf(MergerActor.createActor(), "merger");
		final ActorRef a = system.actorOf(SenderActor.createActor(), "a");
		final ActorRef b = system.actorOf(SenderActor.createActor(), "b");
		final ActorRef c = system.actorOf(SenderActor.createActor(), "c");
		final ActorRef d = system.actorOf(ReceiverActor.createActor(), "d");
		
	
		a.tell(merger, ActorRef.noSender());
		b.tell(merger, ActorRef.noSender());
		c.tell(merger, ActorRef.noSender());
		d.tell(merger, ActorRef.noSender());
	
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
