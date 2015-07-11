package com.skubit.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;

@JsonInclude(Include.NON_NULL)
public class CreatorDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3392885674835047599L;
	
	private String fullName;
	
	private String fullNameReverse;
	
	private CreatorType type;
	
	public CreatorType getType() {
		return type;
	}

	public void setType(CreatorType type) {
		this.type = type;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullNameReverse() {
		return fullNameReverse;
	}

	public void setFullNameReverse(String fullNameReverse) {
		this.fullNameReverse = fullNameReverse;
	}
}
