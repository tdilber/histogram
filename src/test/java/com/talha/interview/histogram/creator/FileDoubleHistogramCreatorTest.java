package com.talha.interview.histogram.creator;

import com.talha.interview.histogram.Histogram;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileDoubleHistogramCreatorTest {

    @BeforeEach
    public void testBeforeEach() {
        BasicConfigurator.configure();
    }

    @Test
    public void testCreate() throws Exception {
        FileDoubleHistogramCreator fileDoubleHistogramCreator = new FileDoubleHistogramCreator("histogram1.txt");
        Histogram<Double> histogram = fileDoubleHistogramCreator.create();
        assertEquals("(1.0 , 2.0): 0\n(2.0 , 4.0): 1\n[4.0 , 5.0): 1\n", histogram.toStringValueMap());
        assertEquals("outliners: 1.0, 2.0, 5.0, 12.0, 13.0", histogram.toStringOutLiners());
    }
}