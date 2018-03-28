/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.streamers.akka.scheduler;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.streamers.akka.scheduler.actors.FetchSmsActor;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.FiniteDuration;


public class Scheduler {

    public static void main(String args[]) {
        ActorSystem actorSystem = ActorSystem.create("SchedulerApp");
        akka.actor.Scheduler scheduler = actorSystem.scheduler();
        long initialDelay = 0;
        long interval = 5000;//in seconds
        FiniteDuration initial = new FiniteDuration(initialDelay, TimeUnit.MILLISECONDS);
        FiniteDuration gap = new FiniteDuration(interval, TimeUnit.MILLISECONDS);

        String message = "fetch";
        ActorRef actor = actorSystem.actorOf(FetchSmsActor.props(), "FetchSmsActor");
        scheduler.schedule(initial, gap, actor, message, actorSystem.dispatcher(), null);
    }
}
