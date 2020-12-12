package com.talha.interview.histogram;

import com.talha.interview.histogram.exception.IllegalValueInIntervalException;
import com.talha.interview.histogram.exception.IntersectValueInHistogramIntervalsException;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistogramTest {

    @BeforeEach
    void beforeEach() {
        BasicConfigurator.configure();
    }

    private Histogram<Double> initHistogram() {
        Histogram<Double> histogram = new Histogram<>();
        histogram.addInterval(HistogramInterval.of(true, true, 0d, 5d));
        histogram.addValue(1d);
        histogram.addValue(2d);
        histogram.addValue(3d);
        histogram.addValue(4d);
        histogram.addValue(5d);
        return histogram;
    }

    @Test
    void mean() {
        Histogram<Double> histogram = initHistogram();

        assertEquals(3d, histogram.mean());
    }

    @Test
    void variance() {
        Histogram<Double> histogram = initHistogram();

        assertEquals(2.5d, histogram.variance());
    }

    @Test
    void addInterval() {
        Histogram<Double> histogram = new Histogram<>();
        assertDoesNotThrow(() -> histogram.addInterval(HistogramInterval.of(true, true, 0d, 5d)));
        assertThrows(IntersectValueInHistogramIntervalsException.class, () -> histogram.addInterval(HistogramInterval.of(true, true, 2d, 4d)));
        assertThrows(IllegalValueInIntervalException.class, () -> histogram.addInterval(HistogramInterval.of(true, true, 5d, 4d)));
        assertThrows(IllegalValueInIntervalException.class, () -> histogram.addInterval(HistogramInterval.of(true, true, 5d, 5d)));
        assertThrows(IntersectValueInHistogramIntervalsException.class, () -> histogram.addInterval(HistogramInterval.of(true, true, 5d, 6d)));
        assertThrows(IntersectValueInHistogramIntervalsException.class, () -> histogram.addInterval(HistogramInterval.of(true, true, -1d, 0d)));
        assertDoesNotThrow(() -> histogram.addInterval(HistogramInterval.of(true, false, -1d, 0d)));
        assertDoesNotThrow(() -> histogram.addInterval(HistogramInterval.of(false, true, 5d, 6d)));
        assertDoesNotThrow(() -> histogram.addInterval(HistogramInterval.of(false, true, 10d, 15d)));
    }

    @Test
    void addValue() {
        Histogram<Double> histogram = initHistogram();

        assertDoesNotThrow(() -> histogram.addValue(1.5d)); // inner value
        assertDoesNotThrow(() -> histogram.addValue(7d)); // outer value
    }

    @Test
    void toStringValueMap() {
        Histogram<Double> histogram = initHistogram();
        histogram.addInterval(HistogramLeftContainInterval.of(-5d, 0d));
        histogram.addInterval(HistogramInterval.of(false, false, 6d, 8d));

        assertEquals("[-5.0 , 0.0): 0\n[0.0 , 5.0]: 5\n(6.0 , 8.0): 0\n", histogram.toStringValueMap());
    }

    @Test
    void toStringOutLiners() {
        Histogram<Double> histogram = initHistogram();
        histogram.addValue(6d);
        histogram.addValue(7d);

        assertEquals("outliners: 6.0, 7.0", histogram.toStringOutLiners());
    }
}