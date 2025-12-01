package biblioteca.menu;

import biblioteca.biblioteca.Unidade;
import biblioteca.excecoes.ClienteInexistenteException;
import biblioteca.menu.MenuCliente;
import biblioteca.pessoas.Cliente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class MenuClienteTest {

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayOutputStream testOut;

    private Unidade unidade;
    private Path testUnidadePath;
    private final String UNIDADE_ID = "Teste";

    @BeforeEach
    void setUp() throws IOException {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        unidade = new Unidade(UNIDADE_ID, "Unidade de Teste", "Rua Teste", "Bairro Teste", "12345-678", "Cidade Teste", "TS");

        testUnidadePath = Path.of(unidade.getPath());
        Files.createDirectories(testUnidadePath);
    }

    @AfterEach
    void tearDown() throws IOException {
        System.setIn(systemIn);
        System.setOut(systemOut);

        if (Files.exists(testUnidadePath)) {
            Files.walk(testUnidadePath)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    private Scanner provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
        return new Scanner(testIn);
    }

    private String getOutput() {
        return testOut.toString().replace("\r\n", "\n");
    }

    private Path getClientesCsvPath() {
        return testUnidadePath.resolve("clientes.csv");
    }

    private Cliente criarClientePadrao() {
        return new Cliente("Cliente Teste", "123456789-00", "01/01/1990", "98765-4321", "Rua T", "Bairro T", "11111-111", "Cidade T", "TS");
    }

    @Test
    void opcoesAcessarAdminCliente_deveImprimirMenuCorretamente() {
        MenuCliente.opcoesAcessarAdminCliente();
        String output = getOutput();
        assertTrue(output.contains("1 - Buscar cliente"));
        assertTrue(output.contains("5 - Voltar ao menu da unidade"));
    }

    @Test
    void adicionarCliente_deveEscreverNoArquivoEAtualizarUnidade() throws IOException {
        String input = "Novo Cliente\n111222333-44\n02/02/2000\n99999-8888\nRua Nova\nBairro Novo\n22222-111\nCidade Nova\nTS\n";
        Scanner scanner = provideInput(input);

        MenuCliente.adicionarCliente(unidade, scanner);

        Path csvPath = getClientesCsvPath();
        assertTrue(Files.exists(csvPath));
        List<String> lines = Files.readAllLines(csvPath);
        assertTrue(lines.get(lines.size() - 1).contains("Novo Cliente,111222333-44"));
        assertEquals(1, unidade.getClientes().size());
        assertEquals("111222333-44", unidade.getClientes().get(0).getCPF());
        assertTrue(getOutput().contains("Cliente adicionado com sucesso!"));
    }

    @Test
    void removerCliente_quandoCpfValido_deveRemoverDoArquivoEDaUnidade() throws IOException, ClienteInexistenteException {
        Cliente cliente = criarClientePadrao();
        unidade.getClientes().add(cliente);

        Path csvPath = getClientesCsvPath();
        String header = "#nome,#cpf,#nascimento,#telefone,#rua,#bairro,#cep,#cidade,#estado";
        String clientLine = cliente.getNome() + "," + cliente.getCPF() + "," + cliente.getNascimento() + "," + cliente.getTelefone() + "," + cliente.getEnd().getRua() + "," + cliente.getEnd().getBairro() + "," + cliente.getEnd().getCep() + "," + cliente.getEnd().getCidade() + "," + cliente.getEnd().getEstado();
        Files.writeString(csvPath, header + "\n" + clientLine);

        Scanner scanner = provideInput("123456789-00\n");
        MenuCliente.removerCliente(unidade, scanner);

        assertTrue(unidade.getClientes().isEmpty());
        List<String> lines = Files.readAllLines(getClientesCsvPath());
        assertEquals(1, lines.size(), "Apenas o cabeçalho deveria permanecer no arquivo.");
    }

    @Test
    void removerCliente_quandoCpfInvalido_deveLancarExcecao() {
        Scanner scanner = provideInput("000.000.000-00\n");
        assertThrows(ClienteInexistenteException.class, () -> {
            MenuCliente.removerCliente(unidade, scanner);
        });
    }

    @Test
    void removerCliente_quandoCpfEmBranco_deveRetornarEMostrarMensagem() throws ClienteInexistenteException {
        Scanner scanner = provideInput("\n");
        MenuCliente.removerCliente(unidade, scanner);
        assertTrue(getOutput().contains("CPF inválido."));
    }

    @Test
    void iniciar_quandoOpcao1EClienteExiste_deveImprimirCliente() {
        Cliente cliente = criarClientePadrao();
        unidade.getClientes().add(cliente);
        String input = "1\n123456789-00\n5\n";
        Scanner scanner = provideInput(input);

        MenuCliente.iniciar(unidade, scanner);

        String output = getOutput();
        assertTrue(output.contains("--- Cliente Encontrado ---"));
        assertTrue(output.contains("Nome: Cliente Teste - CPF: 123456789-00"));
    }

    @Test
    void iniciar_quandoOpcao1EClienteNaoExiste_deveImprimirMensagemErro() {
        Cliente outroCliente = new Cliente("Outro Cliente", "999888777-66", "01/01/2000", "55555-4444", "Rua O", "Bairro O", "33333-222", "Cidade O", "TO");
        unidade.getClientes().add(outroCliente);

        String input = "1\n000000000-00\n5\n"; // Procurar por um CPF que não existe
        Scanner scanner = provideInput(input);

        MenuCliente.iniciar(unidade, scanner);

        assertTrue(getOutput().contains("Cliente não existe"));
    }

    @Test
    void iniciar_quandoOpcao3EClienteExiste_deveImprimirSucesso() {
        Cliente cliente = criarClientePadrao();
        unidade.getClientes().add(cliente);
        Cliente.escreverCliente(cliente, unidade.getPath());

        String input = "3\n123456789-00\n5\n";
        Scanner scanner = provideInput(input);

        MenuCliente.iniciar(unidade, scanner);

        assertTrue(getOutput().contains("Cliente removido com sucesso."));
        assertTrue(unidade.getClientes().isEmpty());
    }

    @Test
    void iniciar_quandoOpcao4ComClientes_deveImprimirLista() {
        Cliente c1 = criarClientePadrao();
        Cliente c2 = new Cliente("Outro Cliente", "987654321-00", "02/02/1992", "12345-6789", "Rua O", "Bairro O", "22222-222", "Cidade O", "TS");
        unidade.getClientes().add(c1);
        unidade.getClientes().add(c2);

        String input = "4\n5\n";
        Scanner scanner = provideInput(input);
        MenuCliente.iniciar(unidade, scanner);

        String output = getOutput();
        assertTrue(output.contains("--- Quadro de Clientes ---"));
        assertTrue(output.contains("123456789-00"));
        assertTrue(output.contains("987654321-00"));
    }

    @Test
    void iniciar_quandoOpcao4SemClientes_deveImprimirMensagemVazio() {
        String input = "4\n5\n";
        Scanner scanner = provideInput(input);
        MenuCliente.iniciar(unidade, scanner);

        String output = getOutput();
        assertTrue(output.contains("--- Quadro de Clientes ---"));
        assertTrue(output.contains("Nenhum cliente cadastrado."));
    }

    @Test
    void iniciar_quandoOpcaoInvalida_deveImprimirMensagemErro() {
        String input = "9\n5\n";
        Scanner scanner = provideInput(input);
        MenuCliente.iniciar(unidade, scanner);
        assertTrue(getOutput().contains("Opção inválida."));
    }

    @Test
    void iniciar_quandoOpcao5_deveSairDoLoop() {
        String input = "5\n";
        Scanner scanner = provideInput(input);
        MenuCliente.iniciar(unidade, scanner);
        assertTrue(getOutput().contains("Retornando ao menu da unidade..."));
    }
}

