package com.talha.interview.histogram;

/**
 * Created by tdilber at 12-Dec-20
 * <p>
 * Variance Interface also contain Mean Interface
 */
public interface IVariance<T> extends IMean<T> {
    /**
     * Get variance
     *
     * @return variance
     */
    Double variance();
}
