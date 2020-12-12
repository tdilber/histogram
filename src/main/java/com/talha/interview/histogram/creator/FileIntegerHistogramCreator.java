package com.talha.interview.histogram.creator;

import com.talha.interview.histogram.Histogram;
import com.talha.interview.histogram.parser.IntegerHistogramIntervalParser;

/**
 * Created by tdilber at 12-Dec-20
 */
public class FileIntegerHistogramCreator extends BaseFileHistogramCreator<Integer> {

    private Histogram<Integer> histogram;

    public FileIntegerHistogramCreator(String fileName) {
        this(fileName, new Histogram<>());
    }

    public FileIntegerHistogramCreator(String fileName, Histogram<Integer> histogram) {
        super(fileName, new IntegerHistogramIntervalParser());
        this.histogram = histogram;
    }

    @Override
    public Histogram<Integer> create() throws Exception {
        createWithFile(histogram);
        return histogram;
    }

    @Override
    protected Integer parseValue(String line) {
        return Integer.parseInt(line.trim());
    }
}
