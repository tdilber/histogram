package com.talha.interview.histogram;

/**
 * Created by tdilber at 12-Dec-20
 */
public class HistogramLeftContainInterval<T extends Comparable> extends HistogramInterval<T> {

    /**
     * [leftValue, rightValue)
     *
     * @param leftValue  Smaller Value
     * @param rightValue Bigger Value
     */
    public static <T extends Comparable> HistogramLeftContainInterval<T> of(T leftValue, T rightValue) {
        return new HistogramLeftContainInterval<T>(leftValue, rightValue);
    }


    /**
     * [leftValue, rightValue)
     *
     * @param leftValue  Smaller Value
     * @param rightValue Bigger Value
     */
    public HistogramLeftContainInterval(T leftValue, T rightValue) {
        super(true, false, leftValue, rightValue);
    }
}
