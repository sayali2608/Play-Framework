package models;

import org.junit.Test;
import org.junit.*;
import static org.junit.Assert.assertEquals;

import model.KeywordModel;

public class KeywordsTest {

	@Test
	public void keywordTest() {
		KeywordModel keyword = new KeywordModel();
		keyword.setKeyword("Concordia");
		assertEquals(keyword.getKeyword(), "Concordia");
	}
}