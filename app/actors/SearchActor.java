package actors;

import akka.actor.AbstractActorWithTimers;

import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import model.SearchModel;
import model.Repositories;
import scala.concurrent.duration.Duration;
import services.HomeControllerService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 
 * @author Sayali Kulkarni
 *
 */
public class SearchActor extends AbstractActorWithTimers {

	@Inject
    private HomeControllerService homeControllerService;

    private ActorRef userActor;

    private String query = "javascript";

    private final LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    private List<Repositories> repos;
    
    /**
     * Dummy inner class used for the timer
     */
    public static final class Tick {
    }

    /**
     * Start the time, create a Tick every 5 seconds
     */
    @Override
    public void preStart() {
        getTimers().startPeriodicTimer("Timer", new Tick(),
                Duration.create(5, TimeUnit.SECONDS));
    }

    /**
     * Constructor
     */
    public SearchActor() {
        this.userActor = null;
        this.query = null;
        this.repos = new ArrayList<>();
    }

	@Override
	public Receive createReceive() {
		   return receiveBuilder()
	                .match(Messages.RegisterActor.class, message -> {
	                    logger.info("Registering actor {}", message);
	                    userActor = sender();
	                    getSender().tell("UserActor registered", getSelf());
	                })
	                .match(Tick.class, message -> {
	                    logger.info("Received message Tick {}", message);
	                    if (query != null) {
	                        tickMessage();
	                    }
	                })
	                .match(Messages.WatchSearchResults.class, message -> {
	                    logger.info("Received message WatchSearchResults {}", message);
	                    if (message != null && message.query != null) {
	                        watchSearchResult(message);
	                    }
	                })
	                .build();
	    }	
	/**
     * watchSearchResult message handling
     * @param message message to handle
     * @return CompletionStage of Void
     */
    public CompletionStage<Void> watchSearchResult(Messages.WatchSearchResults message) {
        // Set the query
        this.query = message.query;

        return homeControllerService.searchService(query).thenAcceptAsync(searchResults -> {
            // This is the first time we want to watch a (new) query: reset the list
            this.repos = new ArrayList<>();

            // Add all the statuses to the list
            repos.add((Repositories) searchResults.getItems().stream().limit(10).collect(Collectors.toList()));

            Messages.RepositoriesMessage repositoryMessage =
                    new Messages.RepositoriesMessage(repos, query);

            userActor.tell(repositoryMessage, self());
        });
    }
    
    /**
     * watchSearchResult message handling
     * @return CompletionStage of void
     */
    public CompletionStage<Void> tickMessage() {
        return homeControllerService.searchService(query).thenAcceptAsync(searchResults -> {
            // Copy the current state of repositories in a temporary variable
            List<Repositories> oldRepos = new ArrayList<>(repos);

            // Add all the repositories to the list, now filtered to only add the new ones
            repos.addAll(searchResults.getItems());

            // Copy the current state of statuses after addition in a temporary variable
            List<Repositories> newRepos = new ArrayList<>(repos);

            // Get the new statuses only by doing new - old = what we have to display
            newRepos.removeAll(oldRepos);

            Messages.RepositoriesMessage repositoryMessage =
                    new Messages.RepositoriesMessage(newRepos, query);
            
           System.out.println("Search Result Updated.");

            userActor.tell(repositoryMessage, self());
        });
    }

	public HomeControllerService getHomeControllerService() {
		return homeControllerService;
	}

	public void setHomeControllerService(HomeControllerService homeControllerService) {
		this.homeControllerService = homeControllerService;
	}
    
    

}
