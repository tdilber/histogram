package com.talha.interview;

import com.talha.interview.histogram.Histogram;
import com.talha.interview.histogram.HistogramLeftContainInterval;
import com.talha.interview.histogram.creator.FileDoubleHistogramCreator;
import com.talha.interview.histogram.exception.HistogramIntervalParserException;
import com.talha.interview.histogram.parser.DoubleHistogramIntervalParser;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * Created by tdilber at 12-Dec-20
 */
public class HistogramApp {
    private final static Logger log = LoggerFactory.getLogger(HistogramApp.class);

    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();

        System.out.println("Created By Talha Dilber");
        Scanner scanner = new Scanner(System.in);
        int options = 0;
        while (options != 4) {
            System.out.println("We have 3 Option for our application");
            System.out.println("1. Start Mail Example");
            System.out.println("2. Read \"histogram1.txt\" in resource folder");
            System.out.println("3. Enter values in console");
            System.out.println("4. Exit");
            System.out.print("Please enter your options => ");

            while (options == 0) {
                try {
                    int result = Integer.parseInt(scanner.next());
                    if (result < 1 || result > 4) {
                        System.out.println("Please enter number between 1 and 4.");
                    } else {
                        options = result;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter just number.");
                }
            }

            switch (options) {
                case 1:
                    mailExample();
                    break;
                case 2:
                    readWithFile();
                    break;
                case 3:
                    consoleCommander();
                    break;
            }
            switch (options) {
                case 1:
                case 2:
                case 3:
                    options = 0;
                    System.out.println("\n---------------------------------------\n\n");
            }
        }

        System.out.println("\nThank you!");
        System.out.println("\nCreated by Talha Dilber");
        System.out.println("Phone Number: 534 069 8484");
        System.out.println("Email: dilber.talha@gmail.com");
    }

    private static void mailExample() {
        Histogram<Double> histogram = new Histogram<>();
        histogram.addInterval(HistogramLeftContainInterval.of(0d, 1.1d));
        histogram.addInterval(HistogramLeftContainInterval.of(3d, 4.1d));
        histogram.addInterval(HistogramLeftContainInterval.of(8.5d, 8.7d));
        histogram.addInterval(HistogramLeftContainInterval.of(31.5d, 41.27d));

        histogram.addValue(40.1d);
        histogram.addValue(8.1d);
        histogram.addValue(8.2d);
        histogram.addValue(30d);
        histogram.addValue(31.51d);
        histogram.addValue(1d);
        histogram.addValue(41.27d);


        printResult(histogram);
    }

    private static void consoleCommander() {
        Scanner scanner = new Scanner(System.in);

        Histogram<Double> histogram = new Histogram<>();
        DoubleHistogramIntervalParser histogramIntervalParser = new DoubleHistogramIntervalParser();
        boolean enterInterval = true;

        while (enterInterval) {
            boolean valueEntered = false;

            while (!valueEntered) {
                try {
                    System.out.print("Please enter interval => ");
                    histogram.addInterval(histogramIntervalParser.parse(scanner.next()));
                    valueEntered = true;
                } catch (HistogramIntervalParserException e) {
                    System.out.println("Histogram Parse Error! Please enter like (1.1, 2.3) or [1.1, 2.3) or (1.1, 2.3] or [1.1, 2.3]");
                }
            }

            boolean nextIntervalRequest = false;
            while (!nextIntervalRequest) {
                System.out.print("Do you want to enter new interval (Y / N) => ");
                String next = scanner.next();
                if (next.equalsIgnoreCase("y") || next.equalsIgnoreCase("n")) {
                    nextIntervalRequest = true;
                    enterInterval = next.equalsIgnoreCase("y");
                } else {
                    System.out.println("Please enter correctly!");
                }
            }
        }

        boolean enterValue = true;

        while (enterValue) {
            boolean valueEntered = false;

            while (!valueEntered) {
                try {
                    System.out.print("Please enter value => ");
                    histogram.addValue(Double.parseDouble(scanner.next()));
                    valueEntered = true;
                } catch (NumberFormatException e) {
                    System.out.println("Double Parse Error!");
                }
            }

            boolean nextValueRequest = false;
            while (!nextValueRequest) {
                System.out.print("Do you want to enter new value (Y / N) => ");
                String next = scanner.next();
                if (next.equalsIgnoreCase("y") || next.equalsIgnoreCase("n")) {
                    nextValueRequest = true;
                    enterValue = next.equalsIgnoreCase("y");
                } else {
                    System.out.println("Please enter correctly!");
                }
            }
        }

        printResult(histogram);
    }

    private static void readWithFile() throws Exception {
        FileDoubleHistogramCreator fileDoubleHistogramCreator = new FileDoubleHistogramCreator("histogram1.txt");
        Histogram<Double> histogram = fileDoubleHistogramCreator.create();

        printResult(histogram);
    }

    private static void printResult(Histogram<Double> histogram) {
        System.out.println("\n");
        System.out.println(histogram.toStringValueMap());
        System.out.println(histogram.toStringOutLiners());
        System.out.println("sample mean: " + histogram.mean());
        System.out.println("sample variance: " + histogram.variance());
    }
}
