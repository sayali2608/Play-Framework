package model;

import java.time.LocalDateTime;


import java.util.Arrays;
import java.util.List;

public class Issues {
	private String url;
	private String repository_url;
	private String labels_url;
	private String comments_url;
	private String events_url;
	private String html_url;
	private long id;
	private String node_id;
	private int number;
	private String title;
	private List<String> labels;
	private String state;
	private boolean locked;
	private int comments;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	private LocalDateTime closed_at;
	private String author_association;
	private String body;
	private String timeline_url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRepository_url() {
		return repository_url;
	}
	public void setRepository_url(String repository_url) {
		this.repository_url = repository_url;
	}
	public String getLabels_url() {
		return labels_url;
	}
	public void setLabels_url(String labels_url) {
		this.labels_url = labels_url;
	}
	public String getComments_url() {
		return comments_url;
	}
	public void setComments_url(String comments_url) {
		this.comments_url = comments_url;
	}
	public String getEvents_url() {
		return events_url;
	}
	public void setEvents_url(String events_url) {
		this.events_url = events_url;
	}
	public String getHtml_url() {
		return html_url;
	}
	public void setHtml_url(String html_url) {
		this.html_url = html_url;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNode_id() {
		return node_id;
	}
	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public int getComments() {
		return comments;
	}
	public void setComments(int comments) {
		this.comments = comments;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public LocalDateTime getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
	public LocalDateTime getClosed_at() {
		return closed_at;
	}
	public void setClosed_at(LocalDateTime closed_at) {
		this.closed_at = closed_at;
	}
	public String getAuthor_association() {
		return author_association;
	}
	public void setAuthor_association(String author_association) {
		this.author_association = author_association;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTimeline_url() {
		return timeline_url;
	}
	public void setTimeline_url(String timeline_url) {
		this.timeline_url = timeline_url;
	}
	@Override
	public String toString() {
		return String.format(
				"Issues [url=%s, repository_url=%s, labels_url=%s, comments_url=%s, events_url=%s, html_url=%s, id=%s, node_id=%s, number=%s, title=%s, labels=%s, state=%s, locked=%s, comments=%s, created_at=%s, updated_at=%s, closed_at=%s, author_association=%s, body=%s, timeline_url=%s]",
				url, repository_url, labels_url, comments_url, events_url, html_url, id, node_id, number, title, labels,
				state, locked, comments, created_at, updated_at, closed_at, author_association, body, timeline_url);
	}

	
	
	
	
}
