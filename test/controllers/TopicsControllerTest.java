package controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.Before;
import org.junit.Test;

import play.data.FormFactory;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Result;
import play.test.WithApplication;
import play.test.WithBrowser;
import services.topics.TopicsService;
import akka.util.ByteString;
import controllers.TopicsController.TopicsController;
import java.util.concurrent.TimeUnit;
import static play.mvc.Results.ok;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;

import org.junit.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.http.HttpEntity;
import play.libs.ws.WSClient;
import play.routing.RoutingDsl;
import play.server.Server;
import java.io.IOException;

public class TopicsControllerTest extends WithBrowser {

    private WSClient ws;
    private Server server;
    private FormFactory formFactory;
    private HttpExecutionContext ec;
    private TopicsController client;

    @Before
    public void setup() {
        server =
                Server.forRouter(
                        (components) ->
                                RoutingDsl.fromComponents(components)
                                        .GET("/repositories?q=topic:")
                                        .routingTo(
                                                request -> {
                                                    ArrayNode repos = Json.newArray();
                                                    ObjectNode repo = Json.newObject();
                                                    repo.put("full_name", "/Hello-World");
                                                    repos.add(repo);
                                                    return ok(repos);
                                                })
                                        .build());
        ws = play.test.WSTestClient.newClient(server.httpPort());
        formFactory = new GuiceApplicationBuilder().injector().instanceOf(FormFactory.class);
        ec = new GuiceApplicationBuilder().injector().instanceOf(HttpExecutionContext.class);
        client = new TopicsController(new TopicsService(), ec);
    }

    @After
    public void tearDown() throws IOException {
        try {
            ws.close();
        } finally {
            server.stop();
        }
    }

    @Test
    public void Topics() throws Exception {
    	Result result = client.getSearchResult("Hello-World")
                .toCompletableFuture().get(10, TimeUnit.SECONDS);
        HttpEntity httpEntity = result.body();
        HttpEntity.Strict httpEntityStrict = (HttpEntity.Strict) httpEntity;
        ByteString body = httpEntityStrict.data();
        String stringBody = body.utf8String();
        System.out.println(stringBody);
        //assertThat(stringBody, containsString("<li><a href=\"#\" style=\"color: lightblue\">"
       }
}