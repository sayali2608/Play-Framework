package controllers;

import play.mvc.*;
import services.HomeControllerService;
import views.html.*;
import java.util.concurrent.CompletionStage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Helper.CacheController;
import java.util.stream.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import java.util.concurrent.CompletionStage;

import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import javax.inject.Inject;

 	
import play.libs.ws.WSClient;
import model.SearchModel;
import model.Repositories;
import model.KeywordModel;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 * It handles the actions on the search page
 * @author Sayali Kulkarni, Kirthana Senguttuvan
 */
public class HomeController extends Controller {

	HomeControllerService homeControllerService;
	private HttpExecutionContext httpExecutionContext;
	FormFactory formFactory;
	private static CacheController cache = new CacheController();

	  public Result index() {
		  Map<String, List<Repositories>> cacheMap = new HashMap<>();
		  return ok(index.render(formFactory.form(KeywordModel.class), cacheMap)); 
	  }
	 /**
	  * The constructor injects the necessary dependencies for the class
	  * @param ws
	  * @param httpExecutionContext
	  * @param formFactory
	  */
	@Inject
	HomeController(HomeControllerService homeControllerService, HttpExecutionContext httpExecutionContext, FormFactory formFactory){
		this.homeControllerService = homeControllerService;
		this.httpExecutionContext = httpExecutionContext;
		this.formFactory = formFactory;
	}
    public HomeController() {
	}
	/**
     * Extracts the keyword from the UI
     * @param request
     * @return Result
     */
    public Result getSearchResult(Http.Request request){
    	
    	Form<KeywordModel> searchForm = formFactory.form(KeywordModel.class).bindFromRequest(request);
    	KeywordModel keywordModel = searchForm.get();
    	String keyword = keywordModel.getKeyword();
    	return redirect(routes.HomeController.getSearch(keyword));
    	
	}

    /**
     * Renders the searched keyword with it's username, repositories and topics
     * @param keyword
     * @return
     */
    public CompletionStage<Result> getSearch(String keyword){
    	return homeControllerService.searchService(keyword)
    			.thenApplyAsync(result -> {
    					List<Repositories> repos = result.getItems().stream().limit(10).collect(Collectors.toList());
                    	cache.setCache(keyword, repos);
                    	Map<String, List<Repositories>> cacheMap = cache.get_cache();
                        return ok(index.render(formFactory.form(KeywordModel.class), cacheMap));
                    }, httpExecutionContext.current());

    }
}
