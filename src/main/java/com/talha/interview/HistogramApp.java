package com.talha.interview;

import com.talha.interview.histogram.Histogram;
import com.talha.interview.histogram.HistogramLeftContainInterval;
import com.talha.interview.histogram.creator.FileDoubleHistogramCreator;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tdilber at 12-Dec-20
 */
public class HistogramApp {
    private final static Logger log = LoggerFactory.getLogger(HistogramApp.class);

    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();

        mailExample();
        readWithFile();
    }

    private static void readWithFile() throws Exception {
        FileDoubleHistogramCreator fileDoubleHistogramCreator = new FileDoubleHistogramCreator("histogram1.txt");
        Histogram<Double> histogram = fileDoubleHistogramCreator.create();

        log.info(histogram.toStringValueMap());
        log.info(histogram.toStringOutLiners());
        log.info("sample mean: " + histogram.mean());
        log.info("sample variance: " + histogram.variance());
    }

    private static void mailExample() {
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

        log.info(histogram.toStringValueMap());
        log.info(histogram.toStringOutLiners());
        log.info("sample mean: " + histogram.mean());
        log.info("sample variance: " + histogram.variance());
    }
}
