package com.talha.interview.histogram.parser;

import com.talha.interview.histogram.HistogramInterval;
import com.talha.interview.histogram.exception.HistogramIntervalParserException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoubleHistogramIntervalParserTest {

    @Test
    void parse() {
        DoubleHistogramIntervalParser doubleHistogramIntervalParser = new DoubleHistogramIntervalParser();
        assertThrows(HistogramIntervalParserException.class, () -> doubleHistogramIntervalParser.parse(null));
        assertThrows(HistogramIntervalParserException.class, () -> doubleHistogramIntervalParser.parse(""));
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
        HistogramInterval<Double> interval = doubleHistogramIntervalParser.parse("[1, 2]");
        assertTrue(interval.isLeftContain());
        assertTrue(interval.isRightContain());
        assertEquals(interval.getLeftValue(), 1d);
        assertEquals(interval.getRightValue(), 2d);
    }
}