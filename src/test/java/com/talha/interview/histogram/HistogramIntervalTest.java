package com.talha.interview.histogram;

import com.talha.interview.histogram.exception.IllegalValueInIntervalException;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HistogramIntervalTest {

    @BeforeEach
    public void testBeforeEach() {
        BasicConfigurator.configure();
    }

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> HistogramInterval.of(false, false, 2, 3));
        assertThrows(IllegalValueInIntervalException.class, () -> HistogramInterval.of(false, false, 3, 2));
        assertThrows(IllegalValueInIntervalException.class, () -> HistogramInterval.of(false, false, 2, 2));
        assertDoesNotThrow(() -> HistogramInterval.of(false, false, 2d, 3d));
        assertThrows(IllegalValueInIntervalException.class, () -> HistogramInterval.of(false, false, 3d, 2d));
        assertThrows(IllegalValueInIntervalException.class, () -> HistogramInterval.of(false, false, 3d, 3d));
    }

    @Test
    public void testIsIntersect() {
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

        HistogramInterval<Integer> hi16 = HistogramInterval.of(false, false, 3, 5);
        HistogramInterval<Integer> hi17 = HistogramInterval.of(false, false, 3, 5);
        assertTrue(hi16.isIntersect(hi17));
        assertTrue(hi17.isIntersect(hi16));
    }

    @Test
    public void testIsAvailableValue() {
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

    @Test
    public void testOf() {
        HistogramInterval<Double> interval = HistogramInterval.of(true, false, 5d, 10d);
        assertTrue(interval.isLeftContain());
        assertFalse(interval.isRightContain());
        assertEquals(5d, interval.getLeftValue());
        assertEquals(10d, interval.getRightValue());
    }

    @Test
    public void testIsLeftContain() {
        HistogramInterval<Double> interval = HistogramInterval.of(false, false, 5d, 10d);
        assertFalse(interval.isLeftContain());
    }

    @Test
    public void testIsRightContain() {
        HistogramInterval<Double> interval = HistogramInterval.of(true, true, 5d, 10d);
        assertTrue(interval.isRightContain());
    }

    @Test
    public void testGetLeftValue() {
        HistogramInterval<Double> interval = HistogramInterval.of(true, false, 2.3d, 10d);
        assertEquals(2.3d, interval.getLeftValue());
    }

    @Test
    public void testGetRightValue() {
        HistogramInterval<Double> interval = HistogramInterval.of(true, false, 5d, 7.7d);
        assertEquals(7.7d, interval.getRightValue());
    }

    @Test
    public void testToString() {
        HistogramInterval<Double> interval = HistogramInterval.of(true, false, 5d, 10d);
        assertEquals("[5.0 , 10.0)", interval.toString());
    }

    @Test
    public void testEquals() {
        HistogramInterval<Double> baseHI = HistogramInterval.of(true, false, 5d, 10d);
        HistogramInterval<Double> hi2 = HistogramInterval.of(true, false, 5d, 10d);
        assertEquals(hi2, baseHI);
        assertEquals(baseHI, hi2);
        HistogramInterval<Double> hi3 = HistogramInterval.of(false, false, 5d, 10d);
        assertNotEquals(baseHI, hi3);
        assertNotEquals(hi3, baseHI);
        HistogramInterval<Double> hi4 = HistogramInterval.of(true, true, 5d, 10d);
        assertNotEquals(baseHI, hi4);
        assertNotEquals(hi4, baseHI);
        HistogramInterval<Double> hi5 = HistogramInterval.of(true, false, 6d, 10d);
        assertNotEquals(baseHI, hi5);
        assertNotEquals(hi5, baseHI);
        HistogramInterval<Double> hi6 = HistogramInterval.of(true, false, 5d, 7d);
        assertNotEquals(baseHI, hi6);
        assertNotEquals(hi6, baseHI);
    }
}