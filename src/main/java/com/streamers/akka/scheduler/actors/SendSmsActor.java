package com.streamers.akka.scheduler.actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class SendSmsActor extends AbstractLoggingActor {

    private final ActorRef sinkSmsActor = getContext().actorOf(SinkSmsStatus.props());

    static public Props props() {
        return Props.create(SendSmsActor.class, () -> new SendSmsActor());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, (x) -> {
                    log().info("sending message " + x.toString());
                    //sleep for 2 senconds to wait fot response from the 
                    //call from Africas Talking 
                    Thread.sleep(2000);
                    sinkSmsActor.tell(x, getSelf());
                }).build();
    }

}
