package com.skubit.shared.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class LockerItemDto implements Dto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3559846565234314372L;
	
	private ComicBookType comicBookType;
	
	private String title;
	
	private String coverArt;
	
	private String userId;
	
	private String vendorId;
	
	private Date orderDate;
	
	private long satoshi;
	
	private String productId;

	private String orderId;

	private String application;
	
	private String volume;
	
	private int issue;
	
	public ComicBookType getComicBookType() {
		return comicBookType;
	}

	public void setComicBookType(ComicBookType comicBookType) {
		this.comicBookType = comicBookType;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public int getIssue() {
		return issue;
	}

	public void setIssue(int issue) {
		this.issue = issue;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCoverArt() {
		return coverArt;
	}

	public void setCoverArt(String coverArt) {
		this.coverArt = coverArt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public long getSatoshi() {
		return satoshi;
	}

	public void setSatoshi(long satoshi) {
		this.satoshi = satoshi;
	}
	
	
}
