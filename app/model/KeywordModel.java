package model;

import play.data.validation.Constraints;
/**
 * Model to get the search keyword from the UI
 * @author Sayali Kulkarni
 *
 */
public class KeywordModel {

	@Constraints.Required
    protected String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
