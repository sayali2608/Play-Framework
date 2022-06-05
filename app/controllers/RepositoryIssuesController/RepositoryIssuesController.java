package controllers.RepositoryIssuesController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import Helper.WordStats;
import model.RepositoryIssuesModel;
import play.libs.concurrent.HttpExecutionContext;
import play.libs.ws.WSClient;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.RepositoryIssuesView.*;
import services.repositoryIssues.*;
import services.repositoryProfile.RepositoryProfileService;
/**
 * This controller contains an action to handle HTTP requests
 * to the user profile page.
 * * 
 * @author Kirthana Senguttuvan 
 */
 
public class RepositoryIssuesController extends Controller {

	private final WSClient ws;
	private RepositoryIssuesService repositoryIssuesService;
	private HttpExecutionContext httpExecutionContext;
	private String issue_number;
	private String issue_title;
	private String state;
	private String created_at;
	private String updated_at;
	
	/**
	 * Constructor to initialize WsClient and HttpExecutionContext
	 * @param ws  Parameter to get WSClient Object
	 * @param httpExecutionContext Parameter to get HttpExecutionContext
	 */
	@Inject
	public RepositoryIssuesController(WSClient ws, RepositoryIssuesService repositoryIssuesService, HttpExecutionContext httpExecutionContext){
		this.ws = ws;
		this.repositoryIssuesService = repositoryIssuesService;
		this.httpExecutionContext = httpExecutionContext;
	}
	/**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    /**
     * 
     * @param username      User Name of the repository passed from profile 
     * @param repositoryName  Repository Name passed from profile
     * @return CompletionStage<Result> to render html page under view
     */
    public CompletionStage<Result> repositoryIssues(String username, String repositoryName){
		return repositoryIssuesService.getRepositoryIssuesService(username,repositoryName)
				// THIS IS NOT BLOCKING! It returns a promise to the response. It comes from WSRequest.
                .thenApplyAsync(result -> {
                    try {
                    	JsonNode rootNode = result;
                    	List<RepositoryIssuesModel> issueModel = new ArrayList<>(); 
                    	rootNode.forEach(items -> { // DESERIALIZING THE NECESSARY VALUES FROM API
                			 String issue_number = items.get("number").toString();
                			 String issue_title = items.get("title").toString();
                			 String state = items.get("state").toString();
                			 String created_at = items.get("created_at").toString();
                			 String updated_at = items.get("updated_at").toString();
                			 issueModel.add(new RepositoryIssuesModel(issue_number,issue_title,state,created_at,updated_at));
                		 });
                    	List<String> titles = new ArrayList<>();
                    	issueModel.forEach(items->{
                    		titles.add(items.getIssue_title());
                    		
                    	});
                    	WordStats stats = new WordStats(); // PERFORMING WORD LEVEL STATISTIC ON THE SPLIT TITLES
                    	Map<String, Integer> finalStats = new HashMap<String, Integer>();
                    	finalStats = stats.countWords(titles);
                    	/**
                    	 * CREATING A StringBuilder OBJECT TO AS HTML TO RENDER THE CONTENT DIRECTLY TO THE VIEW
                    	 */
                    	StringBuilder htmlBuilder = new StringBuilder();
                    	htmlBuilder.append("<h1> Issue Stats </h1>");
                    	htmlBuilder.append("<table border = \"1\">");
                    	htmlBuilder.append("<tr> <th>Word</th> <th>Count</th></tr>");
                    	for (Map.Entry<String, Integer> entry : finalStats.entrySet()) {
                    	    htmlBuilder.append(String.format("<tr><td>%s</td><td>%d</td></tr>",
                    	            entry.getKey(), entry.getValue()));
                    	}

                    	htmlBuilder.append("</table >");

                    	String html = htmlBuilder.toString();
                    	return ok(RepositoryIssues.render(html));
                    }
                    catch(Exception e) {
                    	return ok(e.toString());
                    }
                }, httpExecutionContext.current());
}
}