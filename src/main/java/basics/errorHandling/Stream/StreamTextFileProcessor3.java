package basics.errorHandling.Stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class StreamTextFileProcessor3 implements TextFileProcessor {
    @Override
    public void process(File directory) throws IOException, MyException {
    }

    public void process(String directoryPath) throws IOException, MyException {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath), 2)) {
            paths.filter(getTextFile) // ファイルひとつずつ処理
                    .flatMap(file -> ConsumerWrapper.wrapConsumer(Files.lines(file)))
                    .flatMap(line -> Stream.of(line.split("\\s")))
                    .forEach(wrapConsumer(this::analyze));
        }
    }

    private static final Predicate<Path> getTextFile =
            path -> path.toFile().isFile() && path.toString().endsWith(".txt");

    @FunctionalInterface
    interface ConsumerWrapper<T> extends Consumer<T> {

        void acceptOrigin(T t) throws Exception;

        @Override
        default void accept(T t) {
            try {
                acceptOrigin(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        static <T, R> ConsumerWrapper<T> wrapConsumer(ConsumerWrapper<T> f) {
            return f;
        }
    }

}