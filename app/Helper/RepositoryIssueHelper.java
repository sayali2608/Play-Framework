package Helper;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import model.Issues;

public class RepositoryIssueHelper {
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
	public static List<Issues> getIssues(ArrayNode issues) {
		
		  CompletableFuture<List<Issues>> listCompletableFuture =
		  CompletableFuture .supplyAsync(() -> getAllObjects(issues));
		  
		  try {
		  return listCompletableFuture.get();
		  
		  } catch (Exception e) { return null;
		  
		  }
	}
	
	
	public static List<Issues> getAllObjects(ArrayNode issues) {
		List<Issues> issuesList = new ArrayList<>();
		Iterator<JsonNode> iterarator=issues.iterator();
		while(iterarator.hasNext())
		{
			Issues issue=new Issues();
			List<String> labels=new ArrayList<>();
			try {
			JsonNode x=iterarator.next();
			issue.setUrl(x.get("url").textValue());
		    issue.setRepository_url(x.get("repository_url").textValue());
			issue.setLabels_url(x.get("labels_url").textValue());
			issue.setComments_url(x.get("comments_url").textValue());
			issue.setEvents_url(x.get("events_url").textValue());
			issue.setHtml_url(x.get("html_url").textValue());
			issue.setId(x.get("id").asLong());
			issue.setNode_id(x.get("node_id").textValue());
			issue.setNumber(x.get("number").asInt());
			issue.setTitle(x.get("title").textValue());
			
			ArrayNode getLabels=(ArrayNode) x.get("labels");
			Iterator<JsonNode> labelsIterator=getLabels.iterator();
			while(labelsIterator.hasNext()) {
			labels.add(labelsIterator.next().textValue());
			}
			if(labels.isEmpty()) {
				labels.add("-");
			}	
			issue.setLabels(labels);
			issue.setState(x.get("state").textValue());
			issue.setLocked(x.get("locked").asBoolean());
			issue.setComments(x.get("comments").asInt());
			issue.setCreated_at(LocalDateTime.parse(x.get("created_at").textValue(),formatter));
			issue.setUpdated_at(LocalDateTime.parse(x.get("updated_at").textValue(),formatter));
			issue.setTimeline_url(x.get("timeline_url").textValue());
			
			issuesList.add(issue);
			}
			catch(Exception e) {
				System.out.println(e.getStackTrace());
			}
		}
		return issuesList;
}
}