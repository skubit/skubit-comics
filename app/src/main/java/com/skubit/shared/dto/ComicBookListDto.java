package com.skubit.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ComicBookListDto extends DtoList<ComicBookDto> {

    /**
     *
     */
    private static final long serialVersionUID = 1376245166348182273L;

}
