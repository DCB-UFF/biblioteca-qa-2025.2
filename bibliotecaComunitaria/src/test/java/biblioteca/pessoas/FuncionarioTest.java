package test.java.biblioteca.pessoas;

import biblioteca.pessoas.Funcionario;
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

class FuncionarioTest {

    private Path tempDir;
    private String pathComBarra;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("teste_funcionarios");

        pathComBarra = tempDir.toAbsolutePath().toString() + File.separator;
    }

    @AfterEach
    void tearDown() {
        try (Stream<Path> walk = Files.walk(tempDir)) {
            walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            // Ignora erro de deleção
        }
    }

    @Test
    @DisplayName("Deve testar Getters, Setters e toString (Entidade Básica)")
    void testEntidadeFuncionario() {
        Funcionario f = new Funcionario(
                "João Silva", "12345678900", "01/01/1990", "9999-8888",
                2500.0f, "Bibliotecario",
                "Rua Teste", "Bairro T", "12345-000", "Cidade T", "UF"
        );

        assertEquals("12345678900", f.getCPF());
        assertEquals(2500.0f, f.getSalario());
        assertEquals("Bibliotecario", f.getCargo());
        assertEquals("João Silva", f.getNome());
        assertNotNull(f.getEnd());
        assertEquals("Rua Teste", f.getEnd().getRua());

        f.setCPF("99999999900");
        assertEquals("99999999900", f.getCPF());

        String texto = f.toString();
        assertTrue(texto.contains("João Silva"));
        assertTrue(texto.contains("Bibliotecario"));
    }

    @Test
    @DisplayName("Deve escrever um funcionário no arquivo CSV")
    void testEscreverFuncionario() throws IOException {
        // --- ARRANGE ---
        Funcionario f = new Funcionario(
                "Maria", "11122233344", "05/05/1995", "9888-7777",
                3000.0f, "Gerente",
                "Rua M", "Centro", "54321-000", "Rio", "RJ"
        );

        Funcionario.escreverFuncionario(f, pathComBarra);

        Path arquivo = tempDir.resolve("funcionarios.csv");
        assertTrue(Files.exists(arquivo), "O arquivo funcionarios.csv deveria ter sido criado");

        String conteudo = Files.readString(arquivo);
        assertTrue(conteudo.contains("Maria"), "O nome deveria estar no arquivo");
        assertTrue(conteudo.contains("11122233344"), "O CPF deveria estar no arquivo");
        assertTrue(conteudo.contains("Gerente"), "O cargo deveria estar no arquivo");
    }

    @Test
    @DisplayName("Deve ler funcionários do arquivo (ignorando cabeçalho)")
    void testLeitorFuncionarios() throws IOException {
        // --- ARRANGE ---
        Path arquivo = tempDir.resolve("funcionarios.csv");

        String conteudoCSV = "Nome,CPF,Nasc,Tel,Salario,Cargo,Rua,Bairro,Cep,Cidade,Estado\n" +
                "Carlos,55566677788,10/10/2000,2222-3333,1500.0,Estagiario,Rua C,Lapa,20000-000,Rio,RJ";

        Files.writeString(arquivo, conteudoCSV);

        ArrayList<Funcionario> lista = Funcionario.leitorFuncionarios(pathComBarra);

        assertNotNull(lista);
        assertEquals(1, lista.size(), "Deveria ter lido 1 funcionário");

        Funcionario lido = lista.get(0);
        assertEquals("Carlos", lido.getNome());
        assertEquals("Estagiario", lido.getCargo());
        assertEquals(1500.0f, lido.getSalario());
    }

    @Test
    @DisplayName("Deve remover um funcionário pelo CPF")
    void testRemoverFuncionario() throws IOException {

        Path arquivo = tempDir.resolve("funcionarios.csv");

        String conteudoCSV = "Nome,CPF,RestoDosDados\n" +
                "Ana,11111111111,01/01/90,Tel,1000,Cargo,R,B,C,Cid,ES\n" +
                "Beto,22222222222,02/02/90,Tel,2000,Cargo,R,B,C,Cid,ES";

        Files.writeString(arquivo, conteudoCSV);

        Funcionario aDeletar = new Funcionario("Beto", "22222222222", "", "", 0, "", "", "", "", "", "");

        Funcionario.removerFuncionario(aDeletar, pathComBarra);

        assertTrue(Files.exists(arquivo), "O arquivo original deveria ter sido recriado/renomeado");

        String novoConteudo = Files.readString(arquivo);

        assertTrue(novoConteudo.contains("Nome,CPF"), "O cabeçalho deveria ter sido mantido");
        assertTrue(novoConteudo.contains("Ana"), "A Ana deveria continuar no arquivo");
        assertFalse(novoConteudo.contains("Beto"), "O Beto deveria ter sido removido");
    }
}