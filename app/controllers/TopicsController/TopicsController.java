package controllers.TopicsController;

import java.util.concurrent.CompletionStage;

import actors.Messages;
import actors.TopicsActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import model.SearchModel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.stream.Collectors;

import play.libs.concurrent.HttpExecutionContext;
import javax.inject.Inject;
import play.libs.ws.WSClient;
import play.mvc.Controller;
import play.mvc.Result;
import model.Repositories;
import scala.compat.java8.FutureConverters;
import views.html.TopicsView.TopicsView;
import services.topics.*;
import static akka.pattern.Patterns.ask;

/**
 * This controller contains an action to handle HTTP requests
 * to the user profile page.
 */
public class TopicsController extends Controller {

    TopicsService Topics;
    List<Repositories> repos = new ArrayList<Repositories>();

    private HttpExecutionContext httpExecutionContext;
    final ActorRef topicsActor;
    @Inject
    public TopicsController(TopicsService Topics, ActorSystem system, HttpExecutionContext httpExecutionContext){
        this.Topics = Topics;
        this.topicsActor = system.actorOf(TopicsActor.getProps());
        this.httpExecutionContext = httpExecutionContext;
    }
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */

    public CompletionStage<Result> getSearchResult(String keyword){

        return FutureConverters.toJava(
                ask(topicsActor, new Messages.TopicsKeyword(keyword), 1000))
                .thenApplyAsync(result -> {
                        SearchModel searchResult = (SearchModel) result;
                        List<Repositories> repos = searchResult.getItems().stream().limit(10).collect(Collectors.toList());
                        return ok(TopicsView.render(repos));
                }, httpExecutionContext.current());

    }
}
