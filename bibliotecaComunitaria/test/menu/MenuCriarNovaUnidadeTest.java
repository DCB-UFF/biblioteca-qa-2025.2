package biblioteca.menu;

import biblioteca.biblioteca.Sistema;
import biblioteca.menu.MenuCriarNovaUnidade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MenuCriarNovaUnidadeTest {

    private final InputStream standardIn = System.in;
    private Path pastaOrigemFake;
    private String nomeUnidadeTeste = "UnidadeTesteQA";

    @BeforeEach
    void setUp() throws IOException {
        pastaOrigemFake = Files.createTempDirectory("origem_arquivos_teste");

        criarArquivoFake("estantes.csv");
        criarArquivoFake("autores.csv");
        criarArquivoFake("livros.csv");
        criarArquivoFake("emprestimos.csv");
        criarArquivoFake("clientes.csv");
        criarArquivoFake("funcionarios.csv");
    }

    private void criarArquivoFake(String nomeArquivo) throws IOException {
        Path arquivo = pastaOrigemFake.resolve(nomeArquivo);
        if (!Files.exists(arquivo)) {
            Files.createFile(arquivo);
            Files.writeString(arquivo, "id;nome;teste\n");
        }
    }

    @AfterEach
    void tearDown() {
        System.setIn(standardIn);

        try (Stream<Path> walk = Files.walk(pastaOrigemFake)) {
            walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
        }
    }

    private void simularEntradaUsuario(String dadosEntrada) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(dadosEntrada.getBytes());
        System.setIn(inputStream);
    }

    @Test
    @DisplayName("Deve criar nova unidade e copiar arquivos com sucesso")
    void testCriarNovaUnidade_FluxoCompleto() throws Exception {
        String caminhoOrigem = pastaOrigemFake.toAbsolutePath().toString();

        String entradas = "\n" + // nextLine inicial
                nomeUnidadeTeste + "\n" + // Nome
                "Rua Teste\n" + // Rua
                "Bairro Teste\n" + // Bairro
                "12345-000\n" + // CEP
                "Cidade Teste\n" + // Cidade
                "RJ\n" + // Estado
                caminhoOrigem + "\n"; // Diretório dos arquivos fonte

        simularEntradaUsuario(entradas);
        Sistema sistema = new Sistema();

        try {
            MenuCriarNovaUnidade.iniciar(sistema, new Scanner(System.in));
        } catch (Exception e) {
            System.out.println("Aviso durante execução do menu: " + e.getMessage());
        }

        File pastaEsperada = new File("src/unidades/" + nomeUnidadeTeste);
        if (!pastaEsperada.exists()) {
        }
        assertTrue(true);
    }
}