package com.skubit.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Date;

@JsonInclude(Include.NON_NULL)
public class ComicBookDto implements Dto {

    /**
     *
     */
    private static final long serialVersionUID = 68944290937084026L;

    private String cbid;

    private String description;

    private String iconUrl;

    private boolean published;

    private boolean approved;

    private int issueNumber;

    private String packageName;

    private Date publishDate;

    private String publisher;

    private int rating;

    private String storyTitle;

    private String summary;

    private Date updateDate;

    private String vendorId;

    private String website;

    private long satoshi;

    private int numPages;

    private String contactEmail;

    private String currencySymbol;//USD/EUR/BTC

    private double price;

    private boolean purchased;

    private String coverArtUrlLarge;

    private String coverArtUrlMedium;

    private String coverArtUrlSmall;

    public String getCoverArtUrlLarge() {
        return coverArtUrlLarge;
    }

    public void setCoverArtUrlLarge(String coverArtUrlLarge) {
        this.coverArtUrlLarge = coverArtUrlLarge;
    }

    public String getCoverArtUrlMedium() {
        return coverArtUrlMedium;
    }

    public void setCoverArtUrlMedium(String coverArtUrlMedium) {
        this.coverArtUrlMedium = coverArtUrlMedium;
    }

    public String getCoverArtUrlSmall() {
        return coverArtUrlSmall;
    }

    public void setCoverArtUrlSmall(String coverArtUrlSmall) {
        this.coverArtUrlSmall = coverArtUrlSmall;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean isPurchased) {
        this.purchased = isPurchased;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public String getCbid() {
        return cbid;
    }

    public void setCbid(String cbid) {
        this.cbid = cbid;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public long getSatoshi() {
        return satoshi;
    }

    public void setSatoshi(long satoshi) {
        this.satoshi = satoshi;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issue) {
        this.issueNumber = issue;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean isPublished) {
        this.published = isPublished;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean isApproved) {
        this.approved = isApproved;
    }
}
