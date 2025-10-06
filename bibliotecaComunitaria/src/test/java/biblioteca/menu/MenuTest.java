package test.java.biblioteca.menu;

import biblioteca.menu.Menu;
import biblioteca.biblioteca.Sistema;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import biblioteca.biblioteca.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.io.InputStream;

public class MenuTest {

    private final InputStream standardIn = System.in;
    private Sistema sistemaMock;
    private Unidade unidadeMock;

    @BeforeEach
    void setUp() {
        sistemaMock = new Sistema();
        unidadeMock = new Unidade("01", "Unidade Teste", "Rua A", "Bairro B", "12345", "Cidade C", "Estado D");
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

    @Test
    void testOpcaoSair_Opcao3() {
        String entrada = "3\n";
        simularEntradaUsuario(entrada);

        assertDoesNotThrow(() -> Menu.iniciar(sistemaMock));

    }

    @Test
    void testEntradaInvalidaInicial_NaoNumerica() {
        String entrada = "abc\n3\n";
        simularEntradaUsuario(entrada);

        assertDoesNotThrow(() -> Menu.iniciar(sistemaMock));
    }

    @Test
    void testOpcaoInvalidaInicial_SeguidaPorSair() {
        String entrada = "99\n3\n";
        simularEntradaUsuario(entrada);

        assertDoesNotThrow(() -> Menu.iniciar(sistemaMock));
    }

    @Test
    void testAcessarUnidade_NomeExistente_SairDaUnidade() {
        String entrada = "1\nUnidade Teste\n4\n3\n";
        simularEntradaUsuario(entrada);

        assertDoesNotThrow(() -> Menu.iniciar(sistemaMock));
    }

    @Test
    void testTentativasElevadas_MensagemAtingida() {
        String entrada = "a\nb\nc\n3\n";
        simularEntradaUsuario(entrada);

        assertDoesNotThrow(() -> Menu.iniciar(sistemaMock));
    }
}