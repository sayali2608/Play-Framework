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
import services.repositoryIssues.RepositoryIssuesService;
import akka.util.ByteString;

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
import controllers.RepositoryIssuesController.*;

public class RepositoryIssuesControllerTest extends WithBrowser {
	
	private WSClient ws;
    private Server server;
    private FormFactory formFactory;
    private HttpExecutionContext ec;
    private RepositoryIssuesController client;

    @Before
    public void setup() {
      server =
          Server.forRouter(
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
      ws = play.test.WSTestClient.newClient(server.httpPort());
      formFactory = new GuiceApplicationBuilder().injector().instanceOf(FormFactory.class);
      ec = new GuiceApplicationBuilder().injector().instanceOf(HttpExecutionContext.class);
      client = new RepositoryIssuesController(ws, new RepositoryIssuesService(), ec);
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
    public void repositoryIssues() throws Exception {
    	Result result = client.repositoryIssues("octocat","Hello-World")
    	        .toCompletableFuture().get(10, TimeUnit.SECONDS);
    	    	HttpEntity httpEntity = result.body();
    	        HttpEntity.Strict httpEntityStrict = (HttpEntity.Strict) httpEntity;
    	        ByteString body = httpEntityStrict.data();
    	        String stringBody = body.utf8String();
    	        //assertThat(stringBody, containsString("<li>User: assertThat("test", containsString("test"));
    }
}
