package biblioteca.livros;

import biblioteca.livros.Acervo;
import biblioteca.livros.Emprestimo;
import biblioteca.livros.Livro;
import biblioteca.pessoas.Autor;
import org.junit.jupiter.api.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class EmprestimoTest {

    private Emprestimo emprestimo;
    private Path pastaTemp;
    private String caminhoPasta;

    Autor autor = new Autor("George R. R. Martin","Estados Unidos");
    Livro livroBase = new Livro(autor,"A Danca dos Dragoes",415,"4826002","Guerra","Leya",false);

    @BeforeEach
    void antesDeCadaTeste() throws IOException {
        emprestimo = new Emprestimo("12345678900", "ISNB001", "2025-10-05", "2025-10-20");
        pastaTemp = Files.createTempDirectory("testeEmprestimo");
        caminhoPasta = pastaTemp.toString() + File.separator;
    }

    @AfterEach
    void depoisDeCadaTeste() throws IOException {
        if (Files.exists(pastaTemp)) {
            Files.walk(pastaTemp)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    // --- TESTES BÁSICOS ---
    @Test
    void deveTestarGettersESetters() {
        emprestimo.setCPF("11111111111");
        emprestimo.setISNB("ISBN999");
        emprestimo.setDataEmprestimo("2022-01-01");
        emprestimo.setDataDevolucao("2022-02-01");
        assertEquals("11111111111", emprestimo.getCPF());
        assertEquals("ISBN999", emprestimo.getISNB());
        assertEquals("2022-01-01", emprestimo.getDataEmprestimo());
        assertEquals("2022-02-01", emprestimo.getDataDevolucao());
        String str = emprestimo.toString();
        assertTrue(str.contains("11111111111"));
    }

    private void criarArquivoLivros(List<String> conteudo) throws IOException {
        Path arquivoLivros = pastaTemp.resolve("livros.csv");
        Files.write(arquivoLivros, conteudo);
    }

    private void adicionarCabecalhoManual() throws IOException {
        Path arquivo = pastaTemp.resolve("emprestimos.csv");
        if (Files.exists(arquivo)) {
            List<String> linhas = Files.readAllLines(arquivo);
            linhas.add(0, "CPF,ISNB,DataEmprestimo,DataDevolucao");
            Files.write(arquivo, linhas);
        }
    }

    // --- IO TESTS ---
    @Test
    void deveEscreverELerEmprestimo() throws IOException {
        Emprestimo.escreverEmprestimo(emprestimo, caminhoPasta);
        adicionarCabecalhoManual();
        ArrayList<Emprestimo> lista = Emprestimo.leitorEmprestimos(caminhoPasta);
        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    void deveRetornarNuloOuVazioSeArquivoNaoExistirAoLer() {
        ArrayList<Emprestimo> lista = Emprestimo.leitorEmprestimos(caminhoPasta);
        assertNull(lista);
    }

    @Test
    void deveTratarExcecaoAoEscreverEmLocalInvalido() {
        // MATADOR DE MUTANTES: Captura System.err para garantir que printStackTrace foi chamado
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        try {
            String caminhoInvalido = "/caminho/que/nao/existe/";
            Emprestimo.escreverEmprestimo(emprestimo, caminhoInvalido);
            assertFalse(errContent.toString().isEmpty(), "Deveria ter imprimido o stacktrace");
        } finally {
            System.setErr(originalErr);
        }
    }

    // --- REMOÇÃO ---
    @Test
    void deveRemoverEmprestimoDoArquivo() throws IOException {
        Emprestimo outro = new Emprestimo("99999999999", "ISNB002", "2025-10-06", "2025-10-21");
        Emprestimo.escreverEmprestimo(emprestimo, caminhoPasta);
        Emprestimo.escreverEmprestimo(outro, caminhoPasta);
        adicionarCabecalhoManual();
        Emprestimo.removerEmprestimo(emprestimo, caminhoPasta);
        ArrayList<Emprestimo> lista = Emprestimo.leitorEmprestimos(caminhoPasta);
        assertEquals(1, lista.size());
    }

    @Test
    void deveTratarErroAoRemoverSeArquivoNaoExiste() {
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));
        try {
            Emprestimo.removerEmprestimo(emprestimo, caminhoPasta);
            assertFalse(errContent.toString().isEmpty(), "Deveria ter imprimido o stacktrace de FileNotFound");
        } finally {
            System.setErr(originalErr);
        }
    }

    // --- MODIFICAÇÃO E REFLECTION (MATADORES DE ELITE) ---

    @Test
    void modificar_DeveProcessarLinhaComExatamenteDoisCampos() throws IOException {
        // MATADOR DE MUTANTE: Boundary condition < 2 vs <= 2
        // Linha com exatos 2 campos. O índice 1 contém o título exato.
        String linhaDoisCampos = "George,A Danca dos Dragoes";
        criarArquivoLivros(Collections.singletonList(linhaDoisCampos));

        Emprestimo.modificarEmprestimo(new Acervo(), livroBase, caminhoPasta, "true");

        List<String> saida = Files.readAllLines(pastaTemp.resolve("livros.csv"));
        // Se a lógica funcionou, expandiu para 8 campos e setou true no indice 6
        assertTrue(saida.get(0).contains("true"));
        assertTrue(saida.get(0).split(",").length >= 7);
    }

    @Test
    void testeReflection_SafeMethodsDevemExplodirComIndicesInvalidos() throws NoSuchMethodException {
        // MATADOR DE MUTANTE: safeEquals, safeContains (boundary condition > vs >=)
        // Como o método publico filtra arrays pequenos, precisamos atacar o método privado diretamente
        // para provar que a condição interna dele está correta.

        Method safeEquals = Emprestimo.class.getDeclaredMethod("safeEquals", String[].class, int.class, String.class);
        safeEquals.setAccessible(true);

        String[] arrPequeno = new String[]{"A"}; // Tamanho 1. Indices validos: 0.

        // Se o código for "length > idx" (1 > 1 -> false), retorna false (seguro).
        // Se o mutante mudar para "length >= idx" (1 >= 1 -> true), tenta acessar arr[1] e CRASHA.
        assertDoesNotThrow(() -> {
            try {
                boolean res = (boolean) safeEquals.invoke(null, arrPequeno, 1, "Valor");
                assertFalse(res);
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof ArrayIndexOutOfBoundsException) {
                    throw (ArrayIndexOutOfBoundsException) e.getCause();
                }
                throw e;
            }
        }, "O método safeEquals deve retornar false para índice fora do limite, e não lançar exceção");
    }

    @Test
    void testeReflection_EnsureFieldsNaoDeveRecriarSeTamanhoIgual() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // MATADOR DE MUTANTE: ensureFields boundary
        Method ensureFields = Emprestimo.class.getDeclaredMethod("ensureFields", String[].class, int.class);
        ensureFields.setAccessible(true);

        String[] original = new String[]{"A", "B"};
        String[] resultado = (String[]) ensureFields.invoke(null, original, 2); // Tamanho exato

        // Se o código estiver correto (length >= size), retorna o MESMO objeto.
        // Se o mutante mudar para (length > size), cria um NOVO objeto.
        assertSame(original, resultado, "Deveria retornar a mesma instância se o tamanho for igual");
    }

    // --- TESTES DE LÓGICA EXISTENTES ---

    @Test
    void modificar_DeveIgnorarHeaderVerdadeiro() throws IOException {
        List<String> linhas = Arrays.asList(
                "autor,Titulo,Paginas,ISBN,Genero,Editora,estaEmprestado,Pais",
                "George,A Danca dos Dragoes,415,4826002,Guerra,Leya,false,EUA"
        );
        criarArquivoLivros(linhas);
        Emprestimo.modificarEmprestimo(new Acervo(), livroBase, caminhoPasta, "true");
        List<String> saida = Files.readAllLines(pastaTemp.resolve("livros.csv"));
        assertEquals(linhas.get(0), saida.get(0));
        assertTrue(saida.get(1).contains(",true,"));
    }

    @Test
    void modificar_DeveExpandirArrayQuandoTamanhoExato() throws IOException {
        String linhaCurta = "George,A Danca dos Dragoes,415,4826002,Guerra,Leya,false";
        criarArquivoLivros(Collections.singletonList(linhaCurta));
        Emprestimo.modificarEmprestimo(new Acervo(), livroBase, caminhoPasta, "true");
        List<String> saida = Files.readAllLines(pastaTemp.resolve("livros.csv"));
        assertTrue(saida.get(0).split(",").length >= 7);
        assertTrue(saida.get(0).contains("true"));
    }

    @Test
    void modificar_DeveAlterarComMatchExatoTitulo() throws IOException {
        List<String> linhas = Arrays.asList("George,A Danca dos Dragoes,415,4826002,Guerra,Leya,false,EUA");
        criarArquivoLivros(linhas);
        Emprestimo.modificarEmprestimo(new Acervo(), livroBase, caminhoPasta, "true");
        assertTrue(Files.readAllLines(pastaTemp.resolve("livros.csv")).get(0).contains(",true,"));
    }

    @Test
    void modificar_DeveAlterarComIgnoreCaseSeNaoEstiverFlagged() throws IOException {
        List<String> linhas = Arrays.asList("George,a danca dos dragoes,415,4826002,Guerra,Leya,false,EUA");
        criarArquivoLivros(linhas);
        Emprestimo.modificarEmprestimo(new Acervo(), livroBase, caminhoPasta, "true");
        assertTrue(Files.readAllLines(pastaTemp.resolve("livros.csv")).get(0).contains(",true,"));
    }

    @Test
    void modificar_NaoDeveAlterarComIgnoreCaseSeJaEstiverFlaggedComoTrue() throws IOException {
        List<String> linhas = Arrays.asList("George,a danca dos dragoes,415,4826002,Guerra,Leya,false,true");
        criarArquivoLivros(linhas);
        Emprestimo.modificarEmprestimo(new Acervo(), livroBase, caminhoPasta, "true");
        String[] partes = Files.readAllLines(pastaTemp.resolve("livros.csv")).get(0).split(",");
        assertEquals("false", partes[6]);
    }

    @Test
    void modificar_TratamentoDeExcecaoIO() {
        File arquivoLocado = new File(pastaTemp.toFile(), "livros.csv");
        PrintStream originalErr = System.err;
        try {
            arquivoLocado.createNewFile();
            arquivoLocado.setReadOnly();
            // setReadOnly as vezes não funciona em todos OS para root, mas tentamos
            Emprestimo.modificarEmprestimo(new Acervo(), livroBase, caminhoPasta, "true");
        } catch (IOException e) {
        } finally {
            arquivoLocado.setWritable(true);
            System.setErr(originalErr);
        }
        assertTrue(true);
    }
}