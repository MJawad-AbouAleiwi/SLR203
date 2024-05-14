package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class PublishSubscribe {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("system");
		
		final ActorRef topic1 = system.actorOf(TopicBroadcastActor.createActor(), "topic1");
		final ActorRef topic2 = system.actorOf(TopicBroadcastActor.createActor(), "topic2");
		final ActorRef publisher1 = system.actorOf(PublisherActor.createActor(), "publisher1");
		final ActorRef publisher2 = system.actorOf(PublisherActor.createActor(), "publisher2");
		final ActorRef a = system.actorOf(ReceiverActor.createActor(), "a");
		final ActorRef b = system.actorOf(ReceiverActor.createActor(), "b");
		final ActorRef c = system.actorOf(ReceiverActor.createActor(), "c");
		
		publisher1.tell(topic1, ActorRef.noSender());
		publisher2.tell(topic2, ActorRef.noSender());

		MessageTwoRef topics = new MessageTwoRef(topic1, topic2);
		a.tell(topics, ActorRef.noSender());
		b.tell(topics, ActorRef.noSender());
		c.tell(topics, ActorRef.noSender());
	
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
