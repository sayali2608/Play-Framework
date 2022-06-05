package services.repositoryIssues;

import java.util.concurrent.CompletionStage;

import play.mvc.Http;
import play.mvc.Result;
import play.libs.ws.WSResponse;


public interface RepositoryIssuesApi {
	public CompletionStage<WSResponse> repositoryIssues(String username,String repositoryName);
}
