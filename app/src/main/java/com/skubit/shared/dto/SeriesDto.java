package com.skubit.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;

@JsonInclude(Include.NON_NULL)
public class SeriesDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5736727109787783848L;
	
	private String id;
	
	private String seriesName;
	
	private String coverArt;
	
	private int numberOfIssues;
	
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String name) {
		this.seriesName = name;
	}

	public String getCoverArt() {
		return coverArt;
	}

	public void setCoverArt(String coverArt) {
		this.coverArt = coverArt;
	}

	public int getNumberOfIssues() {
		return numberOfIssues;
	}

	public void setNumberOfIssues(int numberOfIssues) {
		this.numberOfIssues = numberOfIssues;
	}
	
}
