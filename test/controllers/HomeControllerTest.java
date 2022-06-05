package controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;

import akka.util.ByteString;
import controllers.HomeController;
import models.*;
import org.junit.*;
import org.junit.runners.MethodSorters;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.data.FormFactory;
import play.http.HttpEntity;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.concurrent.HttpExecutionContext;
import play.libs.oauth.OAuth;
import play.libs.ws.WSClient;
import play.mvc.Http;
import play.mvc.Result;
import play.routing.RoutingDsl;
import play.server.Server;
import play.test.WithBrowser;

import play.twirl.api.Content;
import services.HomeControllerService;
import views.html.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static play.mvc.Results.ok;
import static play.test.Helpers.*;
import play.libs.Json;
/**
 * 
 * @author Sayali Kulkarni
 *
 */
public class HomeControllerTest extends WithApplication {
	
	private WSClient ws;
    private Server server;
    private FormFactory formFactory;
    private HttpExecutionContext ec;
    private HomeController client;

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
      client = new HomeController(new HomeControllerService(), ec, formFactory);
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
    public void repositories() throws Exception {
    	Result result = client.getSearch("javascript")
    	        .toCompletableFuture().get(10, TimeUnit.SECONDS);
    	    	HttpEntity httpEntity = result.body();
    	        HttpEntity.Strict httpEntityStrict = (HttpEntity.Strict) httpEntity;
    	        ByteString body = httpEntityStrict.data();
    	        String stringBody = body.utf8String();
    	        //assertThat(stringBody, containsString("<input type=\"text\" id=\"keyword\" name=\"keyword\">"));
    }
}
