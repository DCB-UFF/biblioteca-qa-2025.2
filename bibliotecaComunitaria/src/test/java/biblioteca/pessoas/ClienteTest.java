package test.java.biblioteca.pessoas;

import biblioteca.pessoas.Cliente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    private Path tempDir;
    private String pathComBarra;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("teste_clientes");

        pathComBarra = tempDir.toAbsolutePath().toString() + File.separator;
    }

    @AfterEach
    void tearDown() {
        try (Stream<Path> walk = Files.walk(tempDir)) {
            walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
        }
    }

    @Test
    @DisplayName("Deve testar Construtor, Getters, Setters e toString")
    void testEntidadeCliente() {
        Cliente c = new Cliente(
                "Alice", "12345678900", "01/01/2000", "9999-8888",
                "Rua das Flores", "Centro", "12345-000", "Niteroi", "RJ"
        );

        assertEquals("Alice", c.getNome());
        assertEquals("12345678900", c.getCPF());
        assertEquals("01/01/2000", c.getNascimento());
        assertEquals("9999-8888", c.getTelefone());

        assertNotNull(c.getEnd());
        assertEquals("Rua das Flores", c.getEnd().getRua());
        assertEquals("Niteroi", c.getEnd().getCidade());
        assertEquals("RJ", c.getEnd().getEstado());

        c.setCPF("00000000099");
        assertEquals("00000000099", c.getCPF());

        String texto = c.toString();
        assertTrue(texto.contains("Alice"));
        assertTrue(texto.contains("00000000099"));
        assertTrue(texto.contains("Rua das Flores"));
    }

    @Test
    @DisplayName("Deve escrever cliente no arquivo CSV")
    void testEscreverCliente() throws IOException {
        Cliente c = new Cliente(
                "Bob", "11122233344", "10/10/1990", "9888-7777",
                "Av Principal", "Bairro B", "54321-000", "Rio", "RJ"
        );

        Cliente.escreverCliente(c, pathComBarra);

        Path arquivo = tempDir.resolve("clientes.csv");
        assertTrue(Files.exists(arquivo), "O arquivo clientes.csv deveria ter sido criado");

        String conteudo = Files.readString(arquivo);
        assertTrue(conteudo.contains("Bob,11122233344,10/10/1990"));
        assertTrue(conteudo.contains("Av Principal,Bairro B"));
    }

    @Test
    @DisplayName("Deve ler clientes do arquivo (pulando cabeçalho)")
    void testLeitorClientes() throws IOException {
        // --- ARRANGE ---
        Path arquivo = tempDir.resolve("clientes.csv");

        String csvContent = "Nome,CPF,Nasc,Tel,Rua,Bairro,Cep,Cidade,Estado\n" +
                "Carlos,55566677788,05/05/1985,2222-3333,Rua C,Lapa,20000-000,Rio,RJ";

        Files.writeString(arquivo, csvContent);

        ArrayList<Cliente> lista = Cliente.leitorClientes(pathComBarra);

        assertNotNull(lista);
        assertEquals(1, lista.size(), "Deveria ter lido 1 cliente");

        Cliente lido = lista.get(0);
        assertEquals("Carlos", lido.getNome());
        assertEquals("55566677788", lido.getCPF());
        assertEquals("Rua C", lido.getEnd().getRua());
        assertEquals("RJ", lido.getEnd().getEstado());
    }

    @Test
    @DisplayName("Deve remover cliente pelo CPF")
    void testRemoverCliente() throws IOException {
        Path arquivo = tempDir.resolve("clientes.csv");

        String conteudoCSV = "Nome,CPF,RestoDosDados\n" +
                "Ana,11111111111,Dados,Dados,Rua,Bairro,Cep,Cid,ES\n" +
                "Beto,22222222222,Dados,Dados,Rua,Bairro,Cep,Cid,ES";

        Files.writeString(arquivo, conteudoCSV);

        Cliente aDeletar = new Cliente("Beto", "22222222222", "", "", "", "", "", "", "");

        Cliente.removerCliente(aDeletar, pathComBarra);

        assertTrue(Files.exists(arquivo), "O arquivo original deveria existir (recriado)");

        String novoConteudo = Files.readString(arquivo);
        assertTrue(novoConteudo.contains("Ana"), "Ana deveria ficar");
        assertFalse(novoConteudo.contains("Beto"), "Beto deveria ser removido");
        assertTrue(novoConteudo.contains("Nome,CPF"), "O cabeçalho deveria ser mantido");
    }
}