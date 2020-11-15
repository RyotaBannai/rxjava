package basics.errorHandling.Stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NormalTextFileProcessor2 implements TextFileProcessor {
    @Override
    public void process(File directory) throws IOException, MyException {
    }

    public void process(String directoryPath) throws IOException, MyException {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) { // walk get file and directory recursively.
            for (Path file : paths.filter(this.getTextFile).collect(Collectors.toList())) {
                try (BufferedReader reader = Files.newBufferedReader(file)) {
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        for (String word : line.split("\\s")) {
                            this.analyze(word);
//                            throw new IOException("IOException!"); // test Exception caught properly.
                        }
                    }
                }
            }
        }
    }

    // path -> sun.nio.fs.UnixPath
    // Path toFile -> File, File toPath -> Path
    private static final Predicate<Path> getTextFile =
            path -> path.toFile().isFile() && path.toString().endsWith(".txt");
}