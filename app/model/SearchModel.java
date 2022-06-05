package model;

import java.util.ArrayList;
/**
 * Model for the common search
 * @author Sayali Kulkarni
 *
 */
public class SearchModel {
private int total_count;
public int getTotal_count() {
	return total_count;
}
public void setTotal_count(int total_count) {
	this.total_count = total_count;
}
public boolean isIncomplete_results() {
	return incomplete_results;
}
public void setIncomplete_results(boolean incomplete_results) {
	this.incomplete_results = incomplete_results;
}
public ArrayList<Repositories> getItems() {
	return items;
}
public void setItems(ArrayList<Repositories> items) {
	this.items = items;
}
private boolean incomplete_results;
private ArrayList<Repositories> items;
}
