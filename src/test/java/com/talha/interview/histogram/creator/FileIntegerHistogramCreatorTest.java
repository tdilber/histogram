package com.talha.interview.histogram.creator;

import com.talha.interview.histogram.Histogram;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileIntegerHistogramCreatorTest {

    @BeforeEach
    void beforeEach() {
        BasicConfigurator.configure();
    }

    @Test
    void create() throws Exception {
        FileIntegerHistogramCreator fileIntegerHistogramCreator = new FileIntegerHistogramCreator("histogram1.txt");
        Histogram<Integer> histogram = fileIntegerHistogramCreator.create();
        assertEquals("(1 , 2): 0\n(2 , 4): 1\n[4 , 5): 1\n", histogram.toStringValueMap());
        assertEquals("outliners: 1, 2, 5, 12, 13", histogram.toStringOutLiners());
    }
}