package controllers;

import static org.junit.Assert.*;

import org.junit.Test;

import actors.Messages;
import actors.SearchActor;
import services.GithubApi;
import services.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import play.inject.Injector;
import play.inject.guice.GuiceInjectorBuilder;
import static play.inject.Bindings.bind;

import akka.actor.ActorSystem;
import akka.actor.Props;

import akka.testkit.javadsl.TestKit;
import akka.testkit.TestActorRef;


public class SearchActorTest {
	static ActorSystem system;

    private static Injector testApp;
    
    /**
     * Setup the tests
     */
    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }
    
    /**
     * Shut down system
     */
    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }
    
    /**
     * Test for the SearchResultsActor
     */
    @Test
    public void testSearchResultsActor() {
        new TestKit(system) {{
            testApp = new GuiceInjectorBuilder()
                    .overrides(bind(GithubApi.class).to(HomeControllerImplementation.class))
                    .build();
            HomeControllerService github = testApp.instanceOf(HomeControllerService.class);

            final Props props = Props.create(SearchActor.class);
            final TestActorRef<SearchActor> subject = TestActorRef.create(system, props, "testB");
            final SearchActor searchResultsActorSync = subject.underlyingActor();
            searchResultsActorSync.setHomeControllerService(github);

            subject.tell(new Messages.RegisterActor(), getRef()); // test registration
            // await the correct response
            expectMsg(duration("1 seconds"), "UserActor registered");

            // the run() method needs to finish within 3 seconds
            within(duration("3 seconds"), () -> {
                subject.tell(new Messages.WatchSearchResults("concordia"), getRef());

                // response must have been enqueued to us before probe
                expectMsgClass(duration("3 seconds"), Messages.RepositoriesMessage.class);
                return null;
            });

            // the run() method needs to finish within 3 seconds
            within(duration("3 seconds"), () -> {
                subject.tell(new SearchActor.Tick(), getRef());

                // response must have been enqueued to us before probe
                expectMsgClass(duration("3 seconds"), Messages.RepositoriesMessage.class);
                return null;
            });
        }};
    }

}
