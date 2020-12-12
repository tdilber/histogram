package com.talha.interview.histogram.creator;

import com.talha.interview.histogram.Histogram;
import com.talha.interview.histogram.parser.IHistogramIntervalParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by tdilber at 12-Dec-20
 */
public abstract class BaseFileHistogramCreator<T extends Number & Comparable> implements IHistogramCreator<T> {
    protected String fileName;
    protected final IHistogramIntervalParser<T> histogramIntervalParser;

    protected BaseFileHistogramCreator(String fileName, IHistogramIntervalParser<T> histogramIntervalParser) {
        this.fileName = fileName;
        this.histogramIntervalParser = histogramIntervalParser;
    }

    protected void createWithFile(Histogram<T> histogram) throws IOException, URISyntaxException {
        List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource(fileName).toURI()));
        boolean readInterval = true;
        for (String line : lines) {
            if (line.equalsIgnoreCase("values")) {
                readInterval = false;
                continue;
            }
            if (readInterval) {
                histogram.addInterval(histogramIntervalParser.parse(line));
            } else {
                histogram.addValue(parseValue(line));
            }
        }
    }

    protected abstract T parseValue(String line);
}
