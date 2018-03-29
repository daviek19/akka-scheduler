package com.streamers.akka.scheduler.actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.dispatch.MessageDispatcher;

public class SendSmsActor extends AbstractLoggingActor {

    private final ActorRef sinkSmsActor = getContext().actorOf(SinkSmsStatus.props());

    static public Props props() {
        return Props.create(SendSmsActor.class,
                () -> new SendSmsActor()
        ).withDispatcher("blocking-io-dispatcher");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, (x) -> {
                    //sleep for 2 senconds to wait fot response from the 
                    //call from Africas Talking 
                    Thread.sleep(5000);
                    sinkSmsActor.tell(x, getSelf());
                    log().info("sending message " + x.toString());
                }).build();

    }

}
