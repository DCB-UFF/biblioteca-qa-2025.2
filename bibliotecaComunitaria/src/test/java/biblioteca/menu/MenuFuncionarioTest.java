package test.java.biblioteca.menu;

import biblioteca.biblioteca.Unidade;
import biblioteca.menu.MenuFuncionario;
import biblioteca.pessoas.Funcionario;
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MenuFuncionarioTest {

    private Unidade unidade;

    @BeforeEach
    void setup() {
        unidade = new Unidade("UnidadeTest", "Rua Teste", "Rua Teste", "Test Bairro", "Teste cep", "Test Cidade", "Test Estado");
    }

    @Test
    void deveExibirOpcoesCorretamente() {
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        System.setOut(new PrintStream(saida));

        MenuFuncionario.opcoesAcessarAdminFuncionario();

        String output = saida.toString();

        assertTrue(output.contains("1 - Buscar funcionario"));
        assertTrue(output.contains("2 - Adicionar funcionario"));
        assertTrue(output.contains("3 - Remover funcionario"));
        assertTrue(output.contains("4 - imprimir quadro de funcionarios"));
    }

    @Test
    void deveAdicionarFuncionarioNaUnidade() {
        String entradaSimulada = String.join("\n",
                "Maria Teste",
                "11122233344",
                "01/01/1990",
                "21999999999",
                "3500",
                "Bibliotecária",
                "Rua Flores",
                "Centro",
                "23000-000",
                "Niterói",
                "RJ"
        );

        InputStream input = new ByteArrayInputStream(entradaSimulada.getBytes());
        System.setIn(input);

        MenuFuncionario.adicionarFuncionario(unidade);

        assertEquals(1, unidade.getFuncionarios().size());
        Funcionario f = unidade.getFuncionarios().get(0);
        assertEquals("Maria Teste", f.getNome());
        assertEquals("11122233344", f.getCPF());
    }

    @Test
    void deveRemoverFuncionarioDaUnidade() throws Exception {

        Funcionario f = new Funcionario(
                "Carlos", "12345678900", "10/10/1980", "2133334444",
                4000f, "Gerente", "Rua A", "Bairro B", "24000-000", "Niterói", "RJ"
        );
        unidade.getFuncionarios().add(f);


        String entradaSimulada = "12345678900\n";
        System.setIn(new ByteArrayInputStream(entradaSimulada.getBytes()));

        assertDoesNotThrow(() -> {
            MenuFuncionario.removerFuncionario(unidade);
        });

        assertTrue(unidade.getFuncionarios().isEmpty());
    }

    @Test
    void testIniciar_quandoBuscaFuncionarioExistente_deveImprimirSeusDados() throws Exception {
        // Cria um funcionário existente
        Funcionario f = new Funcionario(
                "Jose", "114459885-99", "07/07/2000", "97777-7777",
                2000f, "atendente", "Rua 12 de Abril 99", "Quitino",
                "28651-554", "Rio de Janeiro", "RJ"
        );
        unidade.getFuncionarios().add(f);

        // Simula a entrada do usuário: [1 -> buscar] [cpf -> "114459885-99"] [5 -> sair]
        String entradaSimulada = String.join("\n", "1", "114459885-99", "5", "");

        System.setIn(new ByteArrayInputStream(entradaSimulada.getBytes()));

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        System.setOut(new PrintStream(saida));

        MenuFuncionario.iniciar(unidade, new Scanner(System.in));

        String output = saida.toString();
        assertTrue(output.contains("Jose"), "Deveria imprimir os dados do funcionário Jose");
        assertTrue(output.contains("atendente"), "Deveria exibir o cargo do funcionário");
    }

    @Test
    void testIniciar_quandoBuscaFuncionarioInexistente_deveLancarExcecao() {
        // Simula o usuário tentando buscar um CPF inexistente
        String entradaSimulada = String.join("\n", "1", "000000000-00", "5");
        System.setIn(new ByteArrayInputStream(entradaSimulada.getBytes()));

        assertThrows(
                biblioteca.excecoes.FuncionarioInexistenteException.class,
                () -> MenuFuncionario.iniciar(unidade, new Scanner(System.in)),
                "Deveria lançar FuncionarioInexistenteException ao buscar CPF inexistente"
        );
    }


    @Test
    void testIniciar_quandoImprimeQuadroDeFuncionarios_deveListarTodos() throws Exception {
        // Adiciona alguns funcionários simulando o banco
        unidade.getFuncionarios().addAll(Arrays.asList(
                new Funcionario("Jose", "114459885-99", "07/07/2000", "97777-7777", 2000f, "atendente",
                        "Rua 12 de Abril 99", "Quitino", "28651-554", "Rio de Janeiro", "RJ"),
                new Funcionario("Gustavo", "451878268-52", "08/08/2000", "98888-8888", 2500f, "bibliotecario",
                        "Travessa do Relogio 218", "Botafogo", "24755-012", "Rio de Janeiro", "RJ"),
                new Funcionario("Caio", "247220159-56", "09/09/2000", "99999-9999", 3000f, "gerente",
                        "Estrada dos Catete 834", "Santa Cruz", "29645-090", "Rio de Janeiro", "RJ")
        ));

        // Simula a entrada do usuário: [4 -> listar todos] [5 -> sair]
        String entradaSimulada = String.join("\n", "4", "5", "");

        System.setIn(new ByteArrayInputStream(entradaSimulada.getBytes()));

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        System.setOut(new PrintStream(saida));

        MenuFuncionario.iniciar(unidade, new Scanner(System.in));

        String output = saida.toString();
        assertTrue(output.contains("Jose"), "Deveria listar o funcionário Jose");
        assertTrue(output.contains("Gustavo"), "Deveria listar o funcionário Gustavo");
        assertTrue(output.contains("Caio"), "Deveria listar o funcionário Caio");
    }

    @Test
    void testIniciar_quandoListaVazia_deveInformarUsuario() throws Exception {
        // Garantir que a lista está vazia
        unidade.getFuncionarios().clear();

        // Simula: [4 -> listar] [5 -> sair]
        String entradaSimulada = "4\n5\n";
        System.setIn(new ByteArrayInputStream(entradaSimulada.getBytes()));

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        System.setOut(new PrintStream(saida));

        MenuFuncionario.iniciar(unidade, new Scanner(System.in));

        String output = saida.toString();
        // Verifica se entrou no if (isEmpty)
        assertTrue(output.contains("Nenhum funcionário cadastrado."),
                "Deveria exibir mensagem de lista vazia");
    }

    @Test
    void testIniciar_quandoOpcaoInvalida_deveExibirMensagemErro() throws Exception {
        // Simula: [99 -> inválido] [5 -> sair]
        String entradaSimulada = "99\n5\n";
        System.setIn(new ByteArrayInputStream(entradaSimulada.getBytes()));

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        System.setOut(new PrintStream(saida));

        MenuFuncionario.iniciar(unidade, new Scanner(System.in));

        String output = saida.toString();
        // Verifica o else final
        assertTrue(output.contains("Opção inválida."),
                "Deveria informar que a opção é inválida");
    }

    @Test
    void testIniciar_quandoAtingirCincoTentativas_deveExibirAviso() throws Exception {
        // Simula 5 operações quaisquer (ex: listar vazio 5 vezes) e depois sai
        // [4] [4] [4] [4] [4] -> aviso deve aparecer -> [5] sair
        String entradaSimulada = "4\n4\n4\n4\n4\n5\n";
        System.setIn(new ByteArrayInputStream(entradaSimulada.getBytes()));

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        System.setOut(new PrintStream(saida));

        MenuFuncionario.iniciar(unidade, new Scanner(System.in));

        String output = saida.toString();
        // Verifica o contador de tentativas
        assertTrue(output.contains("Você já realizou 5 operações."),
                "Deveria exibir o aviso de limite de operações");
    }

    @Test
    void testIniciar_fluxoCompletoRemocao() throws Exception {
        // Adiciona um funcionário para ser removido
        Funcionario f = new Funcionario(
                "Removido", "99988877766", "01/01/2000", "21999999999",
                3000f, "Tester", "Rua T", "Bairro T", "20000-000", "Cidade T", "UF"
        );
        unidade.getFuncionarios().add(f);

        // Simula: [3 -> remover] [cpf] [5 -> sair]
        String entradaSimulada = "3\n99988877766\n5\n";
        System.setIn(new ByteArrayInputStream(entradaSimulada.getBytes()));

        MenuFuncionario.iniciar(unidade, new Scanner(System.in));

        // Verifica se a lista ficou vazia após o fluxo do menu
        assertTrue(unidade.getFuncionarios().isEmpty(),
                "O funcionário deveria ter sido removido através do fluxo do menu (Opção 3)");
    }
}
