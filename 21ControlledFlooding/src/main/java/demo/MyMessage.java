package demo;

import akka.actor.ActorRef;
import java.util.*;

public class MyMessage {
	public final String message;
    public final int sequenceNumber;

	public MyMessage(String message, int sequenceNumber) {
		this.message = message;
        this.sequenceNumber = sequenceNumber;
	}
}