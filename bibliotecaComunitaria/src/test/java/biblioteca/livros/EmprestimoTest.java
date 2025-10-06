package test.java.biblioteca.livros;

import biblioteca.livros.Emprestimo;
import biblioteca.livros.Acervo;
import biblioteca.livros.Livro;
import biblioteca.pessoas.Autor;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EmprestimoTest {

    private Emprestimo emprestimo;
    private Path pastaTemp;

    Autor autor = new Autor("George R. R. Martin","Estados Unidos");

    Livro livro = new Livro(autor,"A Danca dos Dragoes",415,"4826002","Guerra","Leya",false);


    @BeforeEach
    void antesDeCadaTeste() throws IOException {
        emprestimo = new Emprestimo("12345678900", "ISNB001", "2025-10-05", "2025-10-20");
        pastaTemp = Files.createTempDirectory("testeEmprestimo");
    }

    @AfterEach
    void depoisDeCadaTeste() throws IOException {
        Files.walk(pastaTemp)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    void deveGerarToStringComDadosCorretos() {
        String texto = emprestimo.toString();
        assertTrue(texto.contains("CPF: 12345678900"));
        assertTrue(texto.contains("IdLivro: ISNB001"));
    }

    @Test
    void deveEscreverELerEmprestimo() throws IOException {
        Emprestimo.escreverEmprestimo(emprestimo, pastaTemp.toString() + File.separator);

        Path arquivo = pastaTemp.resolve("emprestimos.csv");
        List<String> linhas = Files.readAllLines(arquivo);
        linhas.add(0, "CPF,ISNB,DataEmprestimo,DataDevolucao"); // adiciona cabeçalho
        Files.write(arquivo, linhas);

        ArrayList<Emprestimo> lista = Emprestimo.leitorEmprestimos(pastaTemp.toString() + File.separator);

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("12345678900", lista.get(0).getCPF());
        assertEquals("ISNB001", lista.get(0).getISNB());
    }

    @Test
    void deveRemoverEmprestimoDoArquivo() throws IOException {
        Emprestimo outro = new Emprestimo("99999999999", "ISNB002", "2025-10-06", "2025-10-21");

        Emprestimo.escreverEmprestimo(emprestimo, pastaTemp.toString() + File.separator);
        Emprestimo.escreverEmprestimo(outro, pastaTemp.toString() + File.separator);

        Path arquivo = pastaTemp.resolve("emprestimos.csv");
        List<String> linhas = Files.readAllLines(arquivo);
        linhas.add(0, "CPF,ISNB,DataEmprestimo,DataDevolucao"); // adiciona cabeçalho
        Files.write(arquivo, linhas);

        Emprestimo.removerEmprestimo(emprestimo, pastaTemp.toString() + File.separator);

        ArrayList<Emprestimo> lista = Emprestimo.leitorEmprestimos(pastaTemp.toString() + File.separator);

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("99999999999", lista.get(0).getCPF());
        assertEquals("ISNB002", lista.get(0).getISNB());
    }

    @Test
    void deveMarcarLivroComoEmprestado() throws IOException {
        Path arquivoLivros = pastaTemp.resolve("livros.csv");
        List<String> linhas = Arrays.asList(
                "autor,Titulo,Paginas,ISBN,Genero,Editora,estaEmprestado,Pais",
                "George R. R. Martin,A Danca dos Dragoes,415,4826002,Guerra,Leya,false,Estados Unidos"
        );
        Files.write(arquivoLivros, linhas);
        Emprestimo.modificarEmprestimo(new Acervo(), livro, pastaTemp.toString() + File.separator, "true");

        List<String> linhasAtualizadas = Files.readAllLines(arquivoLivros);
        assertTrue(linhasAtualizadas.get(1).contains("true"));
    }

    @Test
    void naoDeveAlterarLivroComTituloDiferente() throws IOException {
        Path arquivoLivros = pastaTemp.resolve("livros.csv");
        List<String> linhas = Arrays.asList(
                "autor,Titulo,Paginas,ISBN,Genero,Editora,estaEmprestado,Pais",
                "George R. R. Martin,A Danca dos Dragoes,415,4826002,Guerra,Leya,false,Estados Unidos"
        );
        Files.write(arquivoLivros, linhas);

        Livro livro = new Livro(autor,"game of thrones",415,"4826002","Guerra","Leya",false);


        Emprestimo.modificarEmprestimo(new Acervo(), livro, pastaTemp.toString() + File.separator, "true");

        List<String> linhasAtualizadas = Files.readAllLines(arquivoLivros);
        assertTrue(linhasAtualizadas.get(1).contains("false")); // não alterou
    }
}

