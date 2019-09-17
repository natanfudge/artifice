package artificemc.artifice.api.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class FileUtils {
    private FileUtils() {}

    public static String readString(Path path) throws IOException {
        return Files.lines(path).collect(Collectors.joining("\n"));
    }

    public static String readInput(InputStream input) {
        try(Scanner scanner = new Scanner(input)) {
            return scanner.useDelimiter("\\A").next();
        }
    }

    @FunctionalInterface
    public interface IOConsumer<T> extends Consumer<T> {
        void acceptThrows(T t) throws IOException;
        default void accept(T t) {
            try { acceptThrows(t); } catch(IOException e) { throw new RuntimeException(e); }
        }
    }
}
