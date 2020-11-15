package basics.errorHandling.Stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class NormalTextFileProcessor implements TextFileProcessor {
    @Override
    public void process(File directory) throws IOException, MyException { // file を指定するのは呼び出す側なので、exception を伝播させる.
        // listFiles を使うときは、NullPointerException が出ても適切に処理できるように Objects.requireNonNull でラップ
        for (File file : Objects.requireNonNull(
                directory.listFiles(this.getTextFile))) { // new FileFilter() の代わりに lambda を使用.
            try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
                String line = null;
                while ((line = reader.readLine()) != null) { // 一行づつ読む
                    for (String word : line.split("\\s")) { // split by space
                        this.analyze(word);
                    }
                }
            }
        }
    }

    private final FileFilter getTextFile =
            pathname -> pathname.getName().endsWith(".txt") && pathname.isFile(); // filename の型は File
}
