package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import java.util.*;
import demo.MyMessage;

public class FirstActor extends UntypedAbstractActor{

	
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    ArrayList<ActorRef> actorList;
    private List<MyMessage> receivedMessages=null;

    public int messagesReceivedCount = 0;
    public int lowestLengthReceived = Integer.MAX_VALUE;

	public FirstActor() {
        this.actorList = new ArrayList<ActorRef>();
        this.receivedMessages = new ArrayList<MyMessage>();
    }

	public static Props createActor() {
		return Props.create(FirstActor.class, () -> {
			return new FirstActor();
		});
	} 

    @Override
	public void onReceive(Object message) throws Throwable {
        if(message instanceof ActorRef){
                actorList.add(((ActorRef)message));
                log.info("["+getSelf().path().name()+"] has the reference to ["+ ((ActorRef)message).path().name() +"]");
            }
        if(message instanceof MyMessage){
            MyMessage msg = (MyMessage) message;
            messagesReceivedCount=messagesReceivedCount+1;
            if (!isSeqNumberInList(msg.sequenceNumber)){
                receivedMessages.add(msg);
                MyMessage send = new MyMessage(msg.message, msg.sequenceNumber, msg.length + 1);
                lowestLengthReceived = Math.min(lowestLengthReceived, msg.length);
                Iterator<ActorRef> i = actorList.iterator();
                while(i.hasNext()){
                    ActorRef element = i.next();
                    element.tell(send, getSelf());
                }
                log.info("["+getSelf().path().name()+"] received a message "+ msg.message+ " from ["+ getSender().path().name() +"]");
                log.info( "[" + getSelf().path().name() + "] MessagesReceived = " + messagesReceivedCount
                + ", LowestLength= " + lowestLengthReceived);
            }
            // if message already received, but smaller length
            else if (isSeqNumberInList(msg.sequenceNumber) & msg.length < getMessageLength(msg.sequenceNumber) ){

                MyMessage send = new MyMessage(msg.message, msg.sequenceNumber, msg.length + 1);
                //replace the old message with the new one
                for (int i = 0; i < receivedMessages.size(); i++) {
                MyMessage existingMessage = receivedMessages.get(i);
                if (existingMessage.sequenceNumber == msg.sequenceNumber) 
                {
                    receivedMessages.set(i, send);
                }

                lowestLengthReceived = Math.min(lowestLengthReceived, msg.length);
                Iterator<ActorRef> it = actorList.iterator();
                while(it.hasNext()){
                    ActorRef element = it.next();
                    element.tell(send, getSelf());
                }
                log.info("["+getSelf().path().name()+"] received a message "+ msg.message + " from ["+ getSender().path().name() +"]");
                log.info( "[" + getSelf().path().name() + "] MessagesReceived = " + messagesReceivedCount
                + ", LowestLength= " + lowestLengthReceived);
            }
        }
    }
}
    private boolean isSeqNumberInList(int seqNumber) {
        for (MyMessage message : receivedMessages) {
            if (message.sequenceNumber == seqNumber) {
                return true;
            }
        }
        return false;
    }
    private int getMessageLength(int seqNumber) {
        for (MyMessage message : receivedMessages) {
            if (message.sequenceNumber == seqNumber) {
                return message.length;
            }
        }
        return -1;
    }
}