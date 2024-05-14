package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ActorSelection;
import akka.actor.ActorIdentity;
import akka.actor.Identify;

public class CommunicationTopologyCreation {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("system");
		int[][] adjacency_matrix = {{0, 1, 1, 0},
									{0, 0, 0, 1},
									{1, 0, 0, 1},
									{1, 0, 0, 1}};

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
