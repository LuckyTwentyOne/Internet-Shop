package ua.kh.butov.ishop.form;

import java.util.ArrayList;
import java.util.List;

public class SearchForm {

	private String query;
	private List<Integer> categories;
	private List<Integer> producers;
	
	
	
	public SearchForm(String query, String[] categories, String[] producers) {
		this.query = query;
		this.categories = convert(categories);
		this.producers = convert(producers);
	}
	
	private List<Integer> convert(String[] args) {
		List<Integer> res = new ArrayList<>();
		if(args!=null) {
			for(String arg : args) {
				res.add(Integer.valueOf(arg));
			}
		}
		return res;
	}
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public List<Integer> getCategories() {
		return categories;
	}
	public void setCategories(List<Integer> categories) {
		this.categories = categories;
	}
	public List<Integer> getProducers() {
		return producers;
	}
	public void setProducers(List<Integer> producers) {
		this.producers = producers;
	}
	
	public boolean isCategoriesEmpty() {
		return categories.isEmpty();
	}
	
	public boolean isProducersEmpty() {
		return producers.isEmpty();
	}
	
}
