package services.userProfile;

import java.util.concurrent.CompletionStage;

import play.mvc.Http;
import play.mvc.Result;
import play.libs.ws.WSResponse;

public interface UserProfileApi {
	public CompletionStage<WSResponse> getUserProfile(String keyword);
	public CompletionStage<WSResponse> getUserRepos(String username);
}
