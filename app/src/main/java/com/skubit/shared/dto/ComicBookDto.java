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
	
	private String video;
	
	//private boolean includeVideoForSeries;

	private ComicBookType comicBookType;
	
	private int votes;
	
	private boolean approved;
	
	private String artist;

	private String cbid;

	private String coverArtUrl;

	private String currencySymbol;//USD/EUR/BTC

	private String description;

	private String genre;
	
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
	
	private Date updateDate;
	
	private String vendorId;
	
	private String volume;
	
	private String writer;
	
	private IssueFormat issueFormat;
	
	private Language language;
	
	private DeliveryFormat deliveryFormat;
	
	private AgeRating ageRating;
	
	private IssueFrequency issueFrequency;

	public ComicBookType getComicBookType() {
		return comicBookType;
	}

	public void setComicBookType(ComicBookType comicBookType) {
		this.comicBookType = comicBookType;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public IssueFormat getIssueFormat() {
		return issueFormat;
	}

	public void setIssueFormat(IssueFormat issueFormat) {
		this.issueFormat = issueFormat;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public DeliveryFormat getDeliveryFormat() {
		return deliveryFormat;
	}

	public void setDeliveryFormat(DeliveryFormat deliveryFormat) {
		this.deliveryFormat = deliveryFormat;
	}

	public AgeRating getAgeRating() {
		return ageRating;
	}

	public void setAgeRating(AgeRating ageRating) {
		this.ageRating = ageRating;
	}

	public IssueFrequency getIssueFrequency() {
		return issueFrequency;
	}

	public void setIssueFrequency(IssueFrequency issueFrequency) {
		this.issueFrequency = issueFrequency;
	}

	public String getArtist() {
		return artist;
	}
	
	public String getCbid() {
		return cbid;
	}

	public String getCoverArtUrl() {
		return coverArtUrl;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}
	
	public String getDescription() {
		return description;
	}

	public String getGenre() {
		return genre;
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
	
	public Date getUpdateDate() {
		return updateDate;
	}

	public String getVendorId() {
		return vendorId;
	}

	public String getVolume() {
		return volume;
	}

	public String getWriter() {
		return writer;
	}

	public boolean isApproved() {
		return approved;
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

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setCbid(String cbid) {
		this.cbid = cbid;
	}

	public void setCoverArtUrl(String coverArtUrl) {
		this.coverArtUrl = coverArtUrl;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setGenre(String genre) {
		this.genre = genre;
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

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	@Override
	public String toString() {
		return "ComicBookDto [approved=" + approved + ", artist=" + artist
				+ ", cbid=" + cbid + ", coverArtUrl=" + coverArtUrl
				+ ", currencySymbol=" + currencySymbol + ", description="
				+ description + ", genre=" + genre + ", issueNumber="
				+ issueNumber + ", numPages=" + numPages + ", packageName="
				+ packageName + ", price=" + price + ", publishDate="
				+ publishDate + ", published=" + published + ", publisher="
				+ publisher + ", purchased=" + purchased + ", satoshi="
				+ satoshi + ", series=" + series + ", storyTitle=" + storyTitle
				+ ", updateDate=" + updateDate + ", vendorId=" + vendorId
				+ ", volume=" + volume + ", writer=" + writer
				+ ", issueFormat=" + issueFormat + ", language=" + language
				+ ", deliveryFormat=" + deliveryFormat + ", ageRating="
				+ ageRating + ", issueFrequency=" + issueFrequency + "]";
	}
	
}
