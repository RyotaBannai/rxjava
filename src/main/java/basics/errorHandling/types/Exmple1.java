package basics.errorHandling.types;

import java.io.IOException;

public class Exmple1 {
    public static void main(String[] args) {
        @FunctionalInterface
        public interface CheckedFunction<T, R> {
            R apply(T t) throws IOException;
        }
    }
}
