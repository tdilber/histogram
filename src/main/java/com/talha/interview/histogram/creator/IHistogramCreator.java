package com.talha.interview.histogram.creator;

import com.talha.interview.histogram.Histogram;

/**
 * Created by tdilber at 12-Dec-20
 */
public interface IHistogramCreator<T extends Number & Comparable> {
    Histogram<T> create() throws Exception;
}
