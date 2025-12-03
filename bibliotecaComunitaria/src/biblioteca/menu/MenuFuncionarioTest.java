package biblioteca.menu;

import biblioteca.biblioteca.Unidade;
import biblioteca.pessoas.Funcionario;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MenuFuncionarioTest {

    private Unidade unidade;
    private ByteArrayOutputStream saida;
    private PrintStream printStreamOriginal;

    @BeforeEach
    void setup() throws IOException {
        Path tempDir = Files.createTempDirectory("unidade_testes");
        unidade = new Unidade("UnidadeTest", "Rua Teste", "Rua Teste", "Test Bairro", "Teste cep", "Test Cidade", "Test Estado");
        // unidade.setPath(tempDir.toString() + "/");

        saida = new ByteArrayOutputStream();
        printStreamOriginal = System.out;
        System.setOut(new PrintStream(saida));
    }

    @AfterEach
    void tearDown() {
        System.setOut(printStreamOriginal);
    }

    @Test
    void deveExibirOpcoesCorretamente() {
        MenuFuncionario.opcoesAcessarAdminFuncionario();
        String output = saida.toString();
        assertTrue(output.contains("1 - Buscar funcionario"));
    }

    @Test
    void deveAdicionarFuncionarioNaUnidadeViaMetodoDireto() {
        String entradaSimulada = String.join("\n",
                "Maria Teste", "11122233344", "01/01/1990", "21999999999",
                "3500", "Bibliotecária", "Rua Flores", "Centro",
                "23000-000", "Niterói", "RJ"
        );
        Scanner scanner = new Scanner(new ByteArrayInputStream(entradaSimulada.getBytes()));

        MenuFuncionario.adicionarFuncionario(unidade, scanner);

        assertEquals(1, unidade.getFuncionarios().size());
        assertEquals("Maria Teste", unidade.getFuncionarios().get(0).getNome());
    }

    @Test
    void deveRemoverFuncionarioDaUnidadeViaMetodoDireto() {
        Funcionario f = new Funcionario("Carlos", "12345678900", "10/10/1980", "2133334444", 4000f, "Gerente", "Rua A", "Bairro B", "24000-000", "Niterói", "RJ");
        unidade.getFuncionarios().add(f);

        String entradaSimulada = "12345678900\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(entradaSimulada.getBytes()));

        assertDoesNotThrow(() -> MenuFuncionario.removerFuncionario(unidade, scanner));
        assertTrue(unidade.getFuncionarios().isEmpty());
    }

    @Test
    void testIniciar_limiteDeTentativas_deveExibirMensagemApos5Operacoes() throws Exception {
        String entradaSimulada = "4\n4\n4\n4\n4\n5\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(entradaSimulada.getBytes()));

        MenuFuncionario.iniciar(unidade, scanner);

        String output = saida.toString();
        assertTrue(output.contains("Você já realizou 5 operações"));
    }

    @Test
    void testIniciar_opcaoInvalidaIncrementaTentativas() throws Exception {
        String entradaSimulada = "99\n4\n4\n4\n4\n5\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(entradaSimulada.getBytes()));

        MenuFuncionario.iniciar(unidade, scanner);

        assertTrue(saida.toString().contains("Opção inválida"));
        assertTrue(saida.toString().contains("Você já realizou 5 operações"));
    }

    @Test
    void testIniciar_entradaNaoInteira_deveTratarComoOpcaoInvalidaECobrirHasNextInt() throws Exception {
        String entrada = "textoInvalido\n5\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(entrada.getBytes()));

        MenuFuncionario.iniciar(unidade, scanner);

        assertTrue(saida.toString().contains("Opção inválida"));
    }

    @Test
    void testIniciar_buscarFuncionario_CobreBlocoOpcao1() throws Exception {
        // CORREÇÃO: Adicionamos um funcionário real antes de buscar
        Funcionario f = new Funcionario("Existente", "123456", "01/01/2000", "219999", 2000f, "Cargo", "Rua", "Bairro", "CEP", "Cidade", "UF");
        unidade.getFuncionarios().add(f);

        // Buscamos o CPF "123456" que agora existe. Assim não lança exceção e cobre o bloco.
        String entrada = "1\n123456\n5\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(entrada.getBytes()));

        MenuFuncionario.iniciar(unidade, scanner);

        String output = saida.toString();
        assertTrue(output.contains("Existente"));
    }

    @Test
    void testIniciar_adicionarIncrementaTentativasCorretamente() throws Exception {
        String dados = "N\nC\nD\nT\n1\nC\nR\nB\nC\nC\nE\n";
        String entrada = "2\n" + dados + "4\n4\n4\n4\n5\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(entrada.getBytes()));

        MenuFuncionario.iniciar(unidade, scanner);

        assertTrue(saida.toString().contains("Você já realizou 5 operações"));
    }

    @Test
    void testIniciar_removerIncrementaTentativasCorretamente() throws Exception {
        // CORREÇÃO: Adicionamos um funcionário para ser removido com sucesso
        Funcionario f = new Funcionario("ParaRemover", "999888", "01/01/2000", "219999", 2000f, "Cargo", "Rua", "Bairro", "CEP", "Cidade", "UF");
        unidade.getFuncionarios().add(f);

        // Opção 3 (Remover) -> CPF Válido -> 4 Listagens -> 5 Sair
        // Isso garante que a remoção funcione, não lance exceção, e o contador suba.
        String entrada = "3\n999888\n4\n4\n4\n4\n5\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(entrada.getBytes()));

        MenuFuncionario.iniciar(unidade, scanner);

        assertTrue(saida.toString().contains("Você já realizou 5 operações"));
        assertTrue(saida.toString().contains("foi removido"));
    }
}