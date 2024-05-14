package demo;

import akka.actor.ActorRef;

public class MyMessage {
	
    public final ActorRef ref;
    public final String message;

    public MyMessage(String m) {
    	message = m;
      ref = null;
    }

    public MyMessage(ActorRef r) {
    	ref = r;
      message = null;
    }
  }