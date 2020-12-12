package com.talha.interview.histogram;


import com.talha.interview.histogram.exception.IllegalValueInIntervalException;

/**
 * Created by tdilber at 12-Dec-20
 */
public class HistogramInterval<T extends Comparable> {
    protected boolean leftContain;
    protected boolean rightContain;
    protected T leftValue;
    protected T rightValue;

    /**
     * (leftValue, rightValue) or  [leftValue, rightValue) or (leftValue, rightValue] or [leftValue, rightValue]
     *
     * @param leftContain  leftContain ? "[" : "("
     * @param rightContain rightContain ? "]" : ")"
     * @param leftValue    Smaller Value
     * @param rightValue   Bigger Value
     */
    public static <T extends Comparable> HistogramInterval<T> of(boolean leftContain, boolean rightContain, T leftValue, T rightValue) {
        return new HistogramInterval<T>(leftContain, rightContain, leftValue, rightValue);
    }

    /**
     * (leftValue, rightValue) or  [leftValue, rightValue) or (leftValue, rightValue] or [leftValue, rightValue]
     *
     * @param leftContain  leftContain ? "[" : "("
     * @param rightContain rightContain ? "]" : ")"
     * @param leftValue    Smaller Value
     * @param rightValue   Bigger Value
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

    public boolean isAvailableValue(T value) {
        int leftC = this.leftValue.compareTo(value);
        int rightC = this.rightValue.compareTo(value);
        if (leftC < 0 && rightC > 0) {
            return true;
        } else if (leftC != 0 && rightC != 0) {
            return false;
        } else if (leftC == 0) {
            return this.leftContain;
        } else { //if (rightC == 0)
            return this.rightContain;
        }
    }

    public boolean isIntersect(HistogramInterval<T> hi) {
        int leftC = this.leftValue.compareTo(hi.rightValue);
        int rightC = this.rightValue.compareTo(hi.leftValue);
        if (leftC > 0 || rightC < 0) {
            return false;
        } else if (leftC != 0 && rightC != 0) {
            return true;
        } else if (leftC == 0) {
            return this.leftContain && hi.rightContain;
        } else { //if (rightC == 0)
            return this.rightContain && hi.leftContain;
        }
    }
}
