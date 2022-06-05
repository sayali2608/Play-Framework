package actors;

import actors.Messages.WatchSearchResults;

import akka.actor.AbstractActor;
import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.stream.UniqueKillSwitch;
import akka.stream.javadsl.*;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import akka.NotUsed;
import actors.Messages.UnwatchSearchResults;

/**
 * 
 * @author Sayali Kulkarni
 *
 */
public class UserActor extends AbstractActor{
	
    private Map<String, UniqueKillSwitch> searchResultsMap = new HashMap<>();
    private Map<String, ActorRef> searchResultsActors;
    private Flow<JsonNode, JsonNode, NotUsed> websocketFlow;
    private Injector injector;
    private final LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

	/**
     * Factory interface to create a UserActor from the UserParentActor
     */
    public interface Factory {
        Actor create(String id);
    }
    
    /**
     * Regular constructor
     * @param injector Guice Injector, used later to create the SearchResultsActor with GuiceInjectedActor
     * @param mat Materializer for the Akka streams
     */
    @Inject
    public UserActor(Injector injector) {
        this.searchResultsActors = new HashMap<>();
        this.injector = injector;
    }


	@Override
	public Receive createReceive() {
		return receiveBuilder()
                .match(WatchSearchResults.class, watchSearchResults -> {
                    logger.info("Received message WatchSearchResults {}", watchSearchResults);
                    if (watchSearchResults != null) {
                        // Ask the searchResultsActors for a stream containing these searchResults
                        askForRepositories(watchSearchResults.query);
                        sender().tell(websocketFlow, self());
                    }
                })
                .match(UnwatchSearchResults.class, unwatchSearchResults -> {
                    logger.info("Received message UnwatchSearchResults {}", unwatchSearchResults);
                    if (unwatchSearchResults != null) {
                        searchResultsMap.get(unwatchSearchResults.query).shutdown();
                        searchResultsMap.remove(unwatchSearchResults.query);
                    }
                })
                .build();
	}
	
	/**
     * If there already exists a SearchResultsActor for the keyword we want, ask it for updates
     * Otherwise, create a new one, register the UserActor and wait the results
     * @param query
     */
    private void askForRepositories(String query) {
        ActorRef actorForQuery = searchResultsActors.get(query);
        if (actorForQuery != null) {
            actorForQuery.tell(new WatchSearchResults(query), self());
        } else {
            actorForQuery = getContext().actorOf(Props.create(GuiceInjectedActor.class, injector,
                    SearchActor.class));
            searchResultsActors.put(query, actorForQuery);
            actorForQuery.tell(new Messages.RegisterActor(), self());
            actorForQuery.tell(new WatchSearchResults(query), self());
        }
    }
}
