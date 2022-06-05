package actors;

import static java.util.Objects.requireNonNull;

import java.util.List;

import model.Repositories;
import model.UserProfileModel;

/**
 * 
 * @author Sayali Kulkarni
 *
 */
public final class Messages {

    /**
     * Create UserParentActor Message
     */
    public static final class UserParentActorCreate {
        public final String id;

        public UserParentActorCreate(String id) {
            this.id = requireNonNull(id);
        }

        @Override
        public String toString() {
            return "UserParentActorCreate(" + id + ")";
        }
    }
    /**
     * WatchSearchResults Message
     */
    public static final class WatchSearchResults {
        public final String query;

        public WatchSearchResults(String query) {
            this.query = requireNonNull(query);
        }

        @Override
        public String toString() {
            return "WatchSearchResults(" + query + ")";
        }
    }
    /**
     * 
     * RegisterActor Message
     */
    public static final class RegisterActor {
        @Override
        public String toString() {
            return "RegisterActor";
        }
    }

    /**
     * Override toString for the Messages class
     * @return string "Messages"
     */
    @Override
    public String toString() {
        return "Messages";
    }
    
    /**
     * RepositoriesMessage Message
     */
    public static final class RepositoriesMessage {
        public final List<Repositories> repos;
        public final String query;

        public RepositoriesMessage(List<Repositories> repos, String query) {
            this.repos = requireNonNull(repos);
            this.query = requireNonNull(query);
        }

        @Override
        public String toString() {
            return "RepositoriesMessage(" + query + ")";
        }
    }
    /**
     * UnwatchSearchResults Message
     */
    public static final class UnwatchSearchResults {
        public final String query;

        public UnwatchSearchResults(String query) {
            this.query = requireNonNull(query);
        }

        @Override
        public String toString() {
            return "UnwatchSearchResults(" + query + ")";
        }
    }
    
    /**
     * 
     */
    public static final class UserProfile {
    	public final String username;
    	
    	public UserProfile(String username){
    		this.username = requireNonNull(username);
    	}
    }
    public static final class TopicsKeyword {
        public final String keyword;

        public TopicsKeyword(String keyword){
            this.keyword = requireNonNull(keyword);
        }
    }
    public static final class RepositoryProfile {
    	public final String organization;
    	public final String reponame;
    	
    	public RepositoryProfile(String organization, String reponame){
    		this.organization = requireNonNull(organization);
    		this.reponame=requireNonNull(reponame);
    	}
    }
    	public static final class ReposProfile {
        	public final String organization;
        	public final String reponame;
        	public final String collabArray;
        	
        	public ReposProfile(String organization, String reponame,String collabArray){
        		this.organization = requireNonNull(organization);
        		this.reponame=requireNonNull(reponame);
        		this.collabArray=requireNonNull(collabArray);
        	}
    	
    }
}

