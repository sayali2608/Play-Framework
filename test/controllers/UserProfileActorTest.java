package controllers;

import static org.junit.Assert.*;
import static play.inject.Bindings.bind;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import actors.Messages;
import actors.UserActor;
import actors.UserProfileActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import play.inject.Injector;
import play.inject.guice.GuiceInjectorBuilder;
import services.GithubApi;
import services.HomeControllerImplementation;
import services.HomeControllerService;

public class UserProfileActorTest {

	private static ActorSystem system;

    private static Injector testApp;
    private static UserProfileActor userActor;

    /**
     * Setup the actor system
     */
    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
        final Props props = Props.create(UserProfileActor.class);
        final TestActorRef<UserProfileActor> subject = TestActorRef.create(system, props, "testA");
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
                    .overrides(bind(UserProfileApi.class).to(UserProfileImplementation.class))
                    .build();
            UserProfileService userProfileService = testApp.instanceOf(UserProfileService.class);
            
            final Props props = Props.create(UserActor.class);
            final TestActorRef<UserActor> subject = TestActorRef.create(system, props, "testC");
            final UserActor userActor = subject.underlyingActor();
            userActor.setUserProfileService(userProfileService);
            subject.tell(new Messages.UserProfile("Sayali2608"), getRef()); // test registration
            
        }};
    }
}
