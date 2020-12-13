package com.talha.interview.histogram;

import com.talha.interview.histogram.exception.IllegalValueInIntervalException;
import com.talha.interview.histogram.exception.IntersectValueInHistogramIntervalsException;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    void concurrencyTest() throws Exception {
        final int CALCULATE_SIZE = 1000;
        int numberOfThreads = 10;

        ExecutorService serviceInterval = Executors.newFixedThreadPool(numberOfThreads);
        ExecutorService serviceValue = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(2 * numberOfThreads);
        final Histogram<Double> histogram = new Histogram<>();
        final Random random = new Random();
        for (int i = 0; i < numberOfThreads; i++) {
            serviceInterval.execute(() -> {
                double identity = Double.parseDouble(Thread.currentThread().getName().substring(Thread.currentThread().getName().length() - 1));
                identity *= 5;
                for (double j = 0; j < CALCULATE_SIZE; j++) {
                    histogram.addInterval(HistogramLeftContainInterval.of(j * 50d + identity, j * 50d + identity + 5d));
                }
                latch.countDown();
            });
            serviceValue.execute(() -> {
                for (double j = 0; j < CALCULATE_SIZE; j++) {
                    histogram.addValue(Math.abs(random.nextDouble()) * 51000);
                }
                latch.countDown();
            });
        }
        latch.await();
        System.out.println(histogram.toStringValueMap());
        System.out.println(histogram.toStringOutLiners());
        System.out.println("Mapped Values Count=> " + histogram.getMappedValueList().size());
        System.out.println("OutLiner Values Count=> " + histogram.getOutLinerValueList().size());
        assertEquals(numberOfThreads * CALCULATE_SIZE, histogram.getHistogramIntervalSet().size());
        assertEquals(numberOfThreads * CALCULATE_SIZE, histogram.getMappedValueList().size() + histogram.getOutLinerValueList().size());
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
    void addInterval2() {
        Histogram<Double> histogram = new Histogram<>();
        assertDoesNotThrow(() -> histogram.addInterval(HistogramInterval.of(false, false, 3d, 5d)));
        assertThrows(IntersectValueInHistogramIntervalsException.class, () -> histogram.addInterval(HistogramInterval.of(false, false, 3d, 5d)));
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

    @Test
    void getOutLinerValueList() {
        Histogram<Double> histogram = initHistogram();
        histogram.addValue(6d);
        histogram.addValue(7d);

        assertEquals(2, histogram.getOutLinerValueList().size());
        List<Double> listResult = new ArrayList<>();
        listResult.add(6d);
        listResult.add(7d);
        assertTrue(histogram.getOutLinerValueList().containsAll(listResult));
    }

    @Test
    void getMappedValueList() {
        Histogram<Double> histogram = initHistogram();

        assertEquals(5, histogram.getMappedValueList().size());
        List<Double> listResult = new ArrayList<>();
        listResult.add(1d);
        listResult.add(2d);
        listResult.add(3d);
        listResult.add(4d);
        listResult.add(5d);
        assertTrue(histogram.getMappedValueList().containsAll(listResult));
    }

    @Test
    void getHistogramIntervalSet() {
        Histogram<Double> histogram = initHistogram();

        assertEquals(1, histogram.getHistogramIntervalSet().size());
        assertEquals(HistogramInterval.of(true, true, 0d, 5d), histogram.getHistogramIntervalSet().iterator().next());
    }
}