package com.talha.interview.histogram.parser;

import com.talha.interview.histogram.HistogramInterval;
import com.talha.interview.histogram.exception.HistogramIntervalParserException;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerHistogramIntervalParserTest {

    @BeforeEach
    void beforeEach() {
        BasicConfigurator.configure();
    }

    @Test
    void parse() {
        IntegerHistogramIntervalParser integerHistogramInterval = new IntegerHistogramIntervalParser();
        assertThrows(HistogramIntervalParserException.class, () -> integerHistogramInterval.parse(null));
        assertThrows(HistogramIntervalParserException.class, () -> integerHistogramInterval.parse(""));
        assertThrows(HistogramIntervalParserException.class, () -> integerHistogramInterval.parse("  "));
        assertThrows(HistogramIntervalParserException.class, () -> integerHistogramInterval.parse("asdasd"));
        assertThrows(HistogramIntervalParserException.class, () -> integerHistogramInterval.parse("(asdasd, asdasd)"));
        assertThrows(HistogramIntervalParserException.class, () -> integerHistogramInterval.parse("(1, asdasd)"));
        assertThrows(HistogramIntervalParserException.class, () -> integerHistogramInterval.parse("(asd, 3)"));
        assertThrows(HistogramIntervalParserException.class, () -> integerHistogramInterval.parse("(1, 3"));
        assertThrows(HistogramIntervalParserException.class, () -> integerHistogramInterval.parse("1, 3)"));
        assertThrows(HistogramIntervalParserException.class, () -> integerHistogramInterval.parse("1, 3"));
        assertDoesNotThrow(() -> integerHistogramInterval.parse("(1, 3)"));
        assertDoesNotThrow(() -> integerHistogramInterval.parse("   (   1   ,   3   )   "));
        assertDoesNotThrow(() -> integerHistogramInterval.parse("(1, 3]"));
        assertDoesNotThrow(() -> integerHistogramInterval.parse("[1, 3)"));
        assertDoesNotThrow(() -> integerHistogramInterval.parse("[1, 4]"));
        assertDoesNotThrow(() -> integerHistogramInterval.parse("[1, 2]"));
        HistogramInterval<Integer> interval = integerHistogramInterval.parse("[1, 2]");
        assertTrue(interval.isLeftContain());
        assertTrue(interval.isRightContain());
        assertEquals(interval.getLeftValue(), 1);
        assertEquals(interval.getRightValue(), 2);
    }
}