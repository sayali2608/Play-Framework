package models;

import org.junit.Test;
import org.junit.*;
import static org.junit.Assert.assertEquals;

import model.Repositories;

public class RepositoriesTest {

	@Test
	public void repoIssuesCountTest() {
		Repositories repo = new Repositories();
		repo.setOpen_issues_count(100);
		assertEquals(repo.getOpen_issues_count(), 100);
	}
	
	@Test
	public void repoForksTest() {
		Repositories repo = new Repositories();
		repo.setForks_count(100);;
		assertEquals(repo.getForks_count(), 100);
	}
	
	@Test
	public void repoWatchersCountTest() {
		Repositories repo = new Repositories();
		repo.setWatchers_count(10);;
		assertEquals(repo.getWatchers_count(), 10);
	}
	
	@Test
	public void repoNameTest() {
		Repositories repo = new Repositories();
		repo.setName("Sree pooja");
		assertEquals(repo.getName(), "Sree pooja");
	}
}
