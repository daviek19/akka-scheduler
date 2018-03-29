package com.streamers.akka.scheduler.actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

public class DispatchSupervisor extends AbstractLoggingActor {

    private final ActorRef fetchSmsActor = getContext()
            .actorOf(FetchSmsActor.props());

    static public Props props() {
        return Props.create(DispatchSupervisor.class,
                () -> new DispatchSupervisor()
        );
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(
                any -> fetchSmsActor.forward(any, getContext()))
                .build();
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(
                10,
                Duration.create("10 seconds"),
                DeciderBuilder.match(
                        RuntimeException.class,
                        ex -> SupervisorStrategy.resume())
                        .build()
        );
    }

}
