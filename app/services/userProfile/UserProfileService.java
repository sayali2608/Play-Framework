package services.userProfile;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Repositories;
import model.UserProfileModel;
import services.userProfile.*;

import play.libs.ws.WSResponse;
public class UserProfileService {
	
	@Inject
	private UserProfileApi userProfile;
	
	private ObjectMapper mapper;
	
	public UserProfileService(){
		mapper = new ObjectMapper();
	}
	
	public CompletionStage<UserProfileModel> getUserProfileService(String keyword){
		return userProfile.getUserProfile(keyword)
				.thenApplyAsync(WSResponse::asJson)
	            .thenApplyAsync(this::parseObject);
	}
	
	public CompletionStage<List<Repositories>> getRepos(String keyword){
		return userProfile.getUserRepos(keyword)
				.thenApplyAsync(WSResponse::asJson)
				.thenApplyAsync(this::parseRepos);
	}
	
	public UserProfileModel parseObject(JsonNode result) {
		
		try {
			return mapper.readValue(result.toString(), UserProfileModel.class);
        } catch (JsonProcessingException e) {
            return null;
        }
	}
	
	public List<Repositories> parseRepos(JsonNode result) {
		try {
			return Arrays.asList(mapper.treeToValue(result,
					Repositories[].class));
		} catch (JsonProcessingException e) {
			return null;
		}
	}
}
