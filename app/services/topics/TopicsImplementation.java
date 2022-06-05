package services.topics;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import play.libs.ws.WSClient;
import model.SearchModel;
import play.libs.ws.WSResponse;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Http;
import play.mvc.Http.Request;
import play.mvc.Result;

public class TopicsImplementation implements TopicsApi{

    private final WSClient ws;

    @Inject
    TopicsImplementation(WSClient ws){
        this.ws = ws;
    }

    @Override
    public CompletionStage<WSResponse> getTopics(String keyword){
        System.out.print("---------------------------" + keyword + "----------------------");
        return ws.url("https://api.github.com/search/repositories?q=topic:" + keyword)
                .get(); // THIS IS NOT BLOCKING! It returns a promise to the response. It comes from WSRequest.

    }
}
