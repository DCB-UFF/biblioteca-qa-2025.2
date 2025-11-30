package test.java.biblioteca.pessoas;

import biblioteca.livros.Livro;
import biblioteca.pessoas.Autor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AutorTest {

    private Path tempDir;
    private String pathComBarra;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("teste_autores");

        pathComBarra = tempDir.toAbsolutePath().toString() + File.separator;
    }

    @AfterEach
    void tearDown() {
        try (Stream<Path> walk = Files.walk(tempDir)) {
            walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            // Ignora erro de deleção
        }
    }

    @Test
    @DisplayName("Deve testar todos os Construtores e Getters/Setters")
    void testEntidadeAutor() {
        Autor a1 = new Autor("J.K. Rowling", "UK");
        assertEquals("J.K. Rowling", a1.getNome());
        assertEquals("UK", a1.getPais());

        Autor a2 = new Autor("Tolkien");
        assertEquals("Tolkien", a2.getNome());
        assertNull(a2.getPais());

        Autor a3 = new Autor();
        a3.cadastro("George Martin", "USA");
        assertEquals("George Martin", a3.getNome());
        assertEquals("USA", a3.getPais());

        a3.setNome("G.R.R. Martin");
        a3.setPais("Estados Unidos");
        assertEquals("G.R.R. Martin", a3.getNome());
        assertEquals("Estados Unidos", a3.getPais());
    }

    @Test
    @DisplayName("Deve manipular a lista de livros do autor")
    void testListaLivrosAutor() {
        Autor a = new Autor("Machado", "Brasil");

        assertNotNull(a.getLivrosAutor());
        assertTrue(a.getLivrosAutor().isEmpty());

        a.addLivro(null);
        assertEquals(1, a.getLivrosAutor().size());

        ArrayList<Livro> novaLista = new ArrayList<>();
        a.setLivrosAutor(novaLista);
        assertEquals(0, a.getLivrosAutor().size());
    }

    @Test
    @DisplayName("Deve escrever autor no arquivo CSV")
    void testEscreverAutor() throws IOException {
        Autor a = new Autor("Stephen King", "Maine");

        Autor.escreverAutor(a, pathComBarra);

        Path arquivo = tempDir.resolve("autores.csv");
        assertTrue(Files.exists(arquivo), "O arquivo autores.csv deveria ter sido criado");

        String conteudo = Files.readString(arquivo);
        assertTrue(conteudo.contains("Stephen King,Maine"));
    }

    @Test
    @DisplayName("Deve ler autores do arquivo (pulando cabeçalho)")
    void testLeitorAutores() throws IOException {
        Path arquivo = tempDir.resolve("autores.csv");

        String csvContent = "Nome,Pais\n" +
                "Agatha Christie,UK\n" +
                "Clarice Lispector,Brasil";

        Files.writeString(arquivo, csvContent);

        ArrayList<Autor> lista = Autor.leitorAutores(pathComBarra);

        assertNotNull(lista);
        assertEquals(2, lista.size(), "Deveria ter lido 2 autores");

        assertEquals("Agatha Christie", lista.get(0).getNome());
        assertEquals("UK", lista.get(0).getPais());

        assertEquals("Clarice Lispector", lista.get(1).getNome());
        assertEquals("Brasil", lista.get(1).getPais());
    }
}