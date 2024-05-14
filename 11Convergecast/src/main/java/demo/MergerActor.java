package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MergerActor extends UntypedAbstractActor{

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	private ArrayList<ActorRef> listOfSenders = new ArrayList<ActorRef>();
	private ActorRef receiver;
	private ArrayList<String> listOfMessages = new ArrayList<String>();

	public MergerActor() {}

	public static Props createActor() {
		return Props.create(MergerActor.class, () -> {
			return new MergerActor();
		});
	}

	private boolean checkSendingStatus(){
		boolean flag = true;
		String first = listOfMessages.get(0);
		for(String message : listOfMessages){
			if (message != first) flag = false;
		}
		return flag;
	}

	private void resetListOfMessages(){
		for(int i=0; i<listOfMessages.size(); i++){
			listOfMessages.set(i, "");
		}
	}

	public void unjoin(ActorRef actor){
		if (listOfSenders.contains(actor)){
			int index = listOfSenders.indexOf(actor);
			listOfMessages.remove(index);
			listOfSenders.remove(actor);
		}
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof String){
			log.info("["+getSelf().path().name()+"] received message '"+((String)message)+"' from ["+ getSender().path().name() +"]");

			if (((String)message)=="join" && getSender().path().name()!="d") {
				listOfSenders.add(getSender());
				listOfMessages.add("");
			}

			else if (((String)message)=="unjoin" && getSender().path().name()!="d") {
				unjoin(getSender());
			}

			else if (getSender().path().name()=="d"){
				receiver = getSender();
			}

			else if (getSender().path().name()!="d"){
				int index = listOfSenders.indexOf(getSender());
				if (index!=-1){
					listOfMessages.set(index, (String)message);

						if (checkSendingStatus()){
							receiver.tell(message, this.getSelf());
							resetListOfMessages();
						}
				}
			}
		}
			
	}

}
