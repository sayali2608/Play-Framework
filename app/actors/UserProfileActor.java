package actors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import actors.Messages.UserProfile;
import akka.actor.*;
import akka.japi.*;
import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import model.UserProfileModel;
import services.userProfile.UserProfileService;


public class UserProfileActor extends AbstractActor{
	
    private UserProfileService userProfileService;
	
	UserProfileActor(){
		
	}
	
	public static Props getProps() {
		return Props.create(UserProfileActor.class);
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Messages.UserProfile.class, username -> {
					getUserProfile(username);
				})
				.build();
	}

	public void getUserProfile(Messages.UserProfile user) {
		UserProfileModel userProfile = (UserProfileModel) userProfileService.getUserProfileService(user.username);
		sender().tell(userProfile, self());
	}
}
