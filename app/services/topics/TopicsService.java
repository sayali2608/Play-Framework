package services.topics;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.SearchModel;
import services.topics.*;

import play.libs.ws.WSResponse;

public class TopicsService {

    @Inject
    private TopicsApi topics;

    private ObjectMapper mapper;

    public TopicsService(){
        mapper = new ObjectMapper();
    }

    public CompletionStage<SearchModel> getTopicsService(String keyword){
        return topics.getTopics(keyword)
                .thenApplyAsync(WSResponse::asJson)
                .thenApplyAsync(this::parseObject);
    }

    public SearchModel parseObject(JsonNode result) {

        try {
            return mapper.readValue(result.toString(), SearchModel.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
