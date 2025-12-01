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
        Files.walk(pastaTemp)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }



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
    }

    // --- MÉTODO AUXILIAR PARA PROBLEMA DO CABEÇALHO ---
    private void adicionarCabecalhoManual() throws IOException {
        Path arquivo = pastaTemp.resolve("emprestimos.csv");
        if (Files.exists(arquivo)) {
            List<String> linhas = Files.readAllLines(arquivo);

            linhas.add(0, "CPF,ISNB,DataEmprestimo,DataDevolucao");
            Files.write(arquivo, linhas);
        }
    }

    @Test
    void deveEscreverELerEmprestimo() throws IOException {
        Emprestimo.escreverEmprestimo(emprestimo, caminhoPasta);

        adicionarCabecalhoManual();

        ArrayList<Emprestimo> lista = Emprestimo.leitorEmprestimos(caminhoPasta);

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("12345678900", lista.get(0).getCPF());
    }

    @Test
    void deveRetornarNuloOuVazioSeArquivoNaoExistirAoLer() {

        ArrayList<Emprestimo> lista = Emprestimo.leitorEmprestimos(caminhoPasta);

        assertNull(lista);
    }

    @Test
    void deveTratarExcecaoAoEscreverEmLocalInvalido() {

        String caminhoInvalido = "/caminho/que/nao/existe/";
        assertDoesNotThrow(() -> Emprestimo.escreverEmprestimo(emprestimo, caminhoInvalido));

    }

    // --- TESTES DE REMOÇÃO ---

    @Test
    void deveRemoverEmprestimoDoArquivo() throws IOException {
        Emprestimo outro = new Emprestimo("99999999999", "ISNB002", "2025-10-06", "2025-10-21");

        Emprestimo.escreverEmprestimo(emprestimo, caminhoPasta);
        Emprestimo.escreverEmprestimo(outro, caminhoPasta);

        adicionarCabecalhoManual();

        Emprestimo.removerEmprestimo(emprestimo, caminhoPasta);

        ArrayList<Emprestimo> lista = Emprestimo.leitorEmprestimos(caminhoPasta);

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("99999999999", lista.get(0).getCPF());
    }
    @Test
    void deveTratarErroAoRemoverSeArquivoNaoExiste() {

        assertDoesNotThrow(() -> Emprestimo.removerEmprestimo(emprestimo, caminhoPasta));
    }



    private void criarArquivoLivros(List<String> conteudo) throws IOException {
        Path arquivoLivros = pastaTemp.resolve("livros.csv");
        Files.write(arquivoLivros, conteudo);
    }

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
                "George,a danca dos dragoes,415,4826002,Guerra,Leya,false,EUA" // Caixa baixa
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

        String linha = saida.get(1);
        String[] partes = linha.split(",");
        assertEquals("false", partes[6]);
    }

    @Test
    void modificar_DeveAlterarComContainsSeBooleanoTrue() throws IOException {

        List<String> linhas = Arrays.asList(
                "H,T,P,I,G,E,S,P",
                "George,Dragoes,415,4826002,Guerra,Leya,false,EUA" // "Dragoes" está contido em "A Danca dos Dragoes"? Não.

        );


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

        List<String> linhas = Arrays.asList(
                "H,T,P,I,G,E,S,P",
                ",,,,,,,"
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

        assertDoesNotThrow(() ->
                Emprestimo.modificarEmprestimo(new Acervo(), livroBase, "/caminho/errado/", "true")
        );
    }



    @Test
    void ensureFields_DeveExpandirArrayCurto() throws IOException {

        List<String> linhas = Arrays.asList(
                "H,T,P,I,G,E,S,P",
                "George,A Danca dos Dragoes,415"
        );
        criarArquivoLivros(linhas);

        Emprestimo.modificarEmprestimo(new Acervo(), livroBase, caminhoPasta, "true");

        List<String> saida = Files.readAllLines(pastaTemp.resolve("livros.csv"));
        String linhaEditada = saida.get(1);

        long virgulas = linhaEditada.chars().filter(ch -> ch == ',').count();
        assertTrue(virgulas >= 7);
        assertTrue(linhaEditada.contains("true"));
    }
}