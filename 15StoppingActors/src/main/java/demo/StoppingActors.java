package demo;


import static akka.pattern.Patterns.gracefulStop;
import akka.pattern.AskTimeoutException;
import java.util.concurrent.CompletionStage;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import demo.SimpleActor;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class StoppingActors {

    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("system");

        final ActorRef a = system.actorOf(SimpleActor.createActor(), "a");
        final ActorRef b = system.actorOf(SimpleActor.createActor(), "b");
        final ActorRef c = system.actorOf(SimpleActor.createActor(), "c");
        final ActorRef d = system.actorOf(SimpleActor.createActor(), "d");


        system.stop(a);
        a.tell("test2", ActorRef.noSender());

        b.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
        b.tell("test2", ActorRef.noSender());

        c.tell(akka.actor.Kill.getInstance(), ActorRef.noSender());
        c.tell("test2", ActorRef.noSender());

        try{
        CompletionStage<Boolean> stopped = gracefulStop(d, Duration.ofSeconds(1));
        stopped.toCompletableFuture().get(6, TimeUnit.SECONDS);
        } catch(Exception e){}
        d.tell("test2", ActorRef.noSender());


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
