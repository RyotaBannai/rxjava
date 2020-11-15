package basics.errorHandling.Stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class StreamTextFileProcessor2 implements TextFileProcessor {
    @Override
    public void process(File directory) throws IOException, MyException {
    }

    public void process(String directoryPath) throws IOException, MyException {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath), 2).filter(getTextFile)) { // Files.list() や Files.find() などでも使える. // Stream は AutoCloseable の拡張
            for (Path file : (Iterable<Path>) paths::iterator) {
                try (Stream<String> words = Files.lines(file)
                        .map(line -> line.split("\\s"))
                        .flatMap(Stream::of)) {
                    for (String word : (Iterable<String>) words::iterator) {
                        this.analyze(word);
//                        throw new IOException("IOException!");
                    }
                }
            }
        }
    }

    private static final Predicate<Path> getTextFile =
            path -> path.toFile().isFile() && path.toString().endsWith(".txt");
}