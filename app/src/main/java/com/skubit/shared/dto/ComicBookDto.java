package com.skubit.shared.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ComicBookDto implements Dto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 68944290937084026L;
	
	private boolean approved;
	
	private String cbid;

	private String contactEmail;

	private String coverArtUrlLarge;
	
	private String coverArtUrlMedium;

	private String coverArtUrlSmall;

	private String currencySymbol;//USD/EUR/BTC

	private String description;
	
	private String iconUrl;
	
	private boolean isExplicit;
	
	private int issueNumber;
	
	private int numPages;
	
	private String packageName;
	
	private double price;
	
	private Date publishDate;
	
	private boolean published;
	
	private String publisher;
	
	private boolean purchased;
	
	private long satoshi;
	
	private String series;
	
	private String storyTitle;
	
	private String summary;
	
	private Date updateDate;
	
	private String vendorId;
	
	private String website;
	
	public String getCbid() {
		return cbid;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public String getCoverArtUrlLarge() {
		return coverArtUrlLarge;
	}

	public String getCoverArtUrlMedium() {
		return coverArtUrlMedium;
	}

	public String getCoverArtUrlSmall() {
		return coverArtUrlSmall;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public String getDescription() {
		return description;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public int getIssueNumber() {
		return issueNumber;
	}

	public int getNumPages() {
		return numPages;
	}

	public String getPackageName() {
		return packageName;
	}

	public double getPrice() {
		return price;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public String getPublisher() {
		return publisher;
	}

	public long getSatoshi() {
		return satoshi;
	}

	public String getSeries() {
		return series;
	}

	public String getStoryTitle() {
		return storyTitle;
	}

	public String getSummary() {
		return summary;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public String getVendorId() {
		return vendorId;
	}

	public String getWebsite() {
		return website;
	}

	public boolean isApproved() {
		return approved;
	}

	public boolean isExplicit() {
		return isExplicit;
	}

	public boolean isPublished() {
		return published;
	}

	public boolean isPurchased() {
		return purchased;
	}

	public void setApproved(boolean isApproved) {
		this.approved = isApproved;
	}

	public void setCbid(String cbid) {
		this.cbid = cbid;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public void setCoverArtUrlLarge(String coverArtUrlLarge) {
		this.coverArtUrlLarge = coverArtUrlLarge;
	}

	public void setCoverArtUrlMedium(String coverArtUrlMedium) {
		this.coverArtUrlMedium = coverArtUrlMedium;
	}

	public void setCoverArtUrlSmall(String coverArtUrlSmall) {
		this.coverArtUrlSmall = coverArtUrlSmall;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExplicit(boolean isExplicit) {
		this.isExplicit = isExplicit;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public void setIssueNumber(int issue) {
		this.issueNumber = issue;
	}

	public void setNumPages(int numPages) {
		this.numPages = numPages;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public void setPublished(boolean isPublished) {
		this.published = isPublished;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setPurchased(boolean isPurchased) {
		this.purchased = isPurchased;
	}

	public void setSatoshi(long satoshi) {
		this.satoshi = satoshi;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public void setStoryTitle(String storyTitle) {
		this.storyTitle = storyTitle;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
}
