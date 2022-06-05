package services.repositoryProfile;
import java.util.concurrent.CompletionStage;


import javax.inject.Inject;

import play.libs.ws.WSClient;
import model.UserProfileModel;
import play.libs.ws.WSResponse;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Http;
import play.mvc.Http.Request;
import play.mvc.Result;
public class RepositoryProfileImplementation implements RepositoryProfileApi{
	
	private final WSClient ws;
	
	 /**
	  * The constructor injects the necessary dependancies for the class
	  * @param ws
	  */
	@Inject
	RepositoryProfileImplementation(WSClient ws){
		this.ws = ws;
	}
   

   /**
    * Renders the searched keyword with it's username, repositories and topics
    * @param keyword
    * @return
    */
	@Override
	public CompletionStage<WSResponse> getCollaborators(String organization,String reponame){
		return ws.url("https://api.github.com/repos/"+organization+"/"+reponame+"/"+"contributors")
                .get(); // THIS IS NOT BLOCKING! It returns a promise to the response. It comes from WSRequest.
          
	}
	@Override
	public CompletionStage<WSResponse> getRepoIssues(String organization,String reponame){
		return ws.url("https://api.github.com/repos/" + organization+"/"+reponame+"/issues")
                .get(); // THIS IS NOT BLOCKING! It returns a promise to the response. It comes from WSRequest.
          
	}


	@Override
	public CompletionStage<WSResponse> getrepositoryProfile(String organization,
			String reponame, String collabArray) {
		return ws.url("https://api.github.com/repos/"+organization+"/"+reponame)
                .get(); // THIS IS NOT BLOCKING! It returns a promise to the response. It comes from WSRequest.
	}
	

}