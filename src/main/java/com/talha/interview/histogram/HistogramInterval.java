package com.talha.interview.histogram;


import com.talha.interview.histogram.exception.IllegalValueInIntervalException;

/**
 * Created by tdilber at 12-Dec-20
 */
public class HistogramInterval<T extends Comparable> {
    protected boolean leftContain;
    protected boolean rightContain;
    protected T leftValue, rightValue;

    /**
     * (leftValue, rightValue) or  [leftValue, rightValue) or (leftValue, rightValue] or [leftValue, rightValue]
     *
     * @param leftContain
     * @param rightContain
     * @param leftValue
     * @param rightValue
     */
    public HistogramInterval(boolean leftContain, boolean rightContain, T leftValue, T rightValue) {
        this.leftContain = leftContain;
        this.rightContain = rightContain;
        if (leftValue.compareTo(rightValue) >= 0) {
            throw new IllegalValueInIntervalException("Right value must be bigger than Left value: " + leftValue + " < " + rightValue);
        }

        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    public boolean isLeftContain() {
        return leftContain;
    }

    public boolean isRightContain() {
        return rightContain;
    }

    public T getLeftValue() {
        return leftValue;
    }

    public T getRightValue() {
        return rightValue;
    }

    @Override
    public String toString() {
        return (leftContain ? "[" : "(") + leftValue + " , " + rightValue + (rightContain ? "]" : ")");
    }
}
