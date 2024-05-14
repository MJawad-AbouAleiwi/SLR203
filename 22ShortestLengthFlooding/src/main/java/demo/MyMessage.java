package demo;

import akka.actor.ActorRef;
import java.util.*;

public class MyMessage {
	public final String message;
    public int sequenceNumber;
    public int length;

	public MyMessage(String message, int sequenceNumber, int length) {
		this.message = message;
        this.sequenceNumber = sequenceNumber;
        this.length = length;
	}
}