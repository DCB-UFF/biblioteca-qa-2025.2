package biblioteca.biblioteca;

import biblioteca.livros.Acervo;
import biblioteca.biblioteca.Endereco;
import biblioteca.biblioteca.Sistema;
import biblioteca.biblioteca.Unidade;
import biblioteca.pessoas.Cliente;
import biblioteca.pessoas.Funcionario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UnidadeTest {
    private final Path PATH_UNIDADES_DIR = Paths.get("src/unidades");
    private final Path PATH_ARQUIVO_CSV = PATH_UNIDADES_DIR.resolve("unidades.csv");

    @BeforeEach
    void setUp() throws IOException {
        if (!Files.exists(PATH_UNIDADES_DIR)) {
            Files.createDirectories(PATH_UNIDADES_DIR);
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Cobre todos os Getters e Setters (Preenche as linhas vermelhas simples)")
    void testGettersSetters() {
        Unidade u = new Unidade("1", "Niteroi", "Rua A", "Bairro B", "123", "Cidade C", "RJ");

        u.setNome("Nova Niteroi");
        assertEquals("Nova Niteroi", u.getNome());

        Endereco novoEnd = new Endereco("Rua X", "Bairro Y", "000", "Cid", "UF");
        u.setEnd(novoEnd);
        assertEquals("Rua X", u.getEnd().getRua());

        u.setPath("caminho/novo");
        assertEquals("caminho/novo", u.getPath());

        ArrayList<Cliente> clientes = new ArrayList<>();
        u.setClientes(clientes);
        assertSame(clientes, u.getClientes());

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        u.setFuncionarios(funcionarios);
        assertSame(funcionarios, u.getFuncionarios());

        Acervo acervo = new Acervo();
        u.setAcervo(acervo);
        assertSame(acervo, u.getAcervo());

        String str = u.toString();
        assertTrue(str.contains("Nova Niteroi"));
        assertTrue(str.contains("Rua X"));
    }

    @Test
    @DisplayName("Deve remover uma unidade do arquivo CSV")
    void testRemoverUnidade() throws IOException {
        String conteudoInicial = "1,Niteroi,R,B,C,Cid,ES\n" +
                "2,Rio,R,B,C,Cid,ES";
        Files.writeString(PATH_ARQUIVO_CSV, conteudoInicial);

        Sistema sistemaMock = new Sistema();
        Unidade.removerUnidade(sistemaMock, 2);

        assertTrue(Files.exists(PATH_ARQUIVO_CSV));
        String conteudoFinal = Files.readString(PATH_ARQUIVO_CSV);

        assertTrue(conteudoFinal.contains("Niteroi"), "A unidade 1 deveria ficar");
        assertFalse(conteudoFinal.contains("Rio"), "A unidade 2 deveria ter sido removida");
    }

    @Test
    @DisplayName("Deve ler o arquivo de unidades corretamente")
    void testLeitorUnidades() throws IOException {
        // --- ARRANGE ---
        // CORREÇÃO: Adicionamos um cabeçalho falso na primeira linha.
        // O leitorUnidades ignora a primeira linha e lê a segunda.
        String csv = "id,nome,rua,bairro,cep,cidade,estado\n" + // Linha 1 (Cabeçalho ignorado)
                "1,Niteroi,R,B,C,Cid,ES";                  // Linha 2 (Dados reais)

        Files.writeString(PATH_ARQUIVO_CSV, csv);

        // --- ACT ---
        Sistema sistema = Unidade.leitorUnidades();

        // --- ASSERT ---
        assertNotNull(sistema);

        // Agora deve ser FALSE (não está vazia), pois leu a segunda linha
        assertFalse(sistema.getUnidades().isEmpty(), "A lista de unidades não deveria estar vazia");

        // Verifica se os dados batem
        assertEquals("Niteroi", sistema.getUnidades().get(0).getNome());
    }

    @Test
    @DisplayName("Deve criar a pasta da unidade e os 6 arquivos CSV dentro")
    void testCriarPastaUnidade() throws IOException {
        String idTeste = "999";
        Path pastaEsperada = PATH_UNIDADES_DIR.resolve("un" + idTeste);

        apagarPastaRecursiva(pastaEsperada);

        Unidade.criarPastaUnidade(idTeste);

        assertTrue(Files.exists(pastaEsperada), "A pasta src/unidades/un999 deveria existir");
        assertTrue(Files.exists(pastaEsperada.resolve("livros.csv")));
        assertTrue(Files.exists(pastaEsperada.resolve("autores.csv")));
        assertTrue(Files.exists(pastaEsperada.resolve("clientes.csv")));
        assertTrue(Files.exists(pastaEsperada.resolve("funcionarios.csv")));
        assertTrue(Files.exists(pastaEsperada.resolve("estantes.csv")));
        assertTrue(Files.exists(pastaEsperada.resolve("emprestimos.csv")));

        apagarPastaRecursiva(pastaEsperada);
    }

    @Test
    @DisplayName("Deve acionar o catch de Exception ao tentar copiar arquivo inexistente")
    void testCopiarArquivo_Erro() {
        File origemInexistente = new File("arquivo_que_nao_existe_123.txt");
        File destino = new File("destino_qualquer.txt");

        assertDoesNotThrow(() -> Unidade.copiarArquivo(origemInexistente, destino));
    }

    private void apagarPastaRecursiva(Path path) throws IOException {
        if (Files.exists(path)) {
            try (Stream<Path> walk = Files.walk(path)) {
                walk.sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            }
        }
    }

    @Test
    @DisplayName("Deve criar o arquivo unidades.csv e cabeçalho se ele não existir")
    void testLeitorUnidades_CriaArquivoSeNaoExistir() throws IOException {
        if (Files.exists(PATH_ARQUIVO_CSV)) {
            Files.delete(PATH_ARQUIVO_CSV);
        }

        assertFalse(Files.exists(PATH_ARQUIVO_CSV), "O arquivo não deveria existir antes do teste");
        Unidade.leitorUnidades();

        assertTrue(Files.exists(PATH_ARQUIVO_CSV), "O sistema deveria ter recriado o arquivo automaticamente.");

        String conteudo = Files.readString(PATH_ARQUIVO_CSV);
        String cabecalhoEsperado = "id,nome,rua,bairro,cep,cidade,estado";

        assertTrue(conteudo.trim().contains(cabecalhoEsperado),
                "O arquivo novo deveria conter o cabeçalho padrão.");
    }
}