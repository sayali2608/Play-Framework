package services.repositoryIssues;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.SearchModel;
import services.repositoryIssues.*;

import play.libs.ws.WSResponse;

public class RepositoryIssuesService {

    @Inject
    private RepositoryIssuesApi repositoryIssues;

    private ObjectMapper mapper;

    public RepositoryIssuesService(){
        mapper = new ObjectMapper();
    }

    public CompletionStage<JsonNode> getRepositoryIssuesService(String username, String repositoryName){
        return repositoryIssues.repositoryIssues(username, repositoryName)
                .thenApplyAsync(WSResponse::asJson);
            //    .thenApplyAsync(this::parseObject);
    }

    public SearchModel parseObject(JsonNode result) {

        try {
            return mapper.readValue(result.toString(), SearchModel.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
