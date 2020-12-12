package com.talha.interview.histogram.parser;

import com.talha.interview.histogram.HistogramInterval;
import com.talha.interview.histogram.exception.HistogramIntervalParserException;

import java.util.List;

/**
 * Created by tdilber at 12-Dec-20
 */
public class IntegerHistogramIntervalParser extends BaseHistogramIntervalParser<Integer> {
    @Override
    public HistogramInterval<Integer> parse(CharSequence charSequence) throws HistogramIntervalParserException {
        List<String> values = splitParseParts(charSequence);
        int firstVal, secondVal;
        try {
            firstVal = Integer.parseInt(values.get(2).trim());
            secondVal = Integer.parseInt(values.get(3).trim());
        } catch (NumberFormatException e) {
            throw new HistogramIntervalParserException("Histogram Interval Text not valid to Parse! " + e.getMessage(), e);
        }

        return HistogramInterval.of(values.get(0).equals("["), values.get(1).equals("]"), firstVal, secondVal);
    }
}
