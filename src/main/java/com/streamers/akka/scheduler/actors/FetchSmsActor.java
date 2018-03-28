package com.streamers.akka.scheduler.actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FetchSmsActor extends AbstractLoggingActor {

    private final ActorRef sendSmsActor = getContext().actorOf(SendSmsActor.props());

    static public Props props() {
        return Props
                .create(FetchSmsActor.class, () -> new FetchSmsActor());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(String.class, (x) -> {
            log().info("we recived the message. " + x);

            getSms().forEach((t) -> {
                sendSmsActor.tell(t, getSelf());
            });

        }).matchAny(x -> log().info("received unknown message"))
                .build();
    }

    private List<String> getSms() {
        //consider this algo when fetching sms
        //var batch = new Guid();
        //update car set sent_to_server = @batch where sent_to_server is null;    
        //select * from car where sent_to_server = @batch;
        
        List<String> smsList = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        for (int j = 0; j <= 5; j++) {
            smsList.add("The time now is " + dateFormat.format(new Date()).toString());
        }
        return smsList;
    }
}
