package com.talha.interview.histogram;

import com.talha.interview.histogram.exception.IntersectValueInHistogramIntervalsException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by tdilber at 12-Dec-20
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Histogram<T extends Number & Comparable> implements IHistogram<T> {

    /**
     * T => leftValue
     * HistogramInterval<T> => Interval.
     */
    private TreeMap<T, HistogramInterval<T>> histogramIntervalTreeMap = new TreeMap<>();
    private Map<HistogramInterval<T>, List<T>> valueMap = new HashMap<>();
    private List<T> outLinerValueList = new ArrayList<>();
    private List<T> allValueList = new ArrayList<>();

    @Override
    public T mean() {
        return null;
    }

    @Override
    public T variance() {
        return null;
    }

    /**
     * Synchronized for Thread Safety
     */
    @Override
    public synchronized void addInterval(HistogramInterval<T> newInterval) {
        checkIntersect(newInterval);
        histogramIntervalTreeMap.put(newInterval.getLeftValue(), newInterval);
        valueMap.put(newInterval, new ArrayList<>());
        checkOutLinerList(newInterval);
    }

    private void checkOutLinerList(HistogramInterval<T> newInterval) {
        for (T value : outLinerValueList) {
            if (newInterval.isAvailableValue(value)) {
                valueMap.get(newInterval).add(value);
            }
        }

        for (T savedOutLiner : valueMap.get(newInterval)) {
            outLinerValueList.remove(savedOutLiner);
        }
    }

    @Override
    public void addValue(T value) {
        allValueList.add(value);

        T lower = histogramIntervalTreeMap.navigableKeySet().lower(value);
        HistogramInterval<T> interval = histogramIntervalTreeMap.get(lower);
        if (interval.isAvailableValue(value)) {
            valueMap.get(interval).add(value);
        } else {
            outLinerValueList.add(value);
        }
    }

    private void checkIntersect(HistogramInterval<T> histogramInterval) {
        T lower = histogramIntervalTreeMap.navigableKeySet().lower(histogramInterval.leftValue);
        T higher = histogramIntervalTreeMap.navigableKeySet().higher(histogramInterval.leftValue);
        checkKeyForIntersect(histogramInterval, lower);
        checkKeyForIntersect(histogramInterval, higher);
    }

    private void checkKeyForIntersect(HistogramInterval<T> histogramInterval, T key) {
        if (key != null && histogramIntervalTreeMap.get(key).isIntersect(histogramInterval)) {
            throw new IntersectValueInHistogramIntervalsException(histogramInterval.toString() + " intersect with existing " + histogramIntervalTreeMap.get(key).toString() + " interval!");
        }
    }
}
