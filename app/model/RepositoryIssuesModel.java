package model;

import java.util.Date;
import java.util.List;

/**
 * This class is a model to deserialize the json 
 * from github api
 * 
 * @author Kirthana Senguttuvan 
 */
public class RepositoryIssuesModel{
	 private String issue_number;
	 private String issue_title;
	 private String state ;
	 private String created_at;
	 private String updated_at;
	 
	 /** Constructor to initialize Repository Issue Details 
	  * 
	  * @param issue_number  Issue number from the git API
	  * @param issue_title 	 Issue Title from the git API
	  * @param state		 State of the issue raised from the git API
	  * @param created_at    TimeStamp of the issue created time
	  * @param updated_at    TimeStamp of issue's last updated time
	  */
	public RepositoryIssuesModel(String issue_number, String issue_title, String state, String created_at,
			String updated_at) {
		this.issue_number = issue_number;
		this.issue_title = issue_title;
		this.state = state;
		this.created_at = created_at;
		this.updated_at = updated_at;
		
	}
	/**
	 * 
	 * @return returns title of the issue for further analysis
	 */
	public String getIssue_title() {
		return this.issue_title;
	}
	
}




