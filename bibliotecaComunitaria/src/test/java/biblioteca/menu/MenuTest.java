package test.java.biblioteca.menu;

import biblioteca.menu.Menu;
import biblioteca.biblioteca.Sistema;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import biblioteca.biblioteca.Unidade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.io.InputStream;
import biblioteca.livros.Acervo;

public class MenuTest {

    private final InputStream standardIn = System.in;
    private Sistema sistemaMock;
    private Unidade unidadeMock;

    @BeforeEach
    void setUp() {
        sistemaMock = new Sistema();
        unidadeMock = new Unidade("1", "Niteroi", "Rua Marechal Floriano 43", "Inga", "23441-001", "Niteroi", "Rio de Janeiro");
        Acervo acervoVazio = new Acervo();
        unidadeMock.setAcervo(acervoVazio);
        sistemaMock.getUnidades().add(unidadeMock);
    }

    @AfterEach
    void tearDown() {
        System.setIn(standardIn);
    }

    private void simularEntradaUsuario(String dadosEntrada) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(dadosEntrada.getBytes());
        System.setIn(inputStream);
    }

    // --- TESTES BÁSICOS QUE PASSAM E COBREM O LOOP PRINCIPAL ---

    @Test
    void testOpcaoSair_Opcao3() {
        String entrada = "3\n"; // Cobre if (op == 3)
        simularEntradaUsuario(entrada);
        assertDoesNotThrow(() -> Menu.iniciar(sistemaMock));
    }

    @Test
    void testEntradaInvalidaInicial_NaoNumerica() {
        String entrada = "abc\n3\n"; // Cobre o else de hasNextInt()
        simularEntradaUsuario(entrada);
        assertDoesNotThrow(() -> Menu.iniciar(sistemaMock));
    }

    @Test
    void testOpcaoInvalidaInicial_SeguidaPorSair() {
        String entrada = "99\n3\n"; // Cobre o else final do loop principal
        simularEntradaUsuario(entrada);
        assertDoesNotThrow(() -> Menu.iniciar(sistemaMock));
    }

    @Test
    void testTentativasElevadas_MensagemAtingida() {
        // Cobre as lógicas de tentativas (tentativas == 4 e tentativas == 7) no loop principal
        String entrada = "a\nb\nc\n99\n99\n99\n99\n"; // 7 tentativas para fechar
        simularEntradaUsuario(entrada);
        assertDoesNotThrow(() -> Menu.iniciar(sistemaMock));
    }

    @Test
    void testAcessarUnidade_NomeInexistente_DeveLancarExcecao() {
        String entrada = "1\nUnidade Inexistente\n";
        simularEntradaUsuario(entrada);

        org.junit.jupiter.api.Assertions.assertThrows(
                biblioteca.excecoes.UnidadeInexistenteException.class,
                () -> Menu.iniciar(sistemaMock)
        );
    }

}