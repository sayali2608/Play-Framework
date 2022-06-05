package controllers;

import static org.junit.Assert.*;

import play.inject.Injector;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import play.inject.guice.GuiceInjectorBuilder;
import play.libs.Json;
import play.libs.ws.WSResponse;
import play.routing.RoutingDsl;
import play.server.Server;

import org.junit.*;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static play.inject.Bindings.bind;
import static play.mvc.Results.ok;

import services.userProfile.UserProfileApi;
import services.userProfile.UserProfileImplementation;


import play.libs.ws.WSClient;
import play.mvc.Http;
import play.mvc.Result;
import play.routing.RoutingDsl;
import play.server.Server;
import play.test.WithBrowser;

import play.twirl.api.Content;

/**
 * 
 * @author Sayali Kulkarni
 *
 */
public class UserProfileServiceTest {
	private static UserProfileApi userProfileImplementation;

    private static Injector testApp;

    /**
     * Initialise the test application, bind the TwitterAPI interface to its mock implementation
     */
    @BeforeClass
    public static void initTestApp() {
    	testApp = new GuiceInjectorBuilder()
                .overrides(bind(UserProfileApi.class).to(UserProfileImplementation.class))
                .build();
    	userProfileImplementation = testApp.instanceOf(UserProfileApi.class);
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
    	Server server = Server.forRouter(
                (components) ->
                RoutingDsl.fromComponents(components)
                    .GET("/repositories")
                    .routingTo(
                        request -> {
                          ArrayNode repos = Json.newArray();
                          ObjectNode repo = Json.newObject();
                          repo.put("full_name", "octocat/Hello-World");
                          repos.add(repo);
                          return ok(repos);
                        })
                    .build());
    
    	userProfileImplementation.setWs( play.test.WSTestClient.newClient(server.httpPort()));
        WSResponse search = userProfileImplementation.getUserProfile("Sayali2608").toCompletableFuture().get();
        String body = search.getBody();
        Assert.assertEquals(body, containsString("Sayali2608"));
    }

}
