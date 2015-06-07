package com.skubit.comics;

import junit.framework.TestCase;

import android.os.Bundle;

public class SeriesFilterTest extends TestCase {

    public void testFilter() throws Exception {
        SeriesFilter filter = new SeriesFilter();
        filter.creator = "a_creator";
        filter.publisher = "a_publisher";

        Bundle bundle = SeriesFilter.toBundle(filter);
        SeriesFilter filterTest = bundle.getParcelable("com.skubit.comics.SERIES_FILTER");
        assertEquals("a_publisher", filterTest.publisher);
        assertEquals("a_creator", filterTest.creator);
    }
}
