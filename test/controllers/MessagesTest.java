package controllers;

import actors.Messages;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Sayali Kulkarni
 *
 */
public class MessagesTest {
	/**
     * Test the UserParentActor creation
     */
    @Test
    public void testUserParentActorCreate() {
        Messages.UserParentActorCreate userParentActorCreate = new Messages.UserParentActorCreate("1");
        Assert.assertEquals("1", userParentActorCreate.id);
        Assert.assertEquals("UserParentActorCreate(1)", userParentActorCreate.toString());
    }

    /**
     * Test WatchSearchResults class
     */
    @Test
    public void testWatchSearchResults() {
        Messages.WatchSearchResults watchSearchResults = new Messages.WatchSearchResults("test");
        Assert.assertEquals("test", watchSearchResults.query);
        Assert.assertEquals("WatchSearchResults(test)", watchSearchResults.toString());
    }

    /**
     * Test UnwatchSearchResults class
     */
    @Test
    public void testUnwatchSearchResults() {
        Messages.UnwatchSearchResults unWatchSearchResults = new Messages.UnwatchSearchResults("test");
        Assert.assertEquals("test", unWatchSearchResults.query);
        Assert.assertEquals("UnwatchSearchResults(test)", unWatchSearchResults.toString());
    }

}
