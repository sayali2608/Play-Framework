package controllers;

import java.io.File;
import play.inject.Injector;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import play.inject.guice.GuiceInjectorBuilder;
import play.libs.ws.WSResponse;
import org.junit.*;
import static play.inject.Bindings.bind;

import services.topics.TopicsApi;
import services.topics.TopicsImplementation;

public class TopicsImplementationTest {
    private static TopicsApi TestImplementation;

    private static Injector testApp;

    /**
     * Initialise the test application, bind the TwitterAPI interface to its mock implementation
     */
    @BeforeClass
    public static void initTestApp() {
        testApp = new GuiceInjectorBuilder()
                .overrides(bind(TopicsApi.class).to(TopicsImplementation.class))
                .build();
        TestImplementation = testApp.instanceOf(TopicsApi.class);
    }

    /**
     * Test the search for a keyword comparing the static jsonfile we expect and what is returned
     * by the mock implementation
     * @throws ExecutionException if an error occurs during execution
     * @throws InterruptedException if the request is interrupted
     * @throws IOException if we have trouble reading the static file
     */
    @Test
    public void testSearch() throws ExecutionException, InterruptedException, IOException {
        TestImplementation.ws = play.test.WSTestClient.newClient(server.httpPort());
        WSResponse search = TestImplementation.getTopics("test").toCompletableFuture().get();
        String body = search.getBody();

        //Assert.assertEquals("test", body);
    }

}
