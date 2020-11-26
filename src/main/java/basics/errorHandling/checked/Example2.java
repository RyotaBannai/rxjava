package basics.errorHandling.checked;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * ref: https://stackoverflow.com/questions/18198176/java-8-lambda-function-that-throws-exception/30246026#30246026
 */
public class Example2 {
    public static void main(String[] args) {
//        boolean fileWasCreated = unchecked(() -> new File("./assets/text/hello.txt").createNewFile());
        final File directory = new File("./assets/text/");
        Arrays.stream(directory.listFiles(getTextFile))
                .forEach(unchecked(file -> System.out.println(file)));
    }

    private static final FileFilter getTextFile =
            pathname -> pathname.getName().endsWith(".txt") && pathname.isFile();

    @FunctionalInterface
    public interface ThrowingConsumer<T> {
        void accept(T t) throws Exception;
    }

    public static <T> Consumer<T> unchecked(ThrowingConsumer<T> consumer) {
        return arg -> {
            try {
                consumer.accept(arg);
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        };
    }

    @FunctionalInterface
    public interface ThrowingSupplier<R> {
        R get() throws Exception;
    }

    public static <R> R unchecked(ThrowingSupplier<R> supplier) {
        try {
            return supplier.get();
        } catch (Exception error) {
            throw new RuntimeException(error);
        }

    }

    @FunctionalInterface
    public interface ThrowingRunnable {
        void run() throws Exception;
    }

    public static void unchecked(ThrowingRunnable runnable) {
        try {
            runnable.run();
        } catch (Exception error) {
            throw new RuntimeException();
        }
    }
}
