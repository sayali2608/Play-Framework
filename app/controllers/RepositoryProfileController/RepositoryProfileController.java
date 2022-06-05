package controllers.RepositoryProfileController;

import java.util.concurrent.CompletionStage;


import scala.util.parsing.json.JSONArray;
import services.userProfile.UserProfileService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import play.libs.ws.*;
import play.data.Form;
import play.mvc.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import views.html.UserProfileView.*;
import views.html.RepositoryProfileView.*;
import views.html.RepositoryProfileView.topIssues.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import static java.util.stream.Collectors.toList;
import play.libs.concurrent.HttpExecutionContext;
import javax.inject.Inject;
import play.libs.ws.WSClient;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import model.UserProfileModel;
import model.Repositories;
import model.SearchModel;
import model.RepositoryIssuesModel;
import model.ContributionsModel;
import model.RepositoryIssuesUserModel;
import Helper.RepositoryIssueHelper;
import model.Issues;
import play.libs.Json;
import services.repositoryProfile.*;
import java.util.stream.*;
import akka.actor.*;
//import controllers.RepositoryProfileController.ActorRef;
//import controllers.RepositoryProfileController.ActorSystem;
import play.mvc.*;
import scala.compat.java8.FutureConverters;
import services.HomeControllerService;

import static akka.pattern.Patterns.ask;
import services.repositoryProfile.*;
import actors.RepositoryProfileActor;
import actors.Messages;

/**
 * This controller contains an action to handle HTTP requests
 * to the user profile page.
 * 
 * @author Sree Pooja Chandupatla 
 */
public class RepositoryProfileController extends Controller {

	private final WSClient ws;
	RepositoryProfileService repositoryProfile;
List<Repositories> repos = new ArrayList<Repositories>();
	List<RepositoryIssuesModel> issues=new ArrayList<RepositoryIssuesModel>();
	List<ContributionsModel> collaborators=new ArrayList<ContributionsModel>();
	List<RepositoryIssuesModel> i=new ArrayList<RepositoryIssuesModel>();
	final ActorRef repositoryProfileActor;
	private HttpExecutionContext httpExecutionContext;
	
	@Inject
	RepositoryProfileController(WSClient ws, ActorSystem system,RepositoryProfileService repositoryProfile, HttpExecutionContext httpExecutionContext){
		this.ws = ws;
		this.repositoryProfileActor = system.actorOf(RepositoryProfileActor.getProps());
		this.repositoryProfile = repositoryProfile;
		this.httpExecutionContext = httpExecutionContext;
	}
    /**
     * An action that renders an HTML page displaying .
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
	/**
     * It fetches the repositories for the particular user
     * @param organization
     * @param reponame
     * @param collabArray
     * @return
     */
    
    public CompletionStage<Result> getrepositoryProfile(String reponame,String organization, String collabArray){
    	
    	
    			return FutureConverters.toJava(
    					ask(repositoryProfileActor, new Messages.ReposProfile(organization,reponame,collabArray), 1000))

                .thenApplyAsync(result -> {
                    try {
                    	
                       
                       
                    	Repositories repos = (Repositories) result;
        				return ok(RepositoryProfile.render(repos,reponame,collabArray));
                    }
                    catch(Exception e) {
                    	return ok(e.toString());
                    }
                }, httpExecutionContext.current());
    	
    }
    /**
     * It fetches the repositories issues for the particular repository
     * @param organization
     * @param reponame
     * @return
     */
    public CompletionStage<Result> getRepoIssues(String organization, String reponame) {
    	return FutureConverters.toJava(
    			ask(repositoryProfileActor, new Messages.RepositoryProfile(organization,reponame), 1000))

		.thenApply(array -> {
			ArrayNode arr=(ArrayNode) array;
			List<Issues> issues = RepositoryIssueHelper.getIssues(arr);
			List<Issues> top20Issues = issues.stream()
					.sorted(Comparator.comparing(Issues::getCreated_at).reversed()).limit(20)
					.collect(toList());
			JsonNode issuesJson=Json.toJson(top20Issues);
			//System.out.println(issuesJson);
			return ok(topIssues.render(organization,reponame,top20Issues));
		});
	}

    /**
     * It fetches the contributors for the particular repository
     * @param organization
     * @param reponame
     * @return
     */
    

    
public CompletionStage<Result> getCollaborators(String organization,String reponame ){
	return FutureConverters.toJava(
			ask(repositoryProfileActor, new Messages.RepositoryProfile(organization,reponame), 1000))

                .thenApplyAsync(result -> {
                    try {
                    List<ContributionsModel> collaborators = (List<ContributionsModel>) result;
                    List<String> collabSt = new ArrayList<String>();
    				for(ContributionsModel collab: collaborators) {
    					collabSt.add(collab.getLogin().toString());
    					
    				}
    				String collabArray = String.join(",", collabSt);
    				return redirect(routes.RepositoryProfileController.getrepositoryProfile(reponame,organization, collabArray));	
    			}
    			catch(Exception e) {
    				return ok(e.toString());
    		}
                }, httpExecutionContext.current());

    }
}

