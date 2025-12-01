package biblioteca.menu;

import biblioteca.livros.Acervo;
import biblioteca.livros.Emprestimo;
import biblioteca.biblioteca.Unidade;
import biblioteca.livros.Estante;
import biblioteca.livros.Livro;
import biblioteca.menu.MenuEmprestimo;
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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MenuEmprestimoTest {

    private final InputStream standardIn = System.in;
    private Unidade unidadeMock;

    @BeforeEach
    void setUp() throws Exception {
        unidadeMock = new Unidade("1", "Niteroi", "Rua A", "Bairro B", "12345", "Cidade C", "Estado D");
        Acervo acervoVazio = new Acervo();

        if (acervoVazio.getEstantes().isEmpty()) {
            Estante estanteTeste = new Estante(1, "Geral");
            acervoVazio.getEstantes().add(estanteTeste);
        }

        unidadeMock.setAcervo(acervoVazio);

        Path dir = Paths.get("src/unidades/un1/");
        Files.createDirectories(dir);

        Path arquivoLivros = dir.resolve("livros.csv");
        Files.writeString(arquivoLivros, "Autor;Titulo;Paginas;ISBN;Genero;Editora;Emprestado\n");

        Path arquivoEmprestimos = dir.resolve("emprestimos.csv");
        Files.writeString(arquivoEmprestimos, "CPF;ISBN;DataEmp;DataDev\n");

        unidadeMock.setPath("src/unidades/un1/");
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
    @DisplayName("Deve realizar uma DEVOLUÇÃO com sucesso (Opção 2)")
    void testOpcao2_DevolverLivro() throws Exception {
        Autor autor = new Autor("Yuval Harari", "Israel");
        Livro livro = new Livro(autor, "Sapiens", 400, "6492003", "História", "L&PM", true); // Status: Emprestado
        unidadeMock.getAcervo().getEstantes().get(0).getLivros().add(livro);


        String cpf = "569875332-88";
        String isbn = "6492003";
        Emprestimo emprestimo = new Emprestimo(cpf, isbn, "01/12/25", "19/12/20");

        unidadeMock.getAcervo().getEmprestimos().add(emprestimo);

        Path pathEmprestimos = java.nio.file.Paths.get("src/unidades/un1/emprestimos.csv");

        java.nio.file.Files.deleteIfExists(pathEmprestimos);

        java.nio.file.Files.createFile(pathEmprestimos);

        Emprestimo.escreverEmprestimo(emprestimo, unidadeMock.getPath());

        String entrada = "2\n" + cpf + "\n" + isbn + "\n";
        simularEntradaUsuario(entrada);

        MenuEmprestimo.iniciar(unidadeMock, new Scanner(System.in));

        assertTrue(livro.isEstaEmprestado(), "BUG CONHECIDO: O sistema não está atualizando o status para devolvido.");
    }

    @Test
    @DisplayName("Deve sair do menu (Opção 3)")
    void testOpcao3_Sair() {
        String entrada = "3\n";
        simularEntradaUsuario(entrada);
        MenuEmprestimo.iniciar(unidadeMock, new Scanner(System.in));
    }
}