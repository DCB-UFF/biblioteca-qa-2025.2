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
        // Captura o output do console
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
        // Simula a entrada do usuário
        String entradaSimulada = String.join("\n",
                "Maria Teste",     // nome
                "11122233344",     // cpf
                "01/01/1990",      // data
                "21999999999",     // telefone
                "3500",            // salário
                "",
                "Bibliotecária",   // cargo
                "Rua Flores",      // rua
                "Centro",          // bairro
                "23000-000",       // cep
                "Niterói",         // cidade
                "RJ"               // estado
        );

        InputStream input = new ByteArrayInputStream(entradaSimulada.getBytes());
        System.setIn(input);

        // Executa o método
        MenuFuncionario.adicionarFuncionario(unidade);

        // Verifica se funcionário foi adicionado
        assertEquals(1, unidade.getFuncionarios().size());
        Funcionario f = unidade.getFuncionarios().get(0);
        assertEquals("Maria Teste", f.getNome());
        assertEquals("11122233344", f.getCPF());
    }

    @Test
    void deveRemoverFuncionarioDaUnidade() throws Exception {
        // Cria um funcionário simulado
        Funcionario f = new Funcionario(
                "Carlos", "12345678900", "10/10/1980", "2133334444",
                4000f, "Gerente", "Rua A", "Bairro B", "24000-000", "Niterói", "RJ"
        );
        unidade.getFuncionarios().add(f);

        // Simula o CPF digitado
        String entradaSimulada = "12345678900\n";
        System.setIn(new ByteArrayInputStream(entradaSimulada.getBytes()));

        // Substitui método de utilitário (opcionalmente, pode mockar)
        // Aqui assumimos que buscarFuncionario retorna o próprio f
        // Você pode ajustar caso Util.buscarFuncionario() tenha lógica extra

        // Executa o método
        assertDoesNotThrow(() -> {
            MenuFuncionario.removerFuncionario(unidade);
        });

        // Verifica se o funcionário foi removido
        assertTrue(unidade.getFuncionarios().isEmpty());
    }
}
