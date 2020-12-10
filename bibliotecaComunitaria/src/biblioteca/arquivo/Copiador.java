package biblioteca.arquivo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/* @author Luam */
public class Copiador {
    public static void copiarArquivo(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }
}
