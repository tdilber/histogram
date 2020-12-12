package com.talha.interview.histogram.parser;

import com.talha.interview.histogram.HistogramInterval;
import com.talha.interview.histogram.exception.HistogramIntervalParserException;

/**
 * Created by tdilber at 12-Dec-20
 */
public interface IHistogramIntervalParser<T extends Comparable> {
    HistogramInterval<T> parse(CharSequence text) throws HistogramIntervalParserException;
}
