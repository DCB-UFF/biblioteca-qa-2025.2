package biblioteca.livros;

import biblioteca.livros.Acervo;
import biblioteca.biblioteca.Unidade;
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
import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LivroTest {

    private Path tempDir;
    private String pathComBarra;
    private Acervo acervo;
    private Unidade unidade;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("teste_livro_entity");
        pathComBarra = tempDir.toAbsolutePath().toString() + File.separator;

        criarArquivo("emprestimos.csv", "CPF,ISBN,DataE,DataD\n");
        criarArquivo("livros.csv", "Autor,Titulo,Pag,ISBN,Gen,Ed,Emp,Pais\n");

        acervo = new Acervo();
        unidade = new Unidade("1", "Teste", "R", "B", "C", "C", "UF");
        unidade.setPath(pathComBarra);
        unidade.setAcervo(acervo);
    }

    private void criarArquivo(String nome, String conteudo) throws IOException {
        Files.writeString(tempDir.resolve(nome), conteudo);
    }

    @AfterEach
    void tearDown() {
        try (Stream<Path> walk = Files.walk(tempDir)) {
            walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {}
    }

    @Test
    @DisplayName("Cobre todos os Setters que faltavam")
    void testSetters() {
        Autor autor = new Autor("Autor Original", "Pais A");
        Livro l = new Livro(autor, "Titulo A", 100, "ISBN-A", "Gen A", "Ed A", false);

        l.setTitulo("Titulo B");
        assertEquals("Titulo B", l.getTitulo());

        Autor autorB = new Autor("Autor B", "Pais B");
        l.setAutor(autorB);
        assertEquals("Autor B", l.getAutor().getNome());

        l.setNumPaginas(500);
        assertEquals(500, l.getNumPaginas());

        l.setISBN("ISBN-B");
        assertEquals("ISBN-B", l.getISBN());

        l.setGenero("Gen B");
        assertEquals("Gen B", l.getGenero());

        l.setEditora("Ed B");
        assertEquals("Ed B", l.getEditora());

        l.setEstaEmprestado(true);
        assertTrue(l.isEstaEmprestado());
    }

    @Test
    @DisplayName("Testa emprestar() e devolver() na classe Livro")
    void testEmprestarDevolver() {
        Autor autor = new Autor("Autor X");
        Livro l = new Livro(autor, "Livro X", 200, "ISBN-X", "Geral", "Ed", false);

        l.emprestar(unidade, acervo);
        assertTrue(l.isEstaEmprestado(), "Deveria mudar status para true");

        l.devolver(unidade, acervo);
        assertFalse(l.isEstaEmprestado(), "Deveria mudar status para false");
    }

    @Test
    @DisplayName("Testa leitorLivros lendo do arquivo CSV")
    void testLeitorLivros() throws IOException {
        String csvContent = "Header,Ignorado,0,0,0,0,false,Pais\n" +
                "J.K. Rowling,Harry Potter,300,ISBN-HP,Fantasia,Rocco,false,UK";

        Files.writeString(tempDir.resolve("livros.csv"), csvContent);

        Livro.leitorLivros(acervo, pathComBarra);

        assertFalse(acervo.getEstantes().isEmpty(), "Deveria ter criado estante");
        assertFalse(acervo.getEstantes().get(0).getLivros().isEmpty(), "Estante deveria ter livro");

        Livro lido = acervo.getEstantes().get(0).getLivros().get(0);
        assertEquals("Harry Potter", lido.getTitulo());
        assertEquals("J.K. Rowling", lido.getAutor().getNome());
    }
}