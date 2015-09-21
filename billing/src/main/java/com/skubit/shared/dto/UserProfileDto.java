package com.skubit.shared.dto;

import java.util.ArrayList;


public class UserProfileDto implements Dto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 830003851690231490L;
	
	private String promo;
	
	private String comixologyLink;
	
	private String bio;
	
	private String twitterName;
	
	private String facebookName;
	
	private String googlePlus;
			
	private String publicEmail;
	
	private String experience;
	
	private String blog;
	
	private String alternateStore;
	
	private ArrayList<SkillType> skillTypes;
	
	private boolean collaborate;
	
	private boolean lookingForJob;
	
	public String getPromo() {
		return promo;
	}

	public void setPromo(String promo) {
		this.promo = promo;
	}

	public boolean isCollaborate() {
		return collaborate;
	}

	public void setCollaborate(boolean collaborate) {
		this.collaborate = collaborate;
	}

	public boolean isLookingForJob() {
		return lookingForJob;
	}

	public void setLookingForJob(boolean lookingForJob) {
		this.lookingForJob = lookingForJob;
	}

	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}

	public String getAlternateStore() {
		return alternateStore;
	}

	public void setAlternateStore(String alternateStore) {
		this.alternateStore = alternateStore;
	}



	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public ArrayList<SkillType> getSkillTypes() {
		return skillTypes;
	}

	public void setSkillTypes(ArrayList<SkillType> skillTypes) {
		this.skillTypes = skillTypes;
	}

	public String getComixologyLink() {
		return comixologyLink;
	}

	public void setComixologyLink(String comixologyLink) {
		this.comixologyLink = comixologyLink;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getTwitterName() {
		return twitterName;
	}

	public void setTwitterName(String twitterName) {
		this.twitterName = twitterName;
	}

	public String getFacebookName() {
		return facebookName;
	}

	public void setFacebookName(String facebookName) {
		this.facebookName = facebookName;
	}

	public String getGooglePlus() {
		return googlePlus;
	}

	public void setGooglePlus(String googlePlus) {
		this.googlePlus = googlePlus;
	}

	public String getPublicEmail() {
		return publicEmail;
	}

	public void setPublicEmail(String publicEmail) {
		this.publicEmail = publicEmail;
	}
	
	
}
