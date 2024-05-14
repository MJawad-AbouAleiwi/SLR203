package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.util.*;
import demo.MyMessage;

public class SessionChildActor {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");
		
        final ActorRef sessionManager = system.actorOf(SessionManager.createActor(), "sessionManager");
        final ActorRef client = system.actorOf(Client.createActor(sessionManager), "client");
	
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
