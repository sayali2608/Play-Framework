package services;

import java.util.concurrent.CompletionStage;

import play.mvc.Http;
import play.mvc.Result;
import play.libs.ws.WSResponse;

/**
 * 
 * @author Sayali Kulkarni
 *
 */
public interface GithubApi {
	public CompletionStage<WSResponse> getSearch(String keyword);
}
