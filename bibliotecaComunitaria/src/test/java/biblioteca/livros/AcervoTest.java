package test.java.biblioteca.livros;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import biblioteca.biblioteca.Unidade;
import biblioteca.livros.Acervo;
import biblioteca.livros.Estante;
import biblioteca.livros.Livro;
import biblioteca.pessoas.Autor;

public class AcervoTest {

    private static final String ISBN = "123456789";
    private static final String TITULO = "Livro Teste";
    private static final String NOME_AUTOR = "Autor Teste";
    private static final String CPF_CLIENTE = "111.222.333-44";

    private Acervo acervo;
    private Livro livro;
    private Autor autor;
    private Unidade unidade;

    @BeforeEach
    void setUp() {
        acervo = new Acervo();
        autor = new Autor(NOME_AUTOR);
        livro = new Livro(autor, TITULO, 200, ISBN, "Aventura", "Editora Teste",true );
        unidade = new Unidade(
            "001",
            "Unidade Central",
            "Rua A",
            "Bairro B",
            "12345-678",
            "Cidade C",
            "Estado D"
        );

        Estante estante = new Estante(1, "Aventura");
        acervo.addEstante(estante);
    }

    @Test
    void deveAdicionarLivroNoAcervo() {
        acervo.addLivro(livro);

        Livro encontrado = acervo.buscarLivroISNB(ISBN);

        assertNotNull(encontrado);
        assertEquals(TITULO, encontrado.getTitulo());
    }

    @Test
    void deveBuscarLivroPorIsbn() {
        acervo.addLivro(livro);

        Livro encontrado = acervo.buscarLivroISNB(ISBN);

        assertNotNull(encontrado);
        assertEquals(ISBN, encontrado.getISBN());
    }

    @Test
    void deveRetornarNullQuandoIsbnNaoExistir() {
        Livro encontrado = acervo.buscarLivroISNB("987654321");

        assertNull(encontrado);
    }

    @Test
    void deveBuscarLivrosPorAutor() {
        acervo.addLivro(livro);

        ArrayList<Livro> livrosDoAutor = acervo.buscarLivroAutor(NOME_AUTOR);

        assertFalse(livrosDoAutor.isEmpty());
        assertEquals(1, livrosDoAutor.size());
        assertEquals(TITULO, livrosDoAutor.get(0).getTitulo());
    }

    @Test
    void deveRetornarListaVaziaParaAutorSemLivros() {
        ArrayList<Livro> livrosDoAutor = acervo.buscarLivroAutor("Autor Inexistente");

        assertTrue(livrosDoAutor.isEmpty());
    }
}
