package services;

import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.CompletionStage;
import model.SearchModel;
import play.libs.ws.WSResponse;

/**
 * 
 * @author Sayali Kulkarni
 *
 */
public class HomeControllerService {
	
	@Inject
	private GithubApi homeControllerImplementation;
	
	private ObjectMapper mapper;
	
	public HomeControllerService(){
		mapper = new ObjectMapper();
	}
	
	public CompletionStage<SearchModel> searchService(String keyword){
		return homeControllerImplementation.getSearch(keyword)
				.thenApplyAsync(WSResponse::asJson)
	            .thenApplyAsync(this::parseObject);
	}
	
	public SearchModel parseObject(JsonNode result) {
		try {
            return mapper.treeToValue(result,
            		SearchModel.class);
        } catch (JsonProcessingException e) {
            return null;
        }
	}
}
