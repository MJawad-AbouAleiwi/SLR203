package demo;

import akka.actor.ActorRef;
import java.util.*;

public class MyMessage {
	public final ActorRef session;

	public MyMessage(ActorRef session) {
		this.session = session;
	}
}