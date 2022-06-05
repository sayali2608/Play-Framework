package services.repositoryProfile;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;
import com.fasterxml.jackson.databind.node.ArrayNode;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Repositories;
import model.ContributionsModel;
import model.Issues;
import services.userProfile.*;

import play.libs.ws.WSResponse;
public class RepositoryProfileService {
	
	@Inject
	private RepositoryProfileApi repositoryProfile;
	
	private ObjectMapper mapper;
	
	public RepositoryProfileService(){
		mapper = new ObjectMapper();
	}
	
	
	public CompletionStage<Repositories> getRepoProfileService(String organization,String reponame,String collabArray){
		return repositoryProfile.getrepositoryProfile(organization,reponame,collabArray)
				.thenApplyAsync(WSResponse::asJson)
	            .thenApplyAsync(this::parseObject2);
	}
	
	public Repositories parseObject2(JsonNode result) {
		
		try {
			 return mapper.readValue(result.toString(),Repositories.class);
        } catch (JsonProcessingException e) {
            return null;
        }
		
	}
	
	public CompletionStage<List<ContributionsModel>> getRepositoryProfileService(String organization,String reponame){
		return repositoryProfile.getCollaborators(organization,reponame)
				.thenApplyAsync(WSResponse::asJson)
	            .thenApplyAsync(this::parseObject);
	}
	
	public List<ContributionsModel> parseObject(JsonNode result) {
		
		try {
			return Arrays.asList(mapper.treeToValue(result,
					ContributionsModel[].class));
        } catch (JsonProcessingException e) {
            return null;
        }
		
	}
	public CompletionStage<ArrayNode> getReposIssuesService(String organization,String reponame){
		return repositoryProfile.getRepoIssues(organization,reponame)
				.thenApplyAsync(WSResponse::asJson)
	            .thenApplyAsync(this::parseObject1);
	}
	
	public ArrayNode parseObject1(JsonNode result) {
		
			return (ArrayNode) result;
   }
		
	
}

