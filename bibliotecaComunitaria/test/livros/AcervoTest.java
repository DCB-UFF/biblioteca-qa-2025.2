package biblioteca.livros;

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

    static class MockAutor extends Autor {
        private boolean forceEquals;

        public MockAutor(String nome, boolean forceEquals) {
            super(nome);
            this.forceEquals = forceEquals;
        }

        @Override
        public boolean equals(Object o) {
            return forceEquals;
        }
    }

    @BeforeEach
    void setUp() throws IOException {
        System.setOut(new PrintStream(outContent));

        tempDir = Files.createTempDirectory("teste_acervo_completo");
        pathComBarra = tempDir.toAbsolutePath().toString() + File.separator;

        criarArquivoCSV("livros.csv", "Autor;Titulo;Paginas;ISBN;Genero;Editora;Emprestado\n");
        criarArquivoCSV("emprestimos.csv", "CPF,ISBN,DataEmp,DataDev\n");
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
    void testAddLivroNull() {
        acervo.addLivro(null);
        assertEquals(1, acervo.getEstantes().size());
    }

    @Test
    void testAddLivroAutoresNull() {
        acervo.setAutores(null);
        acervo.addLivro(livro);
        assertNotNull(acervo.getAutores());
        assertEquals(1, acervo.getAutores().size());
    }

    @Test
    void testAddLivroAutorDoLivroNull() {
        Livro l = new Livro(null, "Sem Autor", 100, "ISBN00", "Drama", "Ed", false);
        acervo.addLivro(l);
        assertEquals(1, acervo.getAutores().size());
        assertEquals("Desconhecido", acervo.getAutores().get(0).getNome());
    }

    @Test
    void testAddLivroAutorNullNaLista() {
        acervo.getAutores().add(null);
        acervo.addLivro(livro);
        assertEquals(2, acervo.getAutores().size());
    }

    @Test
    void testAddLivroAutorEqualsNomeIgual() {
        acervo.getAutores().add(autor);
        acervo.addLivro(livro);
        assertEquals(1, acervo.getAutores().size());
        assertTrue(autor.getLivrosAutor().contains(livro));
    }

    @Test
    void testAddLivroAutorEqualsNomeIgnoreCase() {
        MockAutor a1 = new MockAutor("VICTORIA", true);
        acervo.getAutores().add(a1);
        
        MockAutor a2 = new MockAutor("victoria", true);
        Livro l = new Livro(a2, "T", 1, "I", "G", "E", false);
        
        acervo.addLivro(l);
        assertEquals(1, acervo.getAutores().size());
        assertTrue(a1.getLivrosAutor().contains(l));
    }

    @Test
    void testAddLivroAutorEqualsNomeDiferente() {
        MockAutor a1 = new MockAutor("NomeA", true);
        acervo.getAutores().add(a1);
        
        MockAutor a2 = new MockAutor("NomeB", true);
        Livro l = new Livro(a2, "T", 1, "I", "G", "E", false);
        
        acervo.addLivro(l);
        assertEquals(2, acervo.getAutores().size());
    }

    @Test
    void testAddLivroAutorNotContains() {
        Autor a1 = new Autor("Autor 1");
        acervo.getAutores().add(a1);
        
        Autor a2 = new Autor("Autor 2");
        Livro l = new Livro(a2, "T", 1, "I", "G", "E", false);
        
        acervo.addLivro(l);
        assertEquals(2, acervo.getAutores().size());
        assertEquals("Autor 2", acervo.getAutores().get(1).getNome());
    }

    @Test
    void testAddLivroEstantesNull() {
        acervo.setEstantes(null);
        acervo.addLivro(livro);
        assertNotNull(acervo.getEstantes());
    }

    @Test
    void testAddLivroEstanteNullNaLista() {
        acervo.getEstantes().add(null);
        acervo.addLivro(livro);
        assertTrue(acervo.getEstantes().get(0).getLivros().contains(livro));
    }

    @Test
    void testAddLivroEstanteGeneroNullLivroGeneroNull() {
        acervo.setEstantes(new ArrayList<>());
        Estante e = new Estante(1, null);
        acervo.addEstante(e);
        
        Livro l = new Livro(autor, "T", 1, "I", null, "E", false);
        acervo.addLivro(l);
        assertTrue(e.getLivros().contains(l));
    }

    @Test
    void testAddLivroEstanteGeneroIgual() {
        acervo.setEstantes(new ArrayList<>());
        Estante e = new Estante(1, "Drama");
        acervo.addEstante(e);
        
        Livro l = new Livro(autor, "T", 1, "I", "Drama", "E", false);
        acervo.addLivro(l);
        assertTrue(e.getLivros().contains(l));
    }

    @Test
    void testAddLivroEstanteGeneroIgnoreCase() {
        acervo.setEstantes(new ArrayList<>());
        Estante e = new Estante(1, "DRAMA");
        acervo.addEstante(e);
        
        Livro l = new Livro(autor, "T", 1, "I", "drama", "E", false);
        acervo.addLivro(l);
        assertTrue(e.getLivros().contains(l));
    }

    @Test
    void testAddLivroLivroGeneroNullEstanteGeneroNull() {
        acervo.setEstantes(new ArrayList<>());
        Estante e = new Estante(1, null);
        acervo.addEstante(e);
        
        Livro l = new Livro(autor, "T", 1, "I", null, "E", false);
        acervo.addLivro(l);
        assertTrue(e.getLivros().contains(l));
    }

    @Test
    void testAddLivroLivroGeneroEmptyEstanteGeneroNull() {
        acervo.setEstantes(new ArrayList<>());
        Estante e = new Estante(1, null);
        acervo.addEstante(e);
        
        Livro l = new Livro(autor, "T", 1, "I", "", "E", false);
        acervo.addLivro(l);
        assertTrue(e.getLivros().contains(l));
    }

    @Test
    void testAddLivroLivroGeneroEmptyEstanteGeneroEmpty() {
        acervo.setEstantes(new ArrayList<>());
        Estante e = new Estante(1, "   ");
        acervo.addEstante(e);
        
        Livro l = new Livro(autor, "T", 1, "I", " ", "E", false);
        acervo.addLivro(l);
        assertTrue(e.getLivros().contains(l));
    }

    @Test
    void testAddLivroNaoColocadoCriaNova() {
        acervo.setEstantes(new ArrayList<>());
        Estante e = new Estante(1, "Terror");
        acervo.addEstante(e);
        
        Livro l = new Livro(autor, "T", 1, "I", "Comedia", "E", false);
        acervo.addLivro(l);
        assertEquals(2, acervo.getEstantes().size());
        assertEquals("Comedia", acervo.getEstantes().get(1).getGenero());
    }

    @Test
    void testAddLivroNaoColocadoGeneroNullNaoCria() {
        acervo.setEstantes(new ArrayList<>());
        Livro l = new Livro(autor, "T", 1, "I", null, "E", false);
        acervo.addLivro(l);
        assertEquals(0, acervo.getEstantes().size());
    }

    @Test
    void testAddLivro2Null() {
        acervo.addLivro2(null, pathComBarra);
        assertEquals(1, acervo.getEstantes().size());
    }

    @Test
    void testAddLivro2PathNull() {
        acervo.addLivro2(livro, null);
        assertTrue(acervo.getEstantes().get(0).getLivros().contains(livro));
    }

    @Test
    void testAddLivro2BranchesAutorLoop() {
        MockAutor a1 = new MockAutor("A", true);
        acervo.getAutores().add(a1);
        acervo.getAutores().add(null); 
        
        MockAutor a2 = new MockAutor("a", true);
        Livro l = new Livro(a2, "T", 1, "I", "G", "E", false);
        
        acervo.addLivro2(l, pathComBarra);
        assertTrue(a1.getLivrosAutor().contains(l));
    }
    
    @Test
    void testAddLivro2BranchesAutorElse() {
        MockAutor a1 = new MockAutor("A", true);
        acervo.getAutores().add(a1);
        
        MockAutor a2 = new MockAutor("B", true);
        Livro l = new Livro(a2, "T", 1, "I", "G", "E", false);
        acervo.addLivro2(l, pathComBarra);
        assertEquals(2, acervo.getAutores().size());
    }

    @Test
    void testAddLivro2EstanteBranches() {
        acervo.setEstantes(null);
        acervo.addLivro2(livro, pathComBarra);
        assertNotNull(acervo.getEstantes());
    }

    @Test
    void testAddLivro2EstanteLoopBranches() {
        acervo.setEstantes(new ArrayList<>());
        Estante eNull = null;
        acervo.getEstantes().add(eNull); 
        Estante e = new Estante(1, "G");
        acervo.getEstantes().add(e);
        
        Livro l = new Livro(autor, "T", 1, "I", "G", "E", false);
        acervo.addLivro2(l, pathComBarra);
        assertTrue(e.getLivros().contains(l));
    }
    
    @Test
    void testAddLivro2NullGeneros() {
        acervo.setEstantes(new ArrayList<>());
        Estante e = new Estante(1, null);
        acervo.addEstante(e);
        Livro l = new Livro(autor, "T", 1, "I", null, "E", false);
        acervo.addLivro2(l, pathComBarra);
        assertTrue(e.getLivros().contains(l));
    }

    @Test
    void testAddLivro2GeneroIgnoreCase() {
        acervo.setEstantes(new ArrayList<>());
        Estante e = new Estante(1, "DRAMA");
        acervo.addEstante(e);
        
        Livro l = new Livro(autor, "T", 1, "I", "drama", "E", false);
        acervo.addLivro2(l, pathComBarra);
        
        assertTrue(e.getLivros().contains(l));
    }

    @Test
    void testAddLivro2LivroGeneroNullEstanteGeneroEmpty() {
        acervo.setEstantes(new ArrayList<>());
        Estante e = new Estante(1, ""); 
        acervo.addEstante(e);
        
        Livro l = new Livro(autor, "T", 1, "I", null, "E", false);
        acervo.addLivro2(l, pathComBarra);
        
        assertTrue(e.getLivros().contains(l));
    }
    
    @Test
    void testAddLivro2LivroGeneroEmptyEstanteGeneroNull() {
        acervo.setEstantes(new ArrayList<>());
        Estante e = new Estante(1, null);
        acervo.addEstante(e);
        
        Livro l = new Livro(autor, "T", 1, "I", "", "E", false);
        acervo.addLivro2(l, pathComBarra);
        
        assertTrue(e.getLivros().contains(l));
    }

    @Test
    void testAddLivro2Fallback() {
         acervo.setEstantes(new ArrayList<>());
         Livro l = new Livro(autor, "T", 1, "I", "G", "E", false);
         acervo.addLivro2(l, pathComBarra);
         assertEquals(1, acervo.getEstantes().size());
    }

    @Test
    void testBuscarLivroISNB() {
        acervo.addLivro(livro);
        assertEquals(livro, acervo.buscarLivroISNB(ISBN));
        assertNull(acervo.buscarLivroISNB("000"));
    }

    @Test
    void testBuscarLivroAutor() {
        acervo.addLivro(livro);
        assertFalse(acervo.buscarLivroAutor(NOME_AUTOR).isEmpty());
        assertTrue(acervo.buscarLivroAutor("X").isEmpty());
    }

    @Test
    void testBuscarAutor() {
        acervo.addLivro(livro);
        assertTrue(acervo.buscarAutor(NOME_AUTOR));
        assertFalse(acervo.buscarAutor("X"));
    }

    @Test
    void testBuscarEmprestimo() {
        Emprestimo e = new Emprestimo("C", "I", "d", "d");
        acervo.getEmprestimos().add(e);
        assertEquals(e, acervo.buscarEmprestimo("C", "I"));
        assertNull(acervo.buscarEmprestimo("C", "X"));
        assertNull(acervo.buscarEmprestimo("X", "I"));
    }

    @Test
    void testBuscarClienteNosEmprestimos() {
        Emprestimo e = new Emprestimo("C", "I", "d", "d");
        acervo.getEmprestimos().add(e);
        assertTrue(acervo.buscarClienteNosEmprestimos("C"));
        assertFalse(acervo.buscarClienteNosEmprestimos("X"));
    }
    
    @Test
    void testEmprestarDevolver() {
        acervo.addLivro(livro);
        Emprestimo e = new Emprestimo("C", ISBN, "d", "d");
        acervo.emprestarLivro(unidade, e);
        assertTrue(acervo.getEmprestimos().contains(e));
        
        acervo.devolverLivro(unidade, "C", ISBN);
        assertFalse(acervo.getEmprestimos().contains(e));
    }
    
    @Test
    void testEmprestarFalha() {
        acervo.addLivro(livro);
        Emprestimo e1 = new Emprestimo("C", ISBN, "d", "d");
        acervo.emprestarLivro(unidade, e1);
        
        Emprestimo e2 = new Emprestimo("C", "Other", "d", "d");
        acervo.emprestarLivro(unidade, e2);
        assertFalse(acervo.getEmprestimos().contains(e2));
    }
    
    @Test
    void testImprimir() {
        acervo.imprimirAcervo("U");
        assertTrue(outContent.toString().contains("U"));
    }
    
    @Test
    void testSettersGetters() {
        acervo.setIdsEstantes(10);
        assertEquals(10, acervo.getIdsEstantes());
        acervo.setEstantes(new ArrayList<>());
        assertTrue(acervo.getEstantes().isEmpty());
        acervo.setEmprestimos(new ArrayList<>());
        assertTrue(acervo.getEmprestimos().isEmpty());
        acervo.setAutores(new ArrayList<>());
        assertTrue(acervo.getAutores().isEmpty());
    }
}