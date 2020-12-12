package com.talha.interview.histogram;

/**
 * Created by tdilber at 12-Dec-20
 */
public class HistogramLeftContainInterval<T extends Comparable> extends HistogramInterval<T> {

    /**
     * [leftValue, rightValue)
     *
     * @param leftValue
     * @param rightValue
     */
    public HistogramLeftContainInterval(T leftValue, T rightValue) {
        super(true, false, leftValue, rightValue);
    }
}
