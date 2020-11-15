package basics.errorHandling.Stream;

import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;


public class Example1 {
    public static void main(String[] args) {
        testStreamFor2();
    }

    private static void testNormalFor1() {
        NormalTextFileProcessor processor = new NormalTextFileProcessor();
        final File directory = new File("./assets");
        try {
            processor.process(directory);
        } catch (Exception e) {
            if (e instanceof IOException) {
                // e.printStackTrace();
                System.out.println("IOException");
            } else if (e instanceof TextFileProcessor.MyException) {
                System.out.println("MyException");
            } else {
                System.out.println("Unexpected Exception");
            }
        }
    }

    private static void testNormalFor2() {
        NormalTextFileProcessor2 processor = new NormalTextFileProcessor2();
        try {
            processor.process("./assets");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testStreamFor1() {
        final File directory = new File("./assets");
        StreamTextFileProcessor processor = new StreamTextFileProcessor();
        try {
            processor.process(directory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void testStreamFor2() {
        StreamTextFileProcessor2 processor = new StreamTextFileProcessor2();
        try {
            processor.process("./assets");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
