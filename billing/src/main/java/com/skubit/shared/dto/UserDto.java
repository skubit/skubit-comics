package com.skubit.shared.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class UserDto implements Dto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4548823909918422169L;

	private UserProfileDto userProfile;
	
	private String contactWebsite;

	private String email;

	private String payoutAddress;

	private String userId;

	private String userName;

	private String iconUrl;

	private boolean canContact;

	private String location;
	
	private int karma;

	public UserDto() {
	}

	public int getKarma() {
		return karma;
	}


	public void setKarma(int karma) {
		this.karma = karma;
	}


	public UserProfileDto getUserProfile() {
		return userProfile;
	}


	public void setUserProfile(UserProfileDto userProfile) {
		this.userProfile = userProfile;
	}


	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isCanContact() {
		return canContact;
	}

	public void setCanContact(boolean canContact) {
		this.canContact = canContact;
	}

	public UserDto(String userId, String email, String userName) {
		this.userId = userId;
		this.email = email;
		this.userName = userName;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getContactWebsite() {
		return contactWebsite;
	}

	public String getEmail() {
		return email;
	}

	public String getPayoutAddress() {
		return payoutAddress;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setContactWebsite(String contactWebsite) {
		this.contactWebsite = contactWebsite;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPayoutAddress(String payoutAddress) {
		this.payoutAddress = payoutAddress;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
