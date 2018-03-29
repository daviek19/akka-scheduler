package com.streamers.akka.scheduler.actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.dispatch.Futures;
import akka.pattern.Patterns;
import java.util.concurrent.Callable;

public class SendSmsActor extends AbstractLoggingActor {

    private final ActorRef sinkSmsActor = getContext().actorOf(SinkSmsStatus.props());

    static public Props props() {
        return Props.create(SendSmsActor.class,
                () -> new SendSmsActor()
        ).withDispatcher("my-dispatcher");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, (x) -> {
                    sendSms(x);
                    log().info("sending message " + x.toString());
                }).build();

    }

    private void sendSms(String message) {
        Patterns.pipe(Futures.future(new Callable<String>() {
            public String call() throws InterruptedException {
                //call africas talking
                Thread.sleep(5000);
                return message;
            }
        }, getContext().dispatcher()), getContext().dispatcher()).to(sinkSmsActor);
    }

}
