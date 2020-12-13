package com.talha.interview.histogram.parser;

import com.talha.interview.histogram.HistogramInterval;
import com.talha.interview.histogram.exception.HistogramIntervalParserException;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoubleHistogramIntervalParserTest {

    @BeforeEach
    public void testBeforeEach() {
        BasicConfigurator.configure();
    }

    @Test
    public void testParse() {
        DoubleHistogramIntervalParser doubleHistogramIntervalParser = new DoubleHistogramIntervalParser();
        assertThrows(HistogramIntervalParserException.class, () -> doubleHistogramIntervalParser.parse(null));
        assertThrows(HistogramIntervalParserException.class, () -> doubleHistogramIntervalParser.parse(""));
        assertThrows(HistogramIntervalParserException.class, () -> doubleHistogramIntervalParser.parse(")"));
        assertThrows(HistogramIntervalParserException.class, () -> doubleHistogramIntervalParser.parse("  "));
        assertThrows(HistogramIntervalParserException.class, () -> doubleHistogramIntervalParser.parse("asdasd"));
        assertThrows(HistogramIntervalParserException.class, () -> doubleHistogramIntervalParser.parse("(asdasd, asdasd)"));
        assertThrows(HistogramIntervalParserException.class, () -> doubleHistogramIntervalParser.parse("(1.23, asdasd)"));
        assertThrows(HistogramIntervalParserException.class, () -> doubleHistogramIntervalParser.parse("(asd, 1.232)"));
        assertThrows(HistogramIntervalParserException.class, () -> doubleHistogramIntervalParser.parse("(1.23, 1.232"));
        assertThrows(HistogramIntervalParserException.class, () -> doubleHistogramIntervalParser.parse("1.23, 1.232)"));
        assertThrows(HistogramIntervalParserException.class, () -> doubleHistogramIntervalParser.parse("1.23, 1.232"));
        assertDoesNotThrow(() -> doubleHistogramIntervalParser.parse("(1.23, 1.232)"));
        assertDoesNotThrow(() -> doubleHistogramIntervalParser.parse("   (   1.23   ,   1.232    )    "));
        assertDoesNotThrow(() -> doubleHistogramIntervalParser.parse("(1.23, 1.232]"));
        assertDoesNotThrow(() -> doubleHistogramIntervalParser.parse("[1.23, 1.232)"));
        assertDoesNotThrow(() -> doubleHistogramIntervalParser.parse("[1.1, 1.2]"));
        assertDoesNotThrow(() -> doubleHistogramIntervalParser.parse("[1, 2]"));
        assertDoesNotThrow(() -> doubleHistogramIntervalParser.parse("(     6    ,   9)"));
        HistogramInterval<Double> interval = doubleHistogramIntervalParser.parse("[1, 2]");
        assertTrue(interval.isLeftContain());
        assertTrue(interval.isRightContain());
        assertEquals(interval.getLeftValue(), 1d);
        assertEquals(interval.getRightValue(), 2d);
    }
}