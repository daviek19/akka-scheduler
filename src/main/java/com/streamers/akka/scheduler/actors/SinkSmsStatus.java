
package com.streamers.akka.scheduler.actors;

import akka.actor.AbstractActor;
import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;


public class SinkSmsStatus extends AbstractLoggingActor {
    
  static public Props props() {
        return Props.create(SinkSmsStatus.class, () -> new SinkSmsStatus());
    }
  
    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, (x) -> {                   
                    log().info("sinking sms " + x.toString());
                }).build();
    }
    
}
