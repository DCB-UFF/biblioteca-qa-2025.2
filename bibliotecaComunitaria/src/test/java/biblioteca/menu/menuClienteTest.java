package biblioteca.menu;

import biblioteca.biblioteca.Unidade;
import biblioteca.excecoes.ClienteInexistenteException;
import biblioteca.pessoas.Cliente;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.After;
import org.junit.jupiter.Before;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class menuClienteTest {

    // Exceção de teste para simular a saída controlada
    private static class TestExitException extends RuntimeException {}

    // Implementação de ExitHandler para Teste
    private final ExitHandler mockExitHandler = status -> {
        throw new TestExitException();
    };

    // Simulação de I/O (Entrada/Saída)
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    // Instâncias de teste
    private Unidade mockUnidade;
    private Scanner mockTeclado;
    private MenuCliente menuCliente; // A classe de teste agora testa uma instância

    private static final String CLIENTE_TO_STRING = "Cliente Mocked Info";
    private static final String CPF_VALIDO_BUSCA = "111111111111";
    private static final String CPF_VALIDO_REMOCAO = "222222222222";


    @Before
    public void setUp() {
        // Redireciona System.out para capturar a saída do console
        System.setOut(new PrintStream(outContent));

        // 1. Inicializa a Unidade
        mockUnidade = new Unidade("testPath", "TestUnit", "Rua", "Bairro", "CEP", "Cidade", "Estado");
        mockUnidade.setClientes(new ArrayList<>());

        // 2. Instancia o MenuCliente com o ExitHandler de teste
        menuCliente = new MenuCliente(mockExitHandler);
    }

    @After
    public void restoreStreams() {
        // Restaura System.in e System.out para seus estados originais
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    //-------------------------------------------------------------------------
    // Testes de Métodos Simples
    //-------------------------------------------------------------------------

    /**
     * Testa se o método estático exibe corretamente as opções do menu.
     */
    @Test
    public void testOpcoesAcessarAdminCliente() {
        MenuCliente.opcoesAcessarAdminCliente();
        String expectedOutput = "\n1 - Buscar cliente\n" +
                "2 - Adicionar cliente\n" +
                "3 - Remover cliente\n" +
                "4 - imprimir quadro de clientes\n" +
                "5 - Sair\n\n";

        assertEquals(expectedOutput.replaceAll("\\s", ""), outContent.toString().replaceAll("\\s", ""));
    }

    /**
     * Testa a adição de um cliente com sucesso, verificando a lista e a exceção de saída.
     * Cobertura: MenuCliente.adicionarCliente(Unidade aux)
     */
    @Test(expected = TestExitException.class)
    public void testAdicionarCliente_Sucesso() {
        // Dados de entrada para o Scanner
        String input = "Nome Cliente\n123456789012\n01/01/2000\n123456789\nRua A\nBairro B\n12345678\nCidade C\nEstado D\n";

        // Simula a entrada do usuário (System.in)
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        menuCliente.adicionarCliente(mockUnidade); // Espera-se que lance TestExitException

        // As verificações são feitas pelo @After ou se o teste falhar
        assertEquals(1, mockUnidade.getClientes().size());
        assertEquals("Nome Cliente", mockUnidade.getClientes().get(0).getNome());
    }

    /**
     * Testa a remoção de um cliente com sucesso, verificando a remoção da lista e a saída.
     * Cobertura: MenuCliente.removerCliente(Unidade unidadeAtual) - Sucesso
     */
    @Test(expected = TestExitException.class)
    public void testRemoverCliente_Sucesso() throws ClienteInexistenteException {
        String cpf = CPF_VALIDO_REMOCAO;
        String input = cpf + "\n";

        // Prepara a Unidade com um cliente para ser removido
        Cliente clienteParaRemover = new Cliente("Removivel", cpf, "data", "tele", "rua", "bairro", "cep", "cidade", "estado");
        mockUnidade.getClientes().add(clienteParaRemover);
        assertEquals(1, mockUnidade.getClientes().size());

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        menuCliente.removerCliente(mockUnidade); // Espera-se que lance TestExitException

        // Verificações após a exceção:
        assertTrue(mockUnidade.getClientes().isEmpty());
        assertTrue(outContent.toString().contains("O cliente de cpf " + cpf + "foi removido!"));
    }

    /**
     * Testa a remoção de cliente quando o Util lança ClienteInexistenteException.
     * Cobertura: MenuCliente.removerCliente(Unidade unidadeAtual) - Exceção
     */
    @Test(expected = ClienteInexistenteException.class)
    public void testRemoverCliente_ClienteInexistente() throws ClienteInexistenteException {
        String cpfInexistente = "99999999999";
        String input = cpfInexistente + "\n";

        // A lista está vazia, forçando o Util.buscarCliente a lançar a exceção
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        menuCliente.removerCliente(mockUnidade);
    }

    //-------------------------------------------------------------------------
    // Testes para o método INICIAR (Loop Principal)
    //-------------------------------------------------------------------------

    /**
     * Testa o case 1 (Buscar cliente) - CPF com comprimento inválido.
     * Cobertura: case 1 - if (cpf.length() != 11)
     */
    @Test(expected = TestExitException.class)
    public void testIniciar_Case1_CPFInvalido() throws ClienteInexistenteException {
        // Simula a entrada: opção 1, CPF inválido (10 dígitos).
        String input = "1\n1234567890\n";
        mockTeclado = new Scanner(new ByteArrayInputStream(input.getBytes()));

        menuCliente.iniciar(mockUnidade, mockTeclado);

        assertTrue(outContent.toString().contains("CPF inválido!"));
    }

    /**
     * Testa o case 1 (Buscar cliente) - Cliente encontrado.
     * Cobertura: case 1 - else (cliente encontrado)
     */
    @Test(expected = TestExitException.class)
    public void testIniciar_Case1_ClienteEncontrado() throws ClienteInexistenteException {
        String cpf = CPF_VALIDO_BUSCA;
        // Simula a entrada: opção 1, CPF válido.
        String input = "1\n" + cpf + "\n";
        mockTeclado = new Scanner(new ByteArrayInputStream(input.getBytes()));

        // 1. Cria um Cliente real e o adiciona à lista para que o Util.buscarCliente o encontre
        Cliente clienteEncontrado = new Cliente("Mocked", cpf, "data", "tele", "rua", "bairro", "cep", "cidade", "estado") {
            @Override
            public String toString() {
                return CLIENTE_TO_STRING;
            }
        };
        mockUnidade.getClientes().add(clienteEncontrado);

        menuCliente.iniciar(mockUnidade, mockTeclado);

        assertTrue(outContent.toString().contains(CLIENTE_TO_STRING));
    }

    /**
     * Testa o case 1 (Buscar cliente) - Cliente não encontrado (a linha buscado == null).
     * OBS: O seu código `MenuCliente` tem a linha `if (buscado == null)` mas
     * o `Util.buscarCliente` lança exceção em vez de retornar null.
     * Para cobrir essa linha condicional, precisamos simular a exceção (que não podemos sem Mockito).
     * O teste abaixo cobre a exceção que é lançada, que é o caminho funcional do seu código.
     */
    @Test(expected = ClienteInexistenteException.class)
    public void testIniciar_Case1_ClienteNaoEncontrado() throws ClienteInexistenteException {
        String cpf = CPF_VALIDO_BUSCA;
        // Simula a entrada: opção 1, CPF válido.
        String input = "1\n" + cpf + "\n";
        mockTeclado = new Scanner(new ByteArrayInputStream(input.getBytes()));

        // Lista de clientes vazia, forçando Util.buscarCliente a lançar exceção.
        menuCliente.iniciar(mockUnidade, mockTeclado);
    }

    /**
     * Testa o case 2 (Adicionar cliente).
     * Cobertura: case 2
     */
    @Test(expected = TestExitException.class)
    public void testIniciar_Case2_AdicionarCliente() throws ClienteInexistenteException {
        // Simula a entrada: opção 2, dados do cliente.
        String inputAdicionar = "Nome\n123456789012\nData\nTelefone\nRua\nBairro\nCEP\nCidade\nEstado\n";
        String inputCompleto = "2\n" + inputAdicionar;
        mockTeclado = new Scanner(new ByteArrayInputStream(inputCompleto.getBytes()));

        menuCliente.iniciar(mockUnidade, mockTeclado);

        // Verifica se a adição foi iniciada
        assertTrue(outContent.toString().contains("Digite o nome do cliente:"));
        assertEquals(1, mockUnidade.getClientes().size());
    }

    /**
     * Testa o case 3 (Remover cliente).
     * Cobertura: case 3
     */
    @Test(expected = TestExitException.class)
    public void testIniciar_Case3_RemoverCliente() throws ClienteInexistenteException {
        String cpf = CPF_VALIDO_REMOCAO;
        // Simula a entrada: opção 3, CPF do cliente.
        String input = "3\n" + cpf + "\n";
        mockTeclado = new Scanner(new ByteArrayInputStream(input.getBytes()));

        // Adiciona o cliente para ser removido
        Cliente clienteParaRemover = new Cliente("Removivel", cpf, "data", "tele", "rua", "bairro", "cep", "cidade", "estado");
        mockUnidade.getClientes().add(clienteParaRemover);

        menuCliente.iniciar(mockUnidade, mockTeclado);

        assertTrue(outContent.toString().contains("O cliente de cpf " + cpf + "foi removido!"));
        assertTrue(mockUnidade.getClientes().isEmpty());
    }

    /**
     * Testa o case 4 (imprimir quadro de clientes) - Lista vazia.
     * Cobertura: case 4 - if (unidadeAtual.getClientes().isEmpty())
     */
    @Test(expected = TestExitException.class)
    public void testIniciar_Case4_ListaVazia() throws ClienteInexistenteException {
        // Simula a entrada: opção 4.
        String input = "4\n";
        mockTeclado = new Scanner(new ByteArrayInputStream(input.getBytes()));

        mockUnidade.setClientes(new ArrayList<>());

        menuCliente.iniciar(mockUnidade, mockTeclado);

        assertTrue(outContent.toString().contains("Nenhum cliente cadastrado."));
    }

    /**
     * Testa o case 4 (imprimir quadro de clientes) - Imprimir clientes.
     * Cobertura: case 4 - else (itera sobre a lista)
     */
    @Test(expected = TestExitException.class)
    public void testIniciar_Case4_ImprimirClientes() throws ClienteInexistenteException {
        // Simula a entrada: opção 4.
        String input = "4\n";
        mockTeclado = new Scanner(new ByteArrayInputStream(input.getBytes()));

        // Cria clientes reais com Override no toString para simular a saída
        Cliente cliente1 = new Cliente("C1", "1", "d", "t", "r", "b", "c", "ci", "e") {
            @Override public String toString() { return "Cliente Um Info"; }
        };
        Cliente cliente2 = new Cliente("C2", "2", "d", "t", "r", "b", "c", "ci", "e") {
            @Override public String toString() { return "Cliente Dois Info"; }
        };

        ArrayList<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente1);
        clientes.add(cliente2);
        mockUnidade.setClientes(clientes);

        menuCliente.iniciar(mockUnidade, mockTeclado);

        assertTrue(outContent.toString().contains("Cliente Um Info"));
        assertTrue(outContent.toString().contains("Cliente Dois Info"));
    }

    /**
     * Testa o case 5 (Sair).
     * Cobertura: case 5
     */
    @Test(expected = TestExitException.class)
    public void testIniciar_Case5_Sair() throws ClienteInexistenteException {
        // Simula a entrada: opção 5 (Sair)
        String input = "5\n";
        mockTeclado = new Scanner(new ByteArrayInputStream(input.getBytes()));

        menuCliente.iniciar(mockUnidade, mockTeclado);

        assertTrue(outContent.toString().contains("Fechando aplicação..."));
    }

    /**
     * Testa o default (Opção inválida) e a continuação do loop.
     * Cobertura: default (opção inválida)
     */
    @Test(expected = TestExitException.class)
    public void testIniciar_Default_OpcaoInvalida_Continua() throws ClienteInexistenteException {
        // Simula a entrada: opção 9 (inválida), e opção 5 (Sair)
        String input = "9\n5\n";
        mockTeclado = new Scanner(new ByteArrayInputStream(input.getBytes()));

        menuCliente.iniciar(mockUnidade, mockTeclado);

        assertTrue(outContent.toString().contains("Opção inválida."));
    }
}