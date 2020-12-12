package com.talha.interview.histogram.parser;

import com.talha.interview.histogram.HistogramInterval;
import com.talha.interview.histogram.exception.HistogramIntervalParserException;

import java.util.List;

/**
 * Created by tdilber at 12-Dec-20
 */
public class DoubleHistogramIntervalParser extends BaseHistogramIntervalParser<Double> {
    @Override
    public HistogramInterval<Double> parse(CharSequence charSequence) throws HistogramIntervalParserException {
        List<String> values = splitParseParts(charSequence);
        Double firstVal, secondVal;
        try {
            firstVal = Double.parseDouble(values.get(2).trim());
            secondVal = Double.parseDouble(values.get(3).trim());
        } catch (NumberFormatException e) {
            throw new HistogramIntervalParserException("Histogram Interval Text not valid to Parse! " + e.getMessage(), e);
        }

        return HistogramInterval.of(values.get(0).equals("["), values.get(1).equals("]"), firstVal, secondVal);
    }
}
