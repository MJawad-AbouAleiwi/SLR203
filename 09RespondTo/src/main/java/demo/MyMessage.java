package demo;

import akka.actor.ActorRef;

public class MyMessage {
	
    public final ActorRef ref1;
    public final ActorRef ref2;
    public final String data;

    public MyMessage(String data) {
    	this.data = data;
        ref1 = null;
        ref2 = null;
    }
    public MyMessage(ActorRef ref1, ActorRef ref2)
    {
        this.ref1 = ref1;
        this.ref2 = ref2;
        data = null;
    }
    public MyMessage(String data, ActorRef ref1) {
        this.ref1 = ref1;
        ref2 = null;
        this.data = data;
    }
  }
