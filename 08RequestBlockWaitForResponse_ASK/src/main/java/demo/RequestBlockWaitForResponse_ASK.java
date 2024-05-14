package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class RequestBlockWaitForResponse_ASK {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("system");
		final ActorRef b = system.actorOf(SecondActor.createActor(), "b");
		
		@SuppressWarnings("unused")
		final ActorRef a = system.actorOf(FirstActor.createActor(b), "a");
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