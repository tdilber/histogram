package com.talha.interview.histogram.creator;

import com.talha.interview.histogram.Histogram;
import com.talha.interview.histogram.parser.DoubleHistogramIntervalParser;

/**
 * Created by tdilber at 12-Dec-20
 */
public class FileDoubleHistogramCreator extends BaseFileHistogramCreator<Double> {

    private Histogram<Double> histogram;

    public FileDoubleHistogramCreator(String fileName) {
        this(fileName, new Histogram<>());
    }

    public FileDoubleHistogramCreator(String fileName, Histogram<Double> histogram) {
        super(fileName, new DoubleHistogramIntervalParser());
        this.histogram = histogram;
    }

    @Override
    public Histogram<Double> create() throws Exception {
        createWithFile(histogram);
        return histogram;
    }

    @Override
    protected Double parseValue(String line) {
        return Double.parseDouble(line.trim());
    }
}
