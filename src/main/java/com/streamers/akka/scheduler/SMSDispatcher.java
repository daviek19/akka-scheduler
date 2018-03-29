package com.streamers.akka.scheduler;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.streamers.akka.scheduler.actors.DispatchSupervisor;
import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.FiniteDuration;

public class SMSDispatcher {

    public static void main(String args[]) {
        ActorSystem actorSystem = ActorSystem.create("SMSDispatcherApp");
        akka.actor.Scheduler scheduler = actorSystem.scheduler();
        long initialDelay = 0;
        long interval = 9000;//in seconds
        FiniteDuration initial = new FiniteDuration(initialDelay, TimeUnit.MILLISECONDS);
        FiniteDuration gap = new FiniteDuration(interval, TimeUnit.MILLISECONDS);

        String message = "fetch";

        ActorRef actor = actorSystem.actorOf(DispatchSupervisor.props(), "DispatchSupervisorActor");
        scheduler.schedule(initial, gap, actor, message, actorSystem.dispatcher(), null);
    }
}
