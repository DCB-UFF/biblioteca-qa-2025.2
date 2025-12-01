package biblioteca.menu;

import biblioteca.livros.Acervo;
import biblioteca.biblioteca.Unidade;
import biblioteca.livros.Estante;
import biblioteca.livros.Livro;
import biblioteca.menu.MenuConsultarAcervo;
import biblioteca.pessoas.Autor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class MenuConsultarAcervoTest {

    private final InputStream standardIn = System.in;
    private Unidade unidadeMock;

    @BeforeEach
    void setUp() throws Exception {
        // 1. Configuração Básica (Unidade + Acervo + Estante)
        unidadeMock = new Unidade("1", "Niteroi", "Rua A", "Bairro B", "12345", "Cidade C", "Estado D");
        Acervo acervo = new Acervo();

        if (acervo.getEstantes().isEmpty()) {
            acervo.getEstantes().add(new Estante(1, "Geral"));
        }
        unidadeMock.setAcervo(acervo);

        Path dir = Paths.get("src/unidades/un1/");
        Files.createDirectories(dir);
        unidadeMock.setPath("src/unidades/un1/");
    }

    @AfterEach
    void tearDown() {
        System.setIn(standardIn);
    }

    private void simularEntradaUsuario(String dadosEntrada) {
        System.setIn(new ByteArrayInputStream(dadosEntrada.getBytes()));
    }

    @Test
    @DisplayName("Opção 1: Buscar Título - Cenário SUCCESSO (Disponível e Emprestado)")
    void testOpcao1_BuscarTitulo_Sucesso() throws Exception {
        Autor autor = new Autor("Autor X", "Pais X");

        Livro l1 = new Livro(autor, "Livro Disponivel", 100, "111", "Geral", "Ed A", false);
        unidadeMock.getAcervo().getEstantes().get(0).getLivros().add(l1);

        Livro l2 = new Livro(autor, "Livro Emprestado", 100, "222", "Geral", "Ed B", true);
        unidadeMock.getAcervo().getEstantes().get(0).getLivros().add(l2);

        String entrada = "1\nLivro Disponivel\n" +
                "1\nLivro Emprestado\n" +
                "5\n";

        simularEntradaUsuario(entrada);

        MenuConsultarAcervo.iniciar(unidadeMock, new Scanner(System.in));
    }

    @Test
    @DisplayName("Opção 1: Buscar Título - Cenário ERRO (Livro não existe)")
    void testOpcao1_BuscarTitulo_NaoEncontrado() {

        String entrada = "1\nLivro Fantasma\n";
        simularEntradaUsuario(entrada);

        biblioteca.excecoes.LivroNaoExistenteException erro = org.junit.jupiter.api.Assertions.assertThrows(
                biblioteca.excecoes.LivroNaoExistenteException.class,
                () -> MenuConsultarAcervo.iniciar(unidadeMock, new Scanner(System.in))
        );
    }

    @Test
    @DisplayName("Opção 2: Buscar Autor (Cobre: Encontrado, Não encontrado e Alerta de repetição)")
    void testOpcao2_BuscarAutor() throws Exception {
        Autor autor = new Autor("Machado", "Brasil");
        Livro l1 = new Livro(autor, "Dom Casmurro", 200, "333", "Romance", "Ed C", false);
        unidadeMock.getAcervo().getEstantes().get(0).getLivros().add(l1);

        String entrada = "2\nMachado\n" +
                "2\nOrwell\n" +
                "5\n";

        simularEntradaUsuario(entrada);

        MenuConsultarAcervo.iniciar(unidadeMock, new Scanner(System.in));
    }

    @Test
    @DisplayName("Opção 4: Listar Emprestados (Cobre: Lista vazia e Lista cheia)")
    void testOpcao4_LivrosEmprestados() throws Exception {
        String entradaVazia = "4\n5\n";
        simularEntradaUsuario(entradaVazia);
        MenuConsultarAcervo.iniciar(unidadeMock, new Scanner(System.in));

        Autor autor = new Autor("Autor Y", "Pais Y");
        Livro emprestado = new Livro(autor, "O Emprestado", 100, "444", "Geral", "Ed D", true);
        unidadeMock.getAcervo().getEstantes().get(0).getLivros().add(emprestado);

        String entradaCheia = "4\n5\n";
        simularEntradaUsuario(entradaCheia);
        MenuConsultarAcervo.iniciar(unidadeMock, new Scanner(System.in));
    }

    @Test
    @DisplayName("Erros e Limites (Cobre: Input inválido, Opção inválida e Max Tentativas)")
    void testFluxo_Erros_E_Limite() throws Exception {

        String entrada = "abc\n" +
                "99\n" +
                "3\n3\n3\n3\n3\n";

        simularEntradaUsuario(entrada);

        MenuConsultarAcervo.iniciar(unidadeMock, new Scanner(System.in));

    }
}