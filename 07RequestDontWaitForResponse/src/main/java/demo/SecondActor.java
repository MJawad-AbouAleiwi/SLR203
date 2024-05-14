package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SecondActor extends UntypedAbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public SecondActor() {
	}

	public static Props createActor() {
		return Props.create(SecondActor.class, () -> {
			return new SecondActor();
		});
	}

	@Override
	public void onReceive(Object message) {
		if (message instanceof MyMessage) {
			MyMessage temp = (MyMessage) message;
			log.info("[" + getSelf().path().name() + "] received a request from [" + getSender().path().name()
				+ "] with data: [" + temp.message + "]");
			String request = temp.message;
			String response="";
			int lastDigit = Integer.parseInt(request.substring(request.length() - 1));
			if (lastDigit >= 1 && lastDigit <= 9)
				response = "Response " + lastDigit;
			 else 
				response = "Response " + request.substring(request.length() - 2);
				//response = "Response " + request.substring(request.length() - 1);
			MyMessage r = new MyMessage(response);
			getSender().tell(r, this.getSelf());
		}
	}

}