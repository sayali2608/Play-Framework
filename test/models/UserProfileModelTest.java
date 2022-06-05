package models;

import org.junit.Test;
import org.junit.*;
import static org.junit.Assert.assertEquals;

import model.UserProfileModel;

public class UserProfileModelTest {

	@Test
	public void userLoginTest() {
		UserProfileModel user = new UserProfileModel();
		user.setLogin("Sayali2608");
		assertEquals(user.getLogin(), "Sayali2608");
	}
	
	@Test
	public void userFollowersTest() {
		UserProfileModel user = new UserProfileModel();
		user.setFollowers(100);;
		assertEquals(user.getFollowers(), 100);
	}
	
	@Test
	public void userFollowingTest() {
		UserProfileModel user = new UserProfileModel();
		user.setFollowing(10);;
		assertEquals(user.getFollowing(), 10);
	}
	
	@Test
	public void userNameTest() {
		UserProfileModel user = new UserProfileModel();
		user.setName("Sayali Kulkarni");;
		assertEquals(user.getName(), "Sayali Kulkarni");
	}
}
