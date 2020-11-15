package basics.errorHandling.Stream;


import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * forEach 内で try-catch で補足しないといけない理由は
 * Streams API で用いられる Function, Consumer を始めとする functional interfaces のメソッドが、検査例外をスローする定義になっていないため.
 */
public class StreamTextFileProcessor implements TextFileProcessor {
    @Override
    public void process(File directory) throws IOException, RuntimeException {
        Files.list(directory.toPath())
                .filter(getTextFile)
                .forEach(file -> {
                    try (Stream<String> lines = Files.lines(file)) {
                        lines.forEach(line -> Stream.of(line.split("\\s"))
                                .forEach(word -> {
                                    try {
                                        this.analyze(word);
                                    } catch (MyException e) { // Exception も検査例外なので try-catch ラップが必要.
                                        throw new RuntimeException(e);
                                    }
                                })
                        );
                    } catch (IOException e) {
                        // java.nio.file.Files#lines メソッドは IOException をスローするが、
                        // これを forEach から外にスローできないので、forEach 内で try-catch して非検査例外でラップして再スローせざるを得ない
                        throw new UncheckedIOException(e); // IOException として throw される.
                    }
                });
    }


    private static final Predicate<Path> getTextFile =
            path -> path.toFile().isFile() && path.toString().endsWith(".txt");
}