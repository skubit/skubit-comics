package com.skubit.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;

@JsonInclude(Include.NON_NULL)
public class PublisherDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5736727109787783848L;
	
	private String coverArt;
	
	private String publisherName;
	
	private int numberOfSeries;

	public String getCoverArt() {
		return coverArt;
	}

	public void setCoverArt(String coverArt) {
		this.coverArt = coverArt;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisher) {
		this.publisherName = publisher;
	}

	public int getNumberOfSeries() {
		return numberOfSeries;
	}

	public void setNumberOfSeries(int numberOfSeries) {
		this.numberOfSeries = numberOfSeries;
	}
}
