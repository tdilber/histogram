package com.talha.interview.histogram;

import java.util.List;
import java.util.Set;

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

    /**
     * Get Histogram Out Liner Values List
     *
     * @return Out Liner Values
     */
    public List<T> getOutLinerValueList();

    /**
     * Get Histogram Mapped Values List
     *
     * @return Mapped Values List
     */
    public List<T> getMappedValueList();

    /**
     * Get Histogram Interval Set
     *
     * @return Interval Set
     */
    public Set<HistogramInterval<T>> getHistogramIntervalSet();
}
