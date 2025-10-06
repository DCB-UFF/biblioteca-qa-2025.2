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
}
