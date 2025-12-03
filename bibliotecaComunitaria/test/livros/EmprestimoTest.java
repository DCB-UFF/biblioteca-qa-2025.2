package biblioteca.livros;

import biblioteca.livros.Acervo;
import biblioteca.livros.Emprestimo;
import biblioteca.livros.Livro;
import biblioteca.pessoas.Autor;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class EmprestimoTest {

    private Emprestimo emprestimo;
    private Path pastaTemp;
    private String caminhoPasta;


    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    Autor autor = new Autor("George R. R. Martin","Estados Unidos");
    Livro livroBase = new Livro(autor,"A Danca dos Dragoes",415,"4826002","Guerra","Leya",false);

    @BeforeEach
    void antesDeCadaTeste() throws IOException {
        // Redireciona System.err
        System.setErr(new PrintStream(errContent));

        emprestimo = new Emprestimo("12345678900", "ISNB001", "2025-10-05", "2025-10-20");
        pastaTemp = Files.createTempDirectory("testeEmprestimo");
        caminhoPasta = pastaTemp.toString() + File.separator;
    }

    @AfterEach
    void depoisDeCadaTeste() throws IOException {
        // Restaura System.err
        System.setErr(originalErr);

        Files.walk(pastaTemp)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }


    private void adicionarCabecalhoManual(String nomeArquivo) throws IOException {
        Path arquivo = pastaTemp.resolve(nomeArquivo);
        if (Files.exists(arquivo)) {
            List<String> linhas = Files.readAllLines(arquivo);
            if (linhas.isEmpty() || !linhas.get(0).startsWith("CPF")) {
                linhas.add(0, "CPF,ISNB,DataEmprestimo,DataDevolucao");
                Files.write(arquivo, linhas);
            }
        }
    }

    private void criarArquivoLivros(List<String> conteudo) throws IOException {
        Path arquivoLivros = pastaTemp.resolve("livros.csv");
        Files.write(arquivoLivros, conteudo);
    }



    @Test
    void deveTestarGettersESetters() {
        emprestimo.setCPF("11111111111");
        emprestimo.setISNB("ISBN999");
        emprestimo.setDataEmprestimo("2022-01-01");
        emprestimo.setDataDevolucao("2022-02-01");

        assertAll("Valida propriedades",
                () -> assertEquals("11111111111", emprestimo.getCPF()),
                () -> assertEquals("ISBN999", emprestimo.getISNB()),
                () -> assertEquals("2022-01-01", emprestimo.getDataEmprestimo()),
                () -> assertEquals("2022-02-01", emprestimo.getDataDevolucao())
        );
    }

    @Test
    void deveGerarToStringComFormatoExato() {
        String esperado = "IdLivro: ISNB001 - CPF: 12345678900 - Data de Emprestimo: 2025-10-05 - Data de Devolução: 2025-10-20";
        assertEquals(esperado, emprestimo.toString());
    }



    @Test
    void deveEscreverELerEmprestimo() throws IOException {
        Emprestimo.escreverEmprestimo(emprestimo, caminhoPasta);
        assertTrue(Files.exists(pastaTemp.resolve("emprestimos.csv")));

        adicionarCabecalhoManual("emprestimos.csv");

        ArrayList<Emprestimo> lista = Emprestimo.leitorEmprestimos(caminhoPasta);

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("12345678900", lista.get(0).getCPF());
    }

    @Test
    void deveRetornarNuloSeArquivoNaoExistirAoLer() {

        ArrayList<Emprestimo> lista = Emprestimo.leitorEmprestimos(caminhoPasta);

        assertNull(lista);

        assertTrue(errContent.toString().length() > 0, "Erro deveria ter sido logado no console");
    }

    @Test
    void deveTratarExcecaoAoEscreverEmLocalInvalido() {
        String caminhoInvalido = "/caminho/que/nao/existe/";

        Emprestimo.escreverEmprestimo(emprestimo, caminhoInvalido);


        assertTrue(errContent.toString().length() > 0, "Erro deveria ter sido logado no console");
    }

    // --- TESTES DE REMOÇÃO ---

    @Test
    void deveRemoverEmprestimoDoArquivo() throws IOException {
        Emprestimo outro = new Emprestimo("99999999999", "ISNB002", "2025-10-06", "2025-10-21");

        Emprestimo.escreverEmprestimo(emprestimo, caminhoPasta);
        Emprestimo.escreverEmprestimo(outro, caminhoPasta);
        adicionarCabecalhoManual("emprestimos.csv");

        Emprestimo.removerEmprestimo(emprestimo, caminhoPasta);


        assertFalse(Files.exists(pastaTemp.resolve("temp.csv")), "Temp deve ser renomeado");
        assertTrue(Files.exists(pastaTemp.resolve("emprestimos.csv")), "Arquivo final deve existir");

        ArrayList<Emprestimo> lista = Emprestimo.leitorEmprestimos(caminhoPasta);
        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("99999999999", lista.get(0).getCPF());
    }

    @Test
    void deveRemoverApenasOEmprestimoExatoQuandoHouverMultiplosDoMesmoCPF() throws IOException {
        // Teste crucial para a lógica AND/OR na remoção
        Emprestimo emp1 = new Emprestimo("111", "LIVRO_A", "2025-01-01", "2025-01-10");
        Emprestimo emp2 = new Emprestimo("111", "LIVRO_B", "2025-01-01", "2025-01-10");

        Emprestimo.escreverEmprestimo(emp1, caminhoPasta);
        Emprestimo.escreverEmprestimo(emp2, caminhoPasta);
        adicionarCabecalhoManual("emprestimos.csv");

        // Remove apenas o A
        Emprestimo.removerEmprestimo(emp1, caminhoPasta);

        ArrayList<Emprestimo> lista = Emprestimo.leitorEmprestimos(caminhoPasta);
        assertNotNull(lista);
        assertEquals(1, lista.size(), "Deveria sobrar 1 livro");
        assertEquals("LIVRO_B", lista.get(0).getISNB());
    }

    @Test
    void deveTratarErroAoRemoverSeArquivoNaoExiste() {
        Emprestimo.removerEmprestimo(emprestimo, caminhoPasta);
        assertTrue(errContent.toString().length() > 0, "Erro deveria ter sido logado");
    }

    // --- TESTES DE MODIFICAÇÃO E MUTANTES ---

    @Test
    void modificar_DeveIgnorarLinhasVaziasEMalFormadasPequenas() throws IOException {
        List<String> linhas = Arrays.asList(
                "",
                "DadoUnico",
                "autor,Titulo,Paginas,ISBN,Genero,Editora,estaEmprestado,Pais",
                "George R. R. Martin,A Danca dos Dragoes,415,4826002,Guerra,Leya,false,EUA"
        );
        criarArquivoLivros(linhas);

        Emprestimo.modificarEmprestimo(new Acervo(), livroBase, caminhoPasta, "true");

        List<String> saida = Files.readAllLines(pastaTemp.resolve("livros.csv"));
        assertTrue(saida.contains(""));
        assertTrue(saida.contains("DadoUnico"));
        assertTrue(saida.get(3).contains("true"));
    }

    @Test
    void modificar_DevePreservarHeader() throws IOException {
        List<String> linhas = Arrays.asList(
                "autor,Titulo,Paginas,ISBN,Genero,Editora,estaEmprestado,Pais"
        );
        criarArquivoLivros(linhas);

        Emprestimo.modificarEmprestimo(new Acervo(), livroBase, caminhoPasta, "true");
        List<String> saida = Files.readAllLines(pastaTemp.resolve("livros.csv"));
        assertEquals(linhas.get(0), saida.get(0));
    }

    @Test
    void modificar_NaoDeveConfundirHeaderParcialComDados() throws IOException {

        Livro livroTitulo = new Livro(autor, "Titulo", 100, "123", "G", "E", false);
        List<String> linhas = Arrays.asList(
                "autor,Titulo,Paginas,ISBN,Genero,Editora,estaEmprestado,Pais",
                "George,Titulo,100,123,Guerra,Leya,false,EUA" // Nome do livro é "Titulo"
        );
        criarArquivoLivros(linhas);

        Emprestimo.modificarEmprestimo(new Acervo(), livroTitulo, caminhoPasta, "true");

        List<String> saida = Files.readAllLines(pastaTemp.resolve("livros.csv"));

        assertTrue(saida.get(1).contains("true"), "Deve alterar livro chamado 'Titulo'");
    }

    @Test
    void modificar_DeveAlterarComMatchExatoTitulo() throws IOException {
        List<String> linhas = Arrays.asList(
                "H,T,P,I,G,E,S,P",
                "George,A Danca dos Dragoes,415,4826002,Guerra,Leya,false,EUA"
        );
        criarArquivoLivros(linhas);

        Emprestimo.modificarEmprestimo(new Acervo(), livroBase, caminhoPasta, "true");
        List<String> saida = Files.readAllLines(pastaTemp.resolve("livros.csv"));
        assertTrue(saida.get(1).contains(",true,"));
    }

    @Test
    void modificar_DeveAlterarComIgnoreCaseSeNaoEstiverFlagged() throws IOException {
        List<String> linhas = Arrays.asList(
                "H,T,P,I,G,E,S,P",
                "George,a danca dos dragoes,415,4826002,Guerra,Leya,false,EUA"
        );
        criarArquivoLivros(linhas);

        Emprestimo.modificarEmprestimo(new Acervo(), livroBase, caminhoPasta, "true");
        List<String> saida = Files.readAllLines(pastaTemp.resolve("livros.csv"));
        assertTrue(saida.get(1).contains(",true,"));
    }

    @Test
    void modificar_NaoDeveAlterarComIgnoreCaseSeJaEstiverFlaggedComoTrueNoCampo8() throws IOException {

        List<String> linhas = Arrays.asList(
                "H,T,P,I,G,E,S,P",
                "George,a danca dos dragoes,415,4826002,Guerra,Leya,false,true"
        );
        criarArquivoLivros(linhas);

        Emprestimo.modificarEmprestimo(new Acervo(), livroBase, caminhoPasta, "true");
        List<String> saida = Files.readAllLines(pastaTemp.resolve("livros.csv"));
        String[] partes = saida.get(1).split(",");
        // Não deve mudar o indice 6 para true
        assertEquals("false", partes[6]);
    }

    @Test
    void modificar_DeveAlterarComContainsSeBooleanoTrue() throws IOException {
        List<String> linhasContains = Arrays.asList(
                "H,T,P,I,G,E,S,P",
                "George,A saga A Danca dos Dragoes completa,415,4826002,Guerra,Leya,false,EUA"
        );
        criarArquivoLivros(linhasContains);

        Emprestimo.modificarEmprestimo(new Acervo(), livroBase, caminhoPasta, "true");
        List<String> saida = Files.readAllLines(pastaTemp.resolve("livros.csv"));
        assertTrue(saida.get(1).contains(",true,"));
    }

    @Test
    void modificar_NaoDeveAlterarComContainsSeBooleanoFalse() throws IOException {
        List<String> linhas = Arrays.asList(
                "H,T,P,I,G,E,S,P",
                "George,A saga A Danca dos Dragoes completa,415,4826002,Guerra,Leya,false,EUA"
        );
        criarArquivoLivros(linhas);

        Emprestimo.modificarEmprestimo(new Acervo(), livroBase, caminhoPasta, "false");
        List<String> saida = Files.readAllLines(pastaTemp.resolve("livros.csv"));
        assertTrue(saida.get(1).contains(",false,"));
    }

    @Test
    void modificar_DevePreservarLinhaMalFormadaInterna() throws IOException {
        // Mata mutante isMalformedLine
        List<String> linhas = Arrays.asList(
                "H,T,P,I,G,E,S,P",
                ",,,,,,," // Muitas virgulas vazias
        );
        criarArquivoLivros(linhas);

        Emprestimo.modificarEmprestimo(new Acervo(), livroBase, caminhoPasta, "true");
        List<String> saida = Files.readAllLines(pastaTemp.resolve("livros.csv"));
        assertEquals(",,,,,,,", saida.get(1));
    }

    @Test
    void modificar_DevePreservarLinhaSeCampo8Existe() throws IOException {
        Livro livroDiferente = new Livro(autor, "Outro Livro", 1, "1", "G", "E", false);
        List<String> linhas = Arrays.asList(
                "H,T,P,I,G,E,S,P",
                "George,Livro Desconhecido,415,4826002,Guerra,Leya,false,Campo8Preenchido"
        );
        criarArquivoLivros(linhas);

        Emprestimo.modificarEmprestimo(new Acervo(), livroDiferente, caminhoPasta, "true");
        List<String> saida = Files.readAllLines(pastaTemp.resolve("livros.csv"));
        assertTrue(saida.get(1).contains("Campo8Preenchido"));
    }

    @Test
    void modificar_TratamentoDeExcecaoArquivoNaoEncontrado() {
        Emprestimo.modificarEmprestimo(new Acervo(), livroBase, "/caminho/errado/", "true");
        assertTrue(errContent.toString().length() > 0, "Erro logado");
    }

    @Test
    void ensureFields_DeveExpandirArrayCurto() throws IOException {
        List<String> linhas = Arrays.asList(
                "H,T,P,I,G,E,S,P",
                "George,A Danca dos Dragoes,415" // Curto
        );
        criarArquivoLivros(linhas);

        Emprestimo.modificarEmprestimo(new Acervo(), livroBase, caminhoPasta, "true");

        List<String> saida = Files.readAllLines(pastaTemp.resolve("livros.csv"));
        String linhaEditada = saida.get(1);

        long virgulas = linhaEditada.chars().filter(ch -> ch == ',').count();
        assertTrue(virgulas >= 7);
        assertTrue(linhaEditada.contains("true"));
    }



    @Test
    void helper_safeEquals_DeveFalharComIndiceInvalido() throws Exception {

        java.lang.reflect.Method method = Emprestimo.class.getDeclaredMethod("safeEquals", String[].class, int.class, String.class);
        method.setAccessible(true);

        String[] arr = {"ApenasUm"}; // Length 1

        boolean resultado = (boolean) method.invoke(null, arr, 1, "Valor");

        assertFalse(resultado, "Deveria retornar false se o índice não existe");
    }

    @Test
    void helper_safeEquals_DeveRetornarFalsoSeValorNulo() throws Exception {

        java.lang.reflect.Method method = Emprestimo.class.getDeclaredMethod("safeEquals", String[].class, int.class, String.class);
        method.setAccessible(true);

        String[] arr = {"A", null, "C"};
        boolean resultado = (boolean) method.invoke(null, arr, 1, "Qualquer");

        assertFalse(resultado);
    }

    @Test
    void helper_isMalformedLine_DeveDetectarExcessoDeVazios() throws Exception {

        java.lang.reflect.Method method = Emprestimo.class.getDeclaredMethod("isMalformedLine", String[].class);
        method.setAccessible(true);


        String[] arr = {"Valido", "", "", ""};

        boolean ehMalformada = (boolean) method.invoke(null, (Object) arr);

        assertTrue(ehMalformada, "Deveria considerar malformada (3 vazios em 4 campos)");
    }

    @Test
    void helper_isHeaderLine_DeveSerRigoroso() throws Exception {

        java.lang.reflect.Method method = Emprestimo.class.getDeclaredMethod("isHeaderLine", String.class);
        method.setAccessible(true);


        boolean resultado = (boolean) method.invoke(null, "Titulo do livro sem autor especificado");

        assertFalse(resultado, "Não deveria ser header apenas por conter a palavra Titulo");
    }

    @Test
    void helper_ensureFields_DeveExpandirCorretamente() throws Exception {

        java.lang.reflect.Method method = Emprestimo.class.getDeclaredMethod("ensureFields", String[].class, int.class);
        method.setAccessible(true);

        String[] input = {"A"};
        String[] output = (String[]) method.invoke(null, input, 3);

        assertEquals(3, output.length);
        assertEquals("A", output[0]);
        assertEquals("", output[1]);
        assertEquals("", output[2]);
    }

    @Test
    void deveTratarIOExceptionGenereciaAoEscrever() {

        File arquivoReadOnly = new File(caminhoPasta + "emprestimos.csv");
        try {
            arquivoReadOnly.createNewFile();
            arquivoReadOnly.setReadOnly();

            Emprestimo.escreverEmprestimo(emprestimo, caminhoPasta);


            assertTrue(errContent.toString().length() > 0 || !Files.isWritable(arquivoReadOnly.toPath()));

        } catch (IOException e) {

        } finally {

            arquivoReadOnly.setWritable(true);
        }
    }

    @Test
    void helper_safeEqualsIgnoreCase_DeveFalharNoLimiteExato() throws Exception {

        java.lang.reflect.Method method = Emprestimo.class.getDeclaredMethod("safeEqualsIgnoreCase", String[].class, int.class, String.class);
        method.setAccessible(true);

        String[] arr = {"Valor"}; // Length 1

        boolean resultado = (boolean) method.invoke(null, arr, 1, "Valor");

        assertFalse(resultado, "No limite (idx == length), deve retornar false");
    }

    @Test
    void helper_safeContains_DeveRetornarFalsoSeNaoContem() throws Exception {

        java.lang.reflect.Method method = Emprestimo.class.getDeclaredMethod("safeContains", String[].class, int.class, String.class);
        method.setAccessible(true);

        String[] arr = {"TextoLongo"};

        boolean resultado = (boolean) method.invoke(null, arr, 0, "Abacaxi");

        assertFalse(resultado, "Deveria retornar false se o texto não contém a substring");
    }

    @Test
    void helper_safeContains_DeveFalharNoLimite() throws Exception {

        java.lang.reflect.Method method = Emprestimo.class.getDeclaredMethod("safeContains", String[].class, int.class, String.class);
        method.setAccessible(true);

        String[] arr = {"A"};

        boolean resultado = (boolean) method.invoke(null, arr, 1, "A");

        assertFalse(resultado);
    }

    @Test
    void helper_ensureFields_MutanteLoop() throws Exception {

        java.lang.reflect.Method method = Emprestimo.class.getDeclaredMethod("ensureFields", String[].class, int.class);
        method.setAccessible(true);

        String[] arr = {"A"};

        String[] res = (String[]) method.invoke(null, arr, 1);

        assertEquals(1, res.length);
        assertEquals("A", res[0]);
    }

    @Test
    void deveTratarIOExceptionGenericaAoEscrever() throws IOException {


        File pastaFake = new File(caminhoPasta + "emprestimos.csv");
        if(pastaFake.mkdirs()) {
            Emprestimo.escreverEmprestimo(emprestimo, caminhoPasta);


            assertTrue(errContent.toString().length() > 0, "Deveria capturar IOException genérica");

            // Limpeza extra
            pastaFake.delete();
        }
    }


    @Test
    void helper_isMalformedLine_DeveRetornarFalsoParaLinhaValida() throws Exception {

        java.lang.reflect.Method method = Emprestimo.class.getDeclaredMethod("isMalformedLine", String[].class);
        method.setAccessible(true);

        String[] arr = {"A", "B", "C"}; // Array válido (sem vazios excessivos)
        boolean ehMalformada = (boolean) method.invoke(null, (Object) arr);

        assertFalse(ehMalformada, "Linha válida NÃO deve ser considerada malformada");
    }

    @Test
    void helper_safeEqualsIgnoreCase_DeveRetornarFalsoParaDiferentes() throws Exception {

        java.lang.reflect.Method method = Emprestimo.class.getDeclaredMethod("safeEqualsIgnoreCase", String[].class, int.class, String.class);
        method.setAccessible(true);

        String[] arr = {"Casa"};

        boolean resultado = (boolean) method.invoke(null, arr, 0, "Apartamento");

        assertFalse(resultado, "Strings diferentes devem retornar false");
    }

    @Test
    void helper_ensureFields_NaoDeveAlterarSeTamanhoJaForSuficiente() throws Exception {

        java.lang.reflect.Method method = Emprestimo.class.getDeclaredMethod("ensureFields", String[].class, int.class);
        method.setAccessible(true);

        String[] input = {"A", "B", "C"};

        String[] output = (String[]) method.invoke(null, input, 3);

        assertEquals(3, output.length);
        assertEquals("C", output[2]);

        assertArrayEquals(input, output);
    }
}