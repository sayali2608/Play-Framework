package services.repositoryProfile;
import java.util.concurrent.CompletionStage;


import play.mvc.Http;
import play.mvc.Result;
import play.libs.ws.WSResponse;

public interface RepositoryProfileApi {
	public CompletionStage<WSResponse> getCollaborators(String organization,String reponame);
	public CompletionStage<WSResponse> getRepoIssues(String organization,String reponame);
	public CompletionStage<WSResponse> getrepositoryProfile(String organization,String reponame,String collabArray);
	

}
