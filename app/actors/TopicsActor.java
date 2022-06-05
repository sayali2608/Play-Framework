package actors;

import akka.actor.AbstractActor;
import akka.actor.Props;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import actors.Messages.TopicsKeyword;
import akka.actor.*;
import akka.japi.*;
import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import model.SearchModel;
import services.topics.TopicsService;


public class TopicsActor extends AbstractActor {

    private TopicsService TopicsService;

    TopicsActor(){

    }

    public static Props getProps() {
        return Props.create(TopicsActor.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Messages.TopicsKeyword.class, keyword -> {
                    getTopics(keyword);
                })
                .build();
    }

    public void getTopics(Messages.TopicsKeyword topicsKeyword) {
        SearchModel userProfile = (SearchModel) TopicsService.getTopicsService(topicsKeyword.keyword);
        sender().tell(userProfile, self());
    }
}
