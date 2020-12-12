package com.talha.interview.histogram;

/**
 * Created by tdilber at 12-Dec-20
 * <p>
 * Histogram interface also contain mean and variance interfaces
 */
public interface IHistogram<T extends Number & Comparable> extends IVariance<T> {
    /**
     * Add new interval in Histogram
     *
     * @param histogramInterval interval to add
     */
    void addInterval(HistogramInterval<T> histogramInterval);

    /**
     * Add new value in Histogram
     *
     * @param value value to add
     */
    void addValue(T value);
}
