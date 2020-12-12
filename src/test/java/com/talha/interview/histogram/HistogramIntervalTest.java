package com.talha.interview.histogram;

import com.talha.interview.histogram.exception.IllegalValueInIntervalException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistogramIntervalTest {

    @Test
    void constructor() {
        assertDoesNotThrow(() -> new HistogramInterval<Integer>(false, false, 2, 3));
        assertThrows(IllegalValueInIntervalException.class, () -> new HistogramInterval<Integer>(false, false, 3, 2));
        assertThrows(IllegalValueInIntervalException.class, () -> new HistogramInterval<Integer>(false, false, 2, 2));
        assertDoesNotThrow(() -> new HistogramInterval<Double>(false, false, 2d, 3d));
        assertThrows(IllegalValueInIntervalException.class, () -> new HistogramInterval<Double>(false, false, 3d, 2d));
        assertThrows(IllegalValueInIntervalException.class, () -> new HistogramInterval<Double>(false, false, 3d, 3d));
    }

    @Test
    void isIntersect() {
        HistogramInterval<Integer> hi1 = HistogramInterval.of(false, false, 2, 3);
        HistogramInterval<Integer> hi2 = HistogramInterval.of(false, false, 4, 5);
        assertFalse(hi1.isIntersect(hi2));
        assertFalse(hi2.isIntersect(hi1));

        HistogramInterval<Integer> hi4 = HistogramInterval.of(false, false, 1, 5);
        HistogramInterval<Integer> hi5 = HistogramInterval.of(false, false, 2, 3);
        assertTrue(hi4.isIntersect(hi5));
        assertTrue(hi5.isIntersect(hi4));

        HistogramInterval<Integer> hi6 = HistogramInterval.of(false, true, 2, 3);
        HistogramInterval<Integer> hi7 = HistogramInterval.of(true, false, 3, 4);
        assertTrue(hi6.isIntersect(hi7));
        assertTrue(hi7.isIntersect(hi6));

        HistogramInterval<Integer> hi8 = HistogramInterval.of(false, false, 2, 3);
        HistogramInterval<Integer> hi9 = HistogramInterval.of(true, false, 3, 4);
        assertFalse(hi8.isIntersect(hi9));
        assertFalse(hi9.isIntersect(hi8));

        HistogramInterval<Integer> hi10 = HistogramInterval.of(false, true, 2, 3);
        HistogramInterval<Integer> hi11 = HistogramInterval.of(false, false, 3, 4);
        assertFalse(hi10.isIntersect(hi11));
        assertFalse(hi11.isIntersect(hi10));

        HistogramInterval<Integer> hi12 = HistogramInterval.of(false, false, 2, 3);
        HistogramInterval<Integer> hi13 = HistogramInterval.of(false, false, 3, 4);
        assertFalse(hi12.isIntersect(hi13));
        assertFalse(hi13.isIntersect(hi12));


        HistogramInterval<Integer> hi14 = HistogramInterval.of(true, true, 2, 3);
        HistogramInterval<Integer> hi15 = HistogramInterval.of(true, true, 1, 2);
        assertTrue(hi14.isIntersect(hi15));
        assertTrue(hi15.isIntersect(hi14));
    }

    @Test
    void isAvailableValue() {
        HistogramInterval<Integer> hi = HistogramInterval.of(true, false, 3, 8);
        // borders
        assertFalse(hi.isAvailableValue(8));
        assertTrue(hi.isAvailableValue(3));

        // inner
        assertTrue(hi.isAvailableValue(4));
        assertTrue(hi.isAvailableValue(7));

        // outer
        assertFalse(hi.isAvailableValue(1));
        assertFalse(hi.isAvailableValue(9));
    }
}