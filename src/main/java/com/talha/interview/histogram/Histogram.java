package com.talha.interview.histogram;

import com.google.common.util.concurrent.AtomicDouble;
import com.google.common.util.concurrent.Monitor;
import com.talha.interview.histogram.exception.IntersectValueInHistogramIntervalsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by tdilber at 12-Dec-20
 * <p>
 * Histogram Class
 */
public class Histogram<T extends Number & Comparable> implements IHistogram<T> {
    private static final Logger log = LoggerFactory.getLogger(Histogram.class);

    /**
     * T => leftValue
     * HistogramInterval<T> => Interval.
     */
    private final TreeMap<T, HistogramInterval<T>> histogramIntervalTreeMap = new TreeMap<>();
    private final Map<HistogramInterval<T>, List<T>> valueMap = new HashMap<>();
    private Double mean = 0d, variance = 0d;
    private final List<T> outLinerValueList = new ArrayList<>();
    private final List<T> mappedValueList = new ArrayList<>();
    // Single mutex used
    private Monitor mutex = new Monitor();

    /**
     * Empty Constructor
     */
    public Histogram() {
        log.trace("Constructor Begin");
        log.trace("Constructor End");
    }

    /**
     * Array Interval Constructor
     * @param histogramIntervals Array Intervals
     */
    public Histogram(HistogramInterval<T>... histogramIntervals) {
        this(Arrays.asList(histogramIntervals));
    }

    /**
     * List Interval Constructor
     * @param histogramIntervals List Intervals
     */
    public Histogram(Collection<HistogramInterval<T>> histogramIntervals) {
        log.trace("Constructor Begin");
        for (HistogramInterval<T> histogramInterval : histogramIntervals) {
            addInterval(histogramInterval);
        }
        log.trace("Constructor End");
    }

    /**
     * Get mean current histogram
     * @return mean
     */
    @Override
    public Double mean() {
        mutex.enter();
        try {
            log.trace("mean method called");
            return mean;
        } finally {
            mutex.leave();
        }
    }

    /**
     * Get variance current histogram
     * @return variance
     */
    @Override
    public Double variance() {
        mutex.enter();
        try {
            log.trace("variance method called");
            if (mappedValueList.size() >= 2) {
                AtomicDouble atomicDouble = new AtomicDouble(0d);
                mappedValueList.parallelStream().forEach(v -> atomicDouble.addAndGet(Math.pow((v.doubleValue() - mean), 2)));
                variance = atomicDouble.get() / (mappedValueList.size() - 1);
            }
            return variance;
        } finally {
            mutex.leave();
        }
    }

    /**
     * Add new interval in Histogram
     *
     * @param newInterval interval to add
     */
    @Override
    public void addInterval(HistogramInterval<T> newInterval) { //Synchronized with mutex for Thread Safety
        mutex.enter();
        try {
            log.trace("addInterval method called");
            checkIntersect(newInterval);
            histogramIntervalTreeMap.put(newInterval.getLeftValue(), newInterval);
            log.info(newInterval.toString() + " New interval added");
            valueMap.put(newInterval, new ArrayList<>());
            checkOutLinerList(newInterval);
        } finally {
            mutex.leave();
        }
    }

    private void checkOutLinerList(HistogramInterval<T> newInterval) {
        for (T value : outLinerValueList) {
            if (newInterval.isAvailableValue(value)) {
                addValueInInterval(newInterval, value);
            }
        }

        for (T savedOutLiner : valueMap.get(newInterval)) {
            outLinerValueList.remove(savedOutLiner);
            log.info(savedOutLiner + " value move to new added " + newInterval.toString() + " interval");
        }
    }

    /**
     * Add new value in Histogram
     *
     * @param value value to add
     */
    @Override
    public void addValue(T value) { //Synchronized with mutex for Thread Safety
        mutex.enter();
        try {
            log.trace("addValue method called");

            T lower = histogramIntervalTreeMap.navigableKeySet().lower(value);
            HistogramInterval<T> interval = null;

            if (lower != null) {
                interval = histogramIntervalTreeMap.get(lower);

                // For an exceptional case
                if (interval.getRightValue().equals(value) && !interval.isRightContain() && histogramIntervalTreeMap.navigableKeySet().ceiling(value) != null) {
                    interval = histogramIntervalTreeMap.get(histogramIntervalTreeMap.navigableKeySet().ceiling(value));
                }
            }

            if (interval != null && interval.isAvailableValue(value)) {
                addValueInInterval(interval, value);
            } else {
                log.info(value + " value added in out liners list");
                outLinerValueList.add(value);
            }
        } finally {
            mutex.leave();
        }
    }

    private void addValueInInterval(HistogramInterval<T> interval, T value) {
        valueMap.get(interval).add(value);
        mappedValueList.add(value);
        calculateMean(value);
        log.info(value + " value added in " + interval.toString() + " interval");
    }

    private void calculateMean(T value) {
        int size = mappedValueList.size();
        mean = (mean * (size - 1) + value.doubleValue()) / size;
    }

    private void checkIntersect(HistogramInterval<T> histogramInterval) {
        T lower = histogramIntervalTreeMap.navigableKeySet().lower(histogramInterval.leftValue);
        T ceiling = histogramIntervalTreeMap.navigableKeySet().ceiling(histogramInterval.leftValue);
        T higher = histogramIntervalTreeMap.navigableKeySet().higher(histogramInterval.leftValue);
        checkKeyForIntersect(histogramInterval, lower);
        checkKeyForIntersect(histogramInterval, ceiling);
        checkKeyForIntersect(histogramInterval, higher);
    }

    private void checkKeyForIntersect(HistogramInterval<T> histogramInterval, T key) {
        if (key != null && histogramIntervalTreeMap.get(key).isIntersect(histogramInterval)) {
            String message = histogramInterval.toString() + " intersect with existing " + histogramIntervalTreeMap.get(key).toString() + " interval!";
            log.error(message);
            throw new IntersectValueInHistogramIntervalsException(message);
        }
    }

    /**
     * Generation String for Print Value Map
     *
     * @return generated result
     */
    public String toStringValueMap() {
        log.trace("toStringValueMap method called");
        StringBuilder stringBuilder = new StringBuilder();
        List<Map.Entry<HistogramInterval<T>, List<T>>> entries = new ArrayList<>(valueMap.entrySet());
        entries.sort(Comparator.comparingInt(o -> o.getKey().getLeftValue().intValue()));
        for (Map.Entry<HistogramInterval<T>, List<T>> entry : entries) {
            stringBuilder.append(entry.getKey().toString()).append(": ").append(entry.getValue().size()).append("\n");
        }

        return stringBuilder.toString();
    }

    /**
     * Generation String for Print Out Liners
     *
     * @return generated result
     */
    public String toStringOutLiners() {
        log.trace("toStringOutLiners method called");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("outliners: ");
        for (int i = 0; i < outLinerValueList.size(); i++) {
            stringBuilder.append(outLinerValueList.get(i));
            if (outLinerValueList.size() - 1 != i) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Get Histogram Out Liner Values List
     *
     * @return Out Liner Values
     */
    @Override
    public List<T> getOutLinerValueList() {
        return outLinerValueList;
    }

    /**
     * Get Histogram Mapped Values List
     *
     * @return Mapped Values List
     */
    @Override
    public List<T> getMappedValueList() {
        return mappedValueList;
    }

    /**
     * Get Histogram Interval Set
     *
     * @return Interval Set
     */
    @Override
    public Set<HistogramInterval<T>> getHistogramIntervalSet() {
        return valueMap.keySet();
    }

    /**
     * Get Histogram Interval Values Map
     *
     * @return Histogram Interval Values Map
     */
    @Override
    public Map<HistogramInterval<T>, List<T>> getValueMap() {
        return valueMap;
    }
}
