package controllers;

import actors.Messages;
import actors.SearchResultsActor;
import actors.UserActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import akka.testkit.TestActorRef;
import akka.testkit.javadsl.TestKit;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import play.inject.Injector;
import play.inject.guice.GuiceInjectorBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static play.inject.Bindings.bind;
import services.GithubApi;
import services.HomeControllerImplementation;
import services.HomeControllerService;

public class UserActorTest {
	private static ActorSystem system;

    private static Injector testApp;
    private static UserActor userActor;

    /**
     * Setup the actor system
     */
    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
        final Props props = Props.create(UserActor.class);
        final TestActorRef<UserActor> subject = TestActorRef.create(system, props, "testA");
        userActor = subject.underlyingActor();
    }

    /**
     * Shutdown the actor system
     */
    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }
    
    /**
     * Test for the UserActor
     */
    @Test
    public void testUserActor() {
        new TestKit(system) {{
            testApp = new GuiceInjectorBuilder()
                    .overrides(bind(GithubApi.class).to(HomeControllerImplementation.class))
                    .build();
            HomeControllerService homeService = testApp.instanceOf(HomeControllerService.class);
            Materializer mat = ActorMaterializer.create(system);

            final Props props = Props.create(UserActor.class);
            final TestActorRef<UserActor> subject = TestActorRef.create(system, props, "testC");
            final UserActor userActor = subject.underlyingActor();
            userActor.setMat(mat);

            final Props propsSra = Props.create(SearchResultsActor.class);
            final TestActorRef<SearchResultsActor> subjectSra = TestActorRef.create(system, propsSra, "testB");
            final SearchResultsActor searchResultsActorSync = subjectSra.underlyingActor();
            searchResultsActorSync.setTwitterService(homeService);

            Map<String, ActorRef> searchResultsActorsMap = new HashMap<>();
            searchResultsActorsMap.put("concordia", subjectSra);
            userActor.setSearchResultsActors(searchResultsActorsMap);
            userActor.createSink();

            subject.tell(new Messages.WatchSearchResults("concordia"), getRef()); // test registration
            // await the correct response
            expectMsgClass(duration("3 seconds"), Flow.class);

            subject.tell(new Messages.Repositories(new HashSet<>(), "concordia"), getRef()); // test registration
            // await the correct response
            expectMsgClass(duration("3 seconds"), Flow.class);

            subject.tell(new Messages.UnwatchSearchResults("concordia"), getRef());
        }};
    }
    
    /**
     * Setter test for SearchResultsActor
     */
    @Test
    public void testSetSearchResultsActor() {
        userActor.setSearchResultsActors(null);
        Assert.assertNull(userActor.getSearchResultsActors());
    }
    
    /**
     * Setter test for Materializer
     */
    @Test
    public void testSetMaterializer() {
        userActor.setMat(null);
        Assert.assertNull(userActor.getMat());
    }
    
    /**
     * Setter test for the SearchResultsMap
     */
    @Test
    public void testSetSearchResultsMap() {
        userActor.setSearchResultsMap(null);
        Assert.assertNull(userActor.getSearchResultsMap());
    }
    
    /**
     * Setter test for the JsonSink
     */
    @Test
    public void testSetJsonSink() {
        userActor.setJsonSink(null);
        Assert.assertNull(userActor.getJsonSink());
    }
}
