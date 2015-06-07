package com.skubit.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenreListDto extends DtoList<GenreDto> {

    /**
     *
     */
    private static final long serialVersionUID = 1376245166348182273L;
}
