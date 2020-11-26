package basics.errorHandling.types;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Exmple1 {
    public static void main(String[] args) {
        test((t) -> {
            throw new IOException("test function exception");
        });
//
//        final List<String> list = Arrays.asList("A", "B", "C");
//        final ThrowingConsumer<String> throwingConsumer = aps -> {
//            throw new Exception("asdas");
//        };
//        list.forEach(throwingConsumer);
    }

    private static void test(CheckedFunction f) {
        try {
            f.apply("t");
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    @FunctionalInterface
    public interface CheckedFunction<T, R> {
        R apply(T t) throws IOException;
    }

    @FunctionalInterface
    public interface ThrowingConsumer<T> extends Consumer<T> {
        @Override
        default void accept(final T elem) {
            try {
                acceptThrows(elem);
            } catch (final Exception e) {
                System.out.println("handling exception");
                throw new RuntimeException(e);
            }
        }
        void acceptThrows(T elem) throws Exception;
    }
}
