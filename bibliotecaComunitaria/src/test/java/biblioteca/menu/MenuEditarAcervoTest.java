package test.java.biblioteca.menu;

import biblioteca.livros.Livro;
import biblioteca.menu.MenuEditarAcervo;
import biblioteca.biblioteca.Unidade;
import biblioteca.pessoas.Autor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import biblioteca.livros.Acervo;
import biblioteca.excecoes.LivroNaoExistenteException;
import static org.junit.jupiter.api.Assertions.*;

public class MenuEditarAcervoTest {

    private final InputStream standardIn = System.in;
    private Unidade unidadeMock;

    @BeforeEach
    void setUp() throws Exception {
        unidadeMock = new Unidade("1", "Niteroi", "Rua A", "Bairro B", "12345", "Cidade C", "Estado D");
        Acervo acervoVazio = new Acervo();
        unidadeMock.setAcervo(acervoVazio);

        java.nio.file.Path dir = java.nio.file.Paths.get("src/unidades/un1/");
        java.nio.file.Files.createDirectories(dir);
        java.nio.file.Path file = dir.resolve("livros.csv");
        if (!java.nio.file.Files.exists(file)) {
            java.nio.file.Files.createFile(file);
        }
    }

    @AfterEach
    void tearDown() {
        System.setIn(standardIn);
    }

    private void simularEntradaUsuario(String dadosEntrada) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(dadosEntrada.getBytes());
        System.setIn(inputStream);
    }

    @Test
    void testEditarAcervo_RemoverLivro_Inexistente() {
        String entrada = "2\nThiagoloide\n";

        simularEntradaUsuario(entrada);
        assertThrows(
                LivroNaoExistenteException.class,
                () -> MenuEditarAcervo.iniciar(unidadeMock, new Scanner(System.in))
        );
    }

    @Test
    void testEditarAcervo_OpcaoInvalida_e_Saida() {
        String entrada = "99\n3";

        try {
            simularEntradaUsuario(entrada);
            MenuEditarAcervo.iniciar(unidadeMock, new Scanner(System.in));
        } catch (Exception e) {
        }
    }

    @Test
    void testEditarAcervo_RemoverLivro_CaminhoAtingido() {
        String entrada = "2\nTitulo Inexistente\n";

        try {
            simularEntradaUsuario(entrada);
            MenuEditarAcervo.iniciar(unidadeMock, new Scanner(System.in));
        } catch (Exception e) {
            // Aceita o encerramento do processo.
        }
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar remover um livro que não existe")
    void deveLancarExcecao_QuandoLivroNaoExiste() {
        String inputSimulado = "2\nThiagoloide1\n";
        simularEntradaUsuario(inputSimulado);

        LivroNaoExistenteException ex = assertThrows(LivroNaoExistenteException.class,
                () -> MenuEditarAcervo.iniciar(unidadeMock, new Scanner(System.in))
        );

        assertEquals("O livro não existe no acervo\n", ex.getMessage());
    }

    @Test
    @DisplayName("Deve remover um livro com sucesso (Opção 2)")
    void testEditarAcervo_RemoverLivro_ComSucesso() throws Exception {

        Autor autor = new Autor("Machado de Assis", "Brasil");
        Livro livroParaRemover = new Livro(autor, "Dom Casmurro", 200, "ISBN-999", "Romance", "Editora A", false);

        unidadeMock.getAcervo().addLivro2(livroParaRemover, unidadeMock.getPath());

        boolean livroExisteAntes = unidadeMock.getAcervo().getEstantes().stream()
                .flatMap(estante -> estante.getLivros().stream())
                .anyMatch(l -> l.getTitulo().equals("Dom Casmurro"));
        assertTrue(livroExisteAntes, "Erro no teste: O livro deveria ter sido adicionado antes de tentar remover.");

        String entrada = "2\nDom Casmurro\n";
        simularEntradaUsuario(entrada);


        MenuEditarAcervo.iniciar(unidadeMock, new Scanner(System.in));

        boolean livroAindaExiste = unidadeMock.getAcervo().getEstantes().stream()
                .flatMap(estante -> estante.getLivros().stream())
                .anyMatch(l -> l.getTitulo().equals("Dom Casmurro"));

        assertFalse(livroAindaExiste, "O livro 'Dom Casmurro' deveria ter sido removido, mas ainda está lá.");
    }

    @Test
    @DisplayName("Should successfully add a book (Option 1)")
    void testEditarAcervo_AdicionarLivro_ComSucesso() throws Exception {
        String entradas = "1\nJ.K. Rowling\nUK\nHarry Potter\n978-123\n300\nFantasia\nRocco\n\n3\n";

        simularEntradaUsuario(entradas);

        MenuEditarAcervo.iniciar(unidadeMock, new Scanner(System.in));

        assertTrue(
                unidadeMock.getAcervo().getEstantes().stream()
                        .flatMap(estante -> estante.getLivros().stream())
                        .anyMatch(livro ->
                                "Harry Potter".equals(livro.getTitulo()) &&
                                        livro.getAutor() != null &&
                                        "J.K. Rowling".equals(livro.getAutor().getNome())
                        ),
                "The book 'Harry Potter' should have been added to the acervo."
        );
    }

    @Test
    void testEditarAcervo_Case3_Sair() {
        String entrada = "3";

        try {
            simularEntradaUsuario(entrada);
            MenuEditarAcervo.iniciar(unidadeMock, new Scanner(System.in));
        } catch (Exception e) {
        }
    }
}