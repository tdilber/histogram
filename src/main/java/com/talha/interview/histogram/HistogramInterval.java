package com.talha.interview.histogram;


import com.talha.interview.histogram.exception.IllegalValueInIntervalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tdilber at 12-Dec-20
 */
public class HistogramInterval<T extends Comparable> {
    private final static Logger log = LoggerFactory.getLogger(HistogramInterval.class);

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
        log.trace("Constructor begin");
        this.leftContain = leftContain;
        this.rightContain = rightContain;
        if (leftValue.compareTo(rightValue) >= 0) {
            String message = "Right value must be bigger than Left value: " + leftValue + " < " + rightValue;
            log.error(message);
            throw new IllegalValueInIntervalException(message);
        }

        this.leftValue = leftValue;
        this.rightValue = rightValue;
        log.trace("Constructor end");
    }

    /**
     * Get Left Corner is Contained
     *
     * @return left corner contain
     */
    public boolean isLeftContain() {
        return leftContain;
    }

    /**
     * Get Right Corner is Contained
     *
     * @return right corner contain
     */
    public boolean isRightContain() {
        return rightContain;
    }

    /**
     * Get Smaller Value
     *
     * @return smaller value
     */
    public T getLeftValue() {
        return leftValue;
    }

    /**
     * Get Bigger Value
     *
     * @return bigger value
     */
    public T getRightValue() {
        return rightValue;
    }

    @Override
    public String toString() {
        return (leftContain ? "[" : "(") + leftValue + " , " + rightValue + (rightContain ? "]" : ")");
    }

    /**
     * Check value is available for current interval
     *
     * @param value value to check
     * @return check result
     */
    public boolean isAvailableValue(T value) {
        log.trace("isAvailableValue method called");
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

    /**
     * Check another interval for intersect with current interval
     *
     * @param hi interval to check
     * @return check result
     */
    public boolean isIntersect(HistogramInterval<T> hi) {
        log.trace("isIntersect method called");
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
