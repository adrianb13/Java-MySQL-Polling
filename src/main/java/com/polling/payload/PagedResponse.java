package com.polling.payload;

import java.util.List;

public class PagedResponse<T> {
	private List<T> content;
	private int page;
	private int size;
	private Long totalElements;
	private int totalPages;
	private boolean last;
	
	public PagedResponse() {
	}
	
	public List<T> getContent(){
		return content;
	}
	
	public void setContent(List<T> content) {
		this.content = content;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public long getTotalElements() {
		return totalElements;
	}
	
	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}
	
	public int getTotalPages() {
		return totalPages;
	}
	
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	public boolean isLast() {
		return last;
	}
	
	public void setLast(boolean last) {
		this.last = last;
	}
}