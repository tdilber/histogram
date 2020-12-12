package com.talha.interview;

import com.talha.interview.histogram.Histogram;
import com.talha.interview.histogram.HistogramLeftContainInterval;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tdilber at 12-Dec-20
 */
public class HistogramApp {
    private final static Logger log = LoggerFactory.getLogger(HistogramApp.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Histogram<Double> histogram = new Histogram<>();
        histogram.addInterval(HistogramLeftContainInterval.of(0d, 1.1d));
        histogram.addInterval(HistogramLeftContainInterval.of(3d, 4.1d));
        histogram.addInterval(HistogramLeftContainInterval.of(8.5d, 8.7d));
        histogram.addInterval(HistogramLeftContainInterval.of(31.5d, 41.27d));

        histogram.addValue(40.1d);
        histogram.addValue(8.1d);
        histogram.addValue(8.2d);
        histogram.addValue(30d);
        histogram.addValue(31.51d);
        histogram.addValue(1d);
        histogram.addValue(41.27d);

        System.out.println(histogram.toStringValueMap());
        System.out.println(histogram.toStringOutLiners());
        System.out.println("sample mean: " + histogram.mean());
        System.out.println("sample variance: " + histogram.variance());
    }
}
