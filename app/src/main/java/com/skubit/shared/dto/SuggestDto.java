package com.skubit.shared.dto;

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
public class SuggestDto implements Dto {

    private SuggesterDto mySuggester;

}
