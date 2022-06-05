package services.topics;

import java.util.concurrent.CompletionStage;

import play.mvc.Http;
import play.mvc.Result;
import play.libs.ws.WSResponse;


public interface TopicsApi {
    public CompletionStage<WSResponse> getTopics(String keyword);
}
