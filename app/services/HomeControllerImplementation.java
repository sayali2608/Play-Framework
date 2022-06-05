package services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import Helper.CacheController;
import play.libs.ws.WSClient;
import model.SearchModel;
import play.libs.ws.WSResponse;
import model.Repositories;
import model.KeywordModel;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Http;
import play.mvc.Http.Request;
import play.mvc.Result;

/**
 * 
 * @author Sayali Kulkarni
 *
 */
public class HomeControllerImplementation implements GithubApi{
	private final WSClient ws;
	
	 /**
	  * The constructor injects the necessary dependancies for the class
	  * @param ws
	  */
	@Inject
	HomeControllerImplementation(WSClient ws){
		this.ws = ws;
	}
    

    /**
     * Renders the searched keyword with it's username, repositories and topics
     * @param keyword
     * @return
     */
	@Override
	public CompletionStage<WSResponse> getSearch(String keyword){
		return ws.url("https://api.github.com/search/repositories")
				.addQueryParameter("q", keyword)
                .get();// THIS IS NOT BLOCKING! It returns a promise to the response. It comes from WSRequest.
	}
	
}
