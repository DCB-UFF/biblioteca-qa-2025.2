package test.java.biblioteca.menu;

import biblioteca.menu.Menu;
import biblioteca.biblioteca.Sistema;
import biblioteca.excecoes.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MenuTest {

    @Test
    void testIniciarRunsWithoutException() {
        Sistema sistema = new Sistema(); // You may need to mock or create a minimal Sistema
        String simulatedInput = "3\n"; // Simulates choosing "exit" immediately
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        assertDoesNotThrow(() -> Menu.iniciar(sistema));
    }
}