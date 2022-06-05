package services.repositoryIssues;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import play.libs.ws.WSClient;
import model.SearchModel;
import play.libs.ws.WSResponse;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Http;
import play.mvc.Http.Request;
import play.mvc.Result;

public class RepositoryIssuesImplementation implements RepositoryIssuesApi{

    private final WSClient ws;

    @Inject
    RepositoryIssuesImplementation(WSClient ws){
        this.ws = ws;
    }

    @Override
    public CompletionStage<WSResponse> repositoryIssues(String username, String repositoryName){
        System.out.print("------------------"+username+"---------" + repositoryName + "----------------------");
        return ws.url("https://api.github.com/repos/" + username+"/"+repositoryName+"/"+"issues")
                .get(); // THIS IS NOT BLOCKING! It returns a promise to the response. It comes from WSRequest.

    }
}
