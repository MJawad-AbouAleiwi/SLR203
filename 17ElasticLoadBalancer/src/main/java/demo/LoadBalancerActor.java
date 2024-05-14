package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Pair;

public class LoadBalancerActor extends UntypedAbstractActor{

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	private int numberBalancer;
	private ArrayList<Pair<ActorRef, ArrayList<String>>> listOfPairsActorTask = new ArrayList<Pair<ActorRef, ArrayList<String>>>();
	private int count = 0;
	private int newReceiverIncr = 0;

	public LoadBalancerActor() {}

	public static Props createActor() {
		return Props.create(LoadBalancerActor.class, () -> {
			return new LoadBalancerActor();
		});
	}

	private ActorRef chooseTheReceiver(){
		int nbReceivers = numberBalancer;
		if (count>=nbReceivers){
			count=0;
		} 
		ActorRef receiver = listOfPairsActorTask.get(count).first();
		count++;
		return receiver;
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof Integer){
			numberBalancer = (Integer)message;
		}

		else if(message instanceof MessageString){
			log.info("["+getSelf().path().name()+"] received message '"+((MessageString)message).data +"' from ["+ getSender().path().name() +"]");

			if (getSender().path().name()=="a"){

				if (listOfPairsActorTask.size()<numberBalancer){
					int index = listOfPairsActorTask.size();
					ActorRef currentReceiver = getContext().actorOf(ReceiverActor.createActor(), "receiver"+((Integer)newReceiverIncr).toString());
					newReceiverIncr++;
					listOfPairsActorTask.add(null);
					listOfPairsActorTask.set(index, new Pair<ActorRef, ArrayList<String>>(currentReceiver, new ArrayList<String>()));
					listOfPairsActorTask.get(index).second().add(((MessageString)message).data);

					currentReceiver.tell(message, getSelf());

				} else {
					ActorRef choice = chooseTheReceiver();
					for(Pair<ActorRef, ArrayList<String>> pair : listOfPairsActorTask){
						if (pair.first().equals(choice)){
							pair.second().add(((MessageString)message).data);
						}
						choice.tell(message, getSelf());
						break;
						
					}

				}

			}
			
		}
		
		else if (message instanceof MessageFinished){
			String task = ((MessageFinished)message).task;
			log.info("["+getSelf().path().name()+"] received '"+task +"' finished from ["+ getSender().path().name() +"]");
			
			for(Pair<ActorRef, ArrayList<String>> pair : listOfPairsActorTask){
				if (pair.first().equals(getSender())){
					pair.second().remove(task);
					if (pair.second().size()==0){
						listOfPairsActorTask.remove(pair);
						this.getContext().stop(getSender());
					}
				}
				break;
				
			}
		}
			
	}

}
