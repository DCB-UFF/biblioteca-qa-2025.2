package test.java.biblioteca.livros;

import biblioteca.biblioteca.Unidade;
import biblioteca.livros.Acervo;
import biblioteca.livros.Emprestimo;
import biblioteca.livros.Estante;
import biblioteca.livros.Livro;
import biblioteca.pessoas.Autor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class AcervoTest {

    private static final String ISBN = "123456789";
    private static final String TITULO = "Livro Teste";
    private static final String NOME_AUTOR = "Autor Teste";

    private Path tempDir;
    private String pathComBarra;

    private Acervo acervo;
    private Livro livro;
    private Autor autor;
    private Unidade unidade;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() throws IOException {
        System.setOut(new PrintStream(outContent));

        tempDir = Files.createTempDirectory("teste_acervo_merge");
        pathComBarra = tempDir.toAbsolutePath().toString() + File.separator;

        criarArquivoCSV("livros.csv", "Autor;Titulo;Paginas;ISBN;Genero;Editora;Emprestado\n");
        criarArquivoCSV("emprestimos.csv", "CPF;ISBN;DataEmp;DataDev\n");
        criarArquivoCSV("estantes.csv", "ID,Genero\n");
        criarArquivoCSV("autores.csv", "Nome,Pais\n");

        acervo = new Acervo();

        unidade = new Unidade("001", "Unidade Central", "Rua A", "Bairro B", "12345-678", "Cidade C", "Estado D");
        unidade.setPath(pathComBarra);
        unidade.setAcervo(acervo);

        autor = new Autor(NOME_AUTOR);
        livro = new Livro(autor, TITULO, 200, ISBN, "Aventura", "Editora Teste", true);

        Estante estante = new Estante(1, "Aventura");
        acervo.addEstante(estante);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        try (Stream<Path> walk = Files.walk(tempDir)) {
            walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void criarArquivoCSV(String nome, String conteudo) throws IOException {
        Files.writeString(tempDir.resolve(nome), conteudo);
    }

    @Test
    @DisplayName("Deve adicionar livro no acervo (Memória)")
    void deveAdicionarLivroNoAcervo() {
        acervo.addLivro(livro);
        Livro encontrado = acervo.buscarLivroISNB(ISBN);
        assertNotNull(encontrado);
        assertEquals(TITULO, encontrado.getTitulo());
    }

    @Test
    @DisplayName("Deve buscar livro por ISBN")
    void deveBuscarLivroPorIsbn() {
        acervo.addLivro(livro);
        Livro encontrado = acervo.buscarLivroISNB(ISBN);
        assertNotNull(encontrado);
        assertEquals(ISBN, encontrado.getISBN());
    }

    @Test
    @DisplayName("Deve retornar Null quando ISBN não existir")
    void deveRetornarNullQuandoIsbnNaoExistir() {
        Livro encontrado = acervo.buscarLivroISNB("987654321");
        assertNull(encontrado);
    }

    @Test
    @DisplayName("Deve buscar livros por Autor")
    void deveBuscarLivrosPorAutor() {
        acervo.getAutores().add(autor);
        acervo.addLivro(livro);
        ArrayList<Livro> livrosDoAutor = acervo.buscarLivroAutor(NOME_AUTOR);
        assertFalse(livrosDoAutor.isEmpty());
        assertEquals(1, livrosDoAutor.size());
        assertEquals(TITULO, livrosDoAutor.get(0).getTitulo());
    }

    @Test
    @DisplayName("Deve retornar lista vazia para autor sem livros")
    void deveRetornarListaVaziaParaAutorSemLivros() {
        ArrayList<Livro> livrosDoAutor = acervo.buscarLivroAutor("Autor Inexistente");
        assertTrue(livrosDoAutor.isEmpty());
    }

    @Test
    @DisplayName("ADD LIVRO 2: Cobre a lógica complexa de Autores e Estantes com persistência")
    void testAddLivro_LogicaComplexa() {
        Autor autor1 = new Autor("J.K. Rowling", "UK");
        Livro l1 = new Livro(autor1, "Harry Potter", 300, "ISBN-HP", "Fantasia", "Rocco", false);
        acervo.addLivro2(l1, pathComBarra);
        assertEquals(1, acervo.getAutores().size());
        assertEquals(2, acervo.getEstantes().size());
        assertTrue(Files.exists(tempDir.resolve("livros.csv")));
    }

    @Test
    @DisplayName("Cobre Getters, Setters e Listas (IdsEstantes, Estantes, Emprestimos, Autores)")
    void testGettersSettersComplementares() {
        acervo.setIdsEstantes(100);
        assertEquals(100, acervo.getIdsEstantes());

        ArrayList<Estante> novaListaEstantes = new ArrayList<>();
        acervo.setEstantes(novaListaEstantes);
        assertSame(novaListaEstantes, acervo.getEstantes());

        ArrayList<Emprestimo> novaListaEmprestimos = new ArrayList<>();
        acervo.setEmprestimos(novaListaEmprestimos);
        assertSame(novaListaEmprestimos, acervo.getEmprestimos());

        ArrayList<Autor> novaListaAutores = new ArrayList<>();
        acervo.setAutores(novaListaAutores);
        assertSame(novaListaAutores, acervo.getAutores());
    }

    @Test
    @DisplayName("Cobre as buscas booleanas (buscarAutor e buscarClienteNosEmprestimos)")
    void testBuscasBooleanas() {
        Autor machado = new Autor("Machado de Assis");
        acervo.getAutores().add(machado);
        assertTrue(acervo.buscarAutor("Machado de Assis"));
        assertFalse(acervo.buscarAutor("Clarice Lispector"));

        Emprestimo emp = new Emprestimo("CPF-TESTE-123", "ISBN-X", "Data1", "Data2");
        acervo.getEmprestimos().add(emp);
        assertTrue(acervo.buscarClienteNosEmprestimos("CPF-TESTE-123"));
        assertFalse(acervo.buscarClienteNosEmprestimos("CPF-NAO-EXISTE"));
    }

    @Test
    @DisplayName("EMPRÉSTIMO: Fluxo de Emprestar, Bloqueio e Devolver")
    void testFluxoEmprestimoDevolucao() throws IOException {
        Autor autorTolkien = new Autor("Tolkien", "UK");
        Livro hobbit = new Livro(autorTolkien, "Hobbit", 300, "ISBN-LOTR", "Aventura", "Martins", false);
        acervo.getEstantes().get(0).addLivroNaEstante(hobbit);

        Files.deleteIfExists(tempDir.resolve("emprestimos.csv"));
        Files.createFile(tempDir.resolve("emprestimos.csv"));
        Files.writeString(tempDir.resolve("emprestimos.csv"), "CPF,ISBN,DataE,DataD\n");

        Emprestimo emp = new Emprestimo("CPF123", "ISBN-LOTR", "01/01", "10/01");
        acervo.emprestarLivro(unidade, emp);
        assertTrue(acervo.buscarClienteNosEmprestimos("CPF123"));
        assertTrue(hobbit.isEstaEmprestado());

        Emprestimo emp2 = new Emprestimo("CPF123", "OUTRO", "01/01", "10/01");
        acervo.emprestarLivro(unidade, emp2);
        assertEquals(1, acervo.getEmprestimos().size());

        String conteudoPerfeito = "CPF,ISBN,DataE,DataD\n" + "CPF123,ISBN-LOTR,01/01,10/01";
        Files.writeString(tempDir.resolve("emprestimos.csv"), conteudoPerfeito);

        acervo.devolverLivro(unidade, "CPF123", "ISBN-LOTR");
        assertFalse(acervo.buscarClienteNosEmprestimos("CPF123"));
        assertFalse(hobbit.isEstaEmprestado());
    }

    @Test
    @DisplayName("REMOVER LIVRO: Remove do autor, da estante e do arquivo")
    void testRemoverLivro() throws IOException {
        Autor autorKing = new Autor("King", "EUA");
        Livro it = new Livro(autorKing, "It", 1000, "ISBN-IT", "Aventura", "Suma", false);
        autorKing.addLivro(it);
        acervo.getAutores().add(autorKing);
        acervo.getEstantes().get(0).addLivroNaEstante(it);

        Files.deleteIfExists(tempDir.resolve("livros.csv"));
        Files.createFile(tempDir.resolve("livros.csv"));
        Livro.escreverLivro(it, pathComBarra);

        acervo.removeLivro(it, pathComBarra);
        assertFalse(acervo.getEstantes().get(0).getLivros().contains(it));
        assertFalse(autorKing.getLivrosAutor().contains(it));

        String arquivoFinal = Files.readString(tempDir.resolve("livros.csv"));
        assertFalse(arquivoFinal.contains("ISBN-IT"));
    }

    @Test
    @DisplayName("Testes de Robustez (Null checks)")
    void testAddLivro_Nulls() {
        assertDoesNotThrow(() -> acervo.addLivro(null));
        assertDoesNotThrow(() -> acervo.addLivro2(null, "path"));
    }

    @Test
    @DisplayName("Deve imprimir acervo")
    void deveImprimirAcervo() {
        acervo.imprimirAcervo("Unidade Teste");
        assertTrue(outContent.toString().contains("Acervo da Unidade - Unidade Teste"));
    }

    @Test
    @DisplayName("Adicionar livro com autor nulo")
    void testAddLivroComAutorNulo() {
        Livro livroSemAutor = new Livro(null, "Livro Sem Autor", 100, "ISBN-SA", "Gênero", "Editora", false);
        acervo.addLivro(livroSemAutor);
        assertFalse(acervo.getAutores().isEmpty());
        assertEquals("Desconhecido", acervo.getAutores().get(0).getNome());
    }

    @Test
    @DisplayName("Adicionar livro com autor existente (case-insensitive)")
    void testAddLivroComAutorExistenteCaseInsensitive() {
        Autor autorExistente = new Autor("autor teste");
        Livro outroLivro = new Livro(autorExistente, "Outro Livro", 150, "ISBN-OL", "Aventura", "Editora", false);
        acervo.addLivro(livro); 
        acervo.addLivro(outroLivro);
        assertEquals(1, acervo.getAutores().size());
    }

    @Test
    @DisplayName("Adicionar livro com gênero nulo")
    void testAddLivroComGeneroNulo() {
        Livro livroSemGenero = new Livro(autor, "Livro Sem Gênero", 120, "ISBN-SG", null, "Editora", false);
        acervo.addLivro(livroSemGenero);
        assertTrue(acervo.buscarLivroISNB("ISBN-SG") != null);
    }

    @Test
    @DisplayName("Adicionar livro com estantes nulas")
    void testAddLivroComEstantesNulas() {
        acervo.setEstantes(null);
        acervo.addLivro(livro);
        assertFalse(acervo.getEstantes().isEmpty());
    }

    @Test
    @DisplayName("addLivro2 com path nulo")
    void testAddLivro2ComCaminhoNulo() {
        assertDoesNotThrow(() -> acervo.addLivro2(livro, null));
    }

    @Test
    @DisplayName("addLivro2 com autor já existente, mas objeto diferente")
    void testAddLivro2ComAutorJaExistente() {
        Autor autorClone = new Autor(NOME_AUTOR);
        Livro outroLivro = new Livro(autorClone, "Clone", 100, "ISBN-C", "Aventura", "Editora", false);
        acervo.addLivro2(livro, pathComBarra);
        acervo.addLivro2(outroLivro, pathComBarra);
        assertEquals(1, acervo.getAutores().size());
    }

    @Test
    @DisplayName("addLivro2 com gênero nulo na estante e no livro")
    void testAddLivro2ComGeneroNuloNaEstanteELivro() {
        acervo.getEstantes().get(0).setGenero(null);
        Livro livroSemGenero = new Livro(autor, "Sem Gênero", 100, "ISBN-SG", null, "Editora", false);
        acervo.addLivro2(livroSemGenero, pathComBarra);
        assertNotNull(acervo.buscarLivroISNB("ISBN-SG"));
    }

    @Test
    @DisplayName("Remover livro que não existe na lista do autor")
    void testRemoverLivroInexistenteNaListaDoAutor() {
        autor.getLivrosAutor().clear();
        acervo.getAutores().add(autor);
        acervo.getEstantes().get(0).addLivroNaEstante(livro);
        assertDoesNotThrow(() -> acervo.removeLivro(livro, pathComBarra));
    }

    @Test
    @DisplayName("Remover livro de um gênero que não corresponde a nenhuma estante")
    void testRemoverLivroDeGeneroSemEstanteCorrespondente() {
        livro.setGenero("Ficção Científica");
        acervo.getAutores().add(autor);
        acervo.getEstantes().get(0).addLivroNaEstante(livro);
        assertDoesNotThrow(() -> acervo.removeLivro(livro, pathComBarra));
    }
    
    @Test
    @DisplayName("Buscar empréstimo com CPF e ISBN")
    void testBuscarEmprestimo() {
        Emprestimo emprestimo = new Emprestimo("12345", "98765", "01/01/2025", "15/01/2025");
        acervo.getEmprestimos().add(emprestimo);
        Emprestimo emprestimoEncontrado = acervo.buscarEmprestimo("12345", "98765");
        assertNotNull(emprestimoEncontrado);
        assertEquals("12345", emprestimoEncontrado.getCPF());
    }

    @Test
    @DisplayName("Buscar empréstimo com CPF e ISBN inexistente")
    void testBuscarEmprestimoInexistente() {
        Emprestimo emprestimoEncontrado = acervo.buscarEmprestimo("11111", "22222");
        assertNull(emprestimoEncontrado);
    }
}