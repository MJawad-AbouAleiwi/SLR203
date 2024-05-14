package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Scheduler;
import akka.actor.ActorContext; 
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;
import java.util.concurrent.TimeUnit; 

public class ShortestLengthFlooding {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("system");
		int[][] adjacency_matrix = {{0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
									{1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, 
									{1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
									{1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, 
									{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, 
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, 
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, 
									{0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1}, 
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, 
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, 
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0}, 
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, 
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, 
									{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, 
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}};

		ActorRef a[] = new ActorRef[adjacency_matrix.length];
		for(int i = 0; i < adjacency_matrix.length; i++){
			a[i] = system.actorOf(FirstActor.createActor(), "actor" + (i + 1));
		}

		for(int i = 0; i < adjacency_matrix.length; i++){
			for(int j = 0; j < adjacency_matrix[i].length; j++){
				if( adjacency_matrix[i][j] == 1){
					a[i].tell(a[j], ActorRef.noSender());
				}
			}
		}
		MyMessage initialMessage = new MyMessage("m", 0, 1);
		
		//A starts the algorithm, P received 3 messages and the lowest length is 4//
        a[0].tell(initialMessage, ActorRef.noSender());

		
		//L starts the algorithm, P received 1 message and the lowest length is 5  
		
        //a[11].tell(initialMessage, ActorRef.noSender());
	
		
		//I starts the algorithm, Q received 1 message and the lowest length is 6  
		
        //a[8].tell(initialMessage, ActorRef.noSender());

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
