package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;


public class BroadcastRoundRobin {

	public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("system");

        final ActorRef a = system.actorOf(FirstActor.createActor(), "a");
        final ActorRef b = system.actorOf(SecondActor.createActor(), "b");
        final ActorRef c = system.actorOf(ThirdActor.createActor(), "c");
        final ActorRef broadcaster = system.actorOf(Broadcaster.createActor(), "broadcaster");

        a.tell(broadcaster, ActorRef.noSender());

        broadcaster.tell("join", b );
        broadcaster.tell("join", c);
        
        try {
            waitBeforeTerminate();
        } catch (Exception e) {

        } finally {
            system.terminate();
        }
    }

    public static void waitBeforeTerminate() throws InterruptedException {
        Thread.sleep(5000);
    }
}