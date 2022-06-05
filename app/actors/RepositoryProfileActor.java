package actors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import actors.Messages.RepositoryProfile;
import actors.Messages.ReposProfile;
import akka.actor.*;
import akka.japi.*;
import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import model.Repositories;
import model.ContributionsModel;
import model.Issues;
import services.repositoryProfile.RepositoryProfileService;


/**
 * 
 * @author Sree pooja Chandupatla
 *
 */
public class RepositoryProfileActor extends AbstractActor{
	
    private RepositoryProfileService repositoryProfileService;
	
	RepositoryProfileActor(){
		
	}
	
	public static Props getProps() {
		return Props.create(RepositoryProfileActor.class);
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Messages.RepositoryProfile.class, this::getCollaborators)
				.match(Messages.RepositoryProfile.class,this::getRepoIssues)
				.match(Messages.ReposProfile.class,this::getrepositoryProfile)
				
				
				.build();
	}
	

	public void getCollaborators(Messages.RepositoryProfile repo) {
		ContributionsModel getrepositoryProfile = (ContributionsModel) repositoryProfileService.getRepositoryProfileService(repo.organization,repo.reponame);
		sender().tell(getrepositoryProfile, self());
	}
	public void getRepoIssues(Messages.RepositoryProfile repo) {
		Issues getRepoIssues = (Issues) repositoryProfileService.getRepositoryProfileService(repo.organization,repo.reponame);
		sender().tell(getRepoIssues, self());
	}
	public void getrepositoryProfile(Messages.ReposProfile repos) {
		Repositories getrepositoryProfile= (Repositories) repositoryProfileService.getRepoProfileService(repos.organization,repos.reponame,repos.collabArray);
		sender().tell(getrepositoryProfile, self());
	}
}
