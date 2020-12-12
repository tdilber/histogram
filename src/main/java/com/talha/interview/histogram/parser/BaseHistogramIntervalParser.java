package com.talha.interview.histogram.parser;

import com.talha.interview.histogram.exception.HistogramIntervalParserException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tdilber at 12-Dec-20
 */
public abstract class BaseHistogramIntervalParser<T extends Comparable> implements IHistogramIntervalParser<T> {

    protected List<String> splitParseParts(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() == 0 || charSequence.toString().trim().isEmpty()) {
            throw new HistogramIntervalParserException("Text is Empty!");
        }
        String text = charSequence.toString();
        text = text.trim();

        List<String> values = new ArrayList<>();
        String beginChar = text.substring(0, 1);
        String lastChar = text.substring(text.length() - 1);
        values.add(beginChar);
        values.add(lastChar);


        text = text.substring(1, text.length() - 1);
        String[] split = text.split(",");

        if ((!beginChar.equals("(") && !beginChar.equals("[")) || (!lastChar.equals(")") && !lastChar.equals("]")) || split.length != 2) {
            throw new HistogramIntervalParserException("Histogram Interval Text not valid to Parse!");
        }
        values.add(split[0]);
        values.add(split[1]);
        return values;
    }
}
