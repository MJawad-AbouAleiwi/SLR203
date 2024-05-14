package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Multicast {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("system");
		
		final ActorRef multicaster = system.actorOf(Multicaster.createActor(), "multicaster");
		final ActorRef sender = system.actorOf(SenderActor.createActor(), "sender");
		final ActorRef r1 = system.actorOf(ReceiverActor.createActor(), "r1");
		final ActorRef r2 = system.actorOf(ReceiverActor.createActor(), "r2");
		final ActorRef r3 = system.actorOf(ReceiverActor.createActor(), "r3");

		MessageReferences m = new MessageReferences(multicaster, r1, r2, r3);
		sender.tell(m, ActorRef.noSender());
		

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
