package com.skubit.shared.dto;

import java.io.Serializable;

/**
 * suggest: {
 mySuggester: {
 Mu: {
 numFound: 1,
 suggestions: [
 {
 term: "Museum of the Macabre",
 weight: 0,
 payload: ""
 }
 ]
 }
 }
 }
 */
public class SuggestDto implements Serializable {

    private SuggesterDto mySuggester;

}
