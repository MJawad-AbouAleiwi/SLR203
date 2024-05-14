package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class ElasticLoadBalancer {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("system");
		
		final ActorRef balancer = system.actorOf(LoadBalancerActor.createActor(), "loadbalancer");
		final ActorRef a = system.actorOf(SenderActor.createActor(), "a");

		a.tell(balancer, ActorRef.noSender());
		balancer.tell(2, ActorRef.noSender());
	

	    try {
			waitBeforeTerminate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			system.terminate();
		}
	}

	public static void waitBeforeTerminate() throws InterruptedException {
		Thread.sleep(10000);
	}

}
