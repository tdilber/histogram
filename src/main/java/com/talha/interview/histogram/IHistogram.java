package com.talha.interview.histogram;

/**
 * Created by tdilber at 12-Dec-20
 */
public interface IHistogram<T extends Number & Comparable> extends IVariance<T> {
    void addInterval(HistogramInterval<T> histogramInterval);

    void addValue(T value);
}
