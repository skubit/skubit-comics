package com.skubit.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenreDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4642352505004761326L;
	
	private String genreName;

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String name) {
		this.genreName = name;
	}
	
}
