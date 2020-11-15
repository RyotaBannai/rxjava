package basics.errorHandling.Stream;

import java.io.File;
import java.io.IOException;

public interface TextFileProcessor {

    // Using the annotation @SuppressWarnings("serial") makes the compiler shut up about a missing serialVersionUID
    @SuppressWarnings("serial")
    class MyException extends Exception {
    }

    default void analyze(String word) throws MyException {
        System.out.println(String.format("Analyzing... %s", word));
    }

    // implement this!
    void process(File directory) throws IOException, MyException;

}