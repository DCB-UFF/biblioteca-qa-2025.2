package test.java.biblioteca.livros;

import biblioteca.livros.Acervo; // <--- CORRIGIDO: Agora importando do pacote certo
import biblioteca.livros.Estante;
import biblioteca.livros.Livro;
import biblioteca.pessoas.Autor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EstanteTest {

    private Path tempDir;
    private String pathComBarra;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("teste_estantes_final");

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
    @DisplayName("Deve testar Construtor, Getters e Setters básicos")
    void testEntidadeEstante() {
        Estante e = new Estante(1, "Terror");
        assertEquals(1, e.getIdEstante());
        assertEquals("Terror", e.getGenero());
        assertEquals(50, e.getCapacidade());
        assertNotNull(e.getLivros());
        assertTrue(e.getLivros().isEmpty());

        e.setIdEstante(99);
        assertEquals(99, e.getIdEstante());

        e.setGenero("Comédia");
        assertEquals("Comédia", e.getGenero());

        e.setCapacidade(100);
        assertEquals(100, e.getCapacidade());

        ArrayList<Livro> novaLista = new ArrayList<>();
        e.setLivros(novaLista);
        assertSame(novaLista, e.getLivros());
    }

    @Test
    @DisplayName("Deve validar a capacidade e testar buscas (Usando Reflection para métodos protegidos)")
    void testLogicaLivrosNaEstante() throws Exception {

        Estante e = new Estante(10, "Geral");
        e.setCapacidade(1);

        Autor autor = new Autor("Autor Teste", "Pais Teste");
        Livro l1 = new Livro(autor, "Livro Aceito", 100, "111", "Geral", "Ed A", false);
        Livro l2 = new Livro(autor, "Livro Recusado", 100, "222", "Geral", "Ed A", false);

        e.addLivroNaEstante(l1);
        e.addLivroNaEstante(l2);

        assertEquals(1, e.getLivros().size());
        assertEquals("Livro Aceito", e.getLivros().get(0).getTitulo());

        Method metodoBuscarTitulo = Estante.class.getDeclaredMethod("buscarLivroNaEstanteTitulo", String.class);
        metodoBuscarTitulo.setAccessible(true);

        Livro achado = (Livro) metodoBuscarTitulo.invoke(e, "Livro Aceito");
        assertNotNull(achado);
        assertEquals("Livro Aceito", achado.getTitulo());

        Livro naoAchado = (Livro) metodoBuscarTitulo.invoke(e, "Inexistente");
        assertNull(naoAchado);

        Method metodoBuscarVoid = Estante.class.getDeclaredMethod("buscarLivroNaEstante", String.class);
        metodoBuscarVoid.setAccessible(true);

        metodoBuscarVoid.invoke(e, "Livro Aceito");
        metodoBuscarVoid.invoke(e, "Nada");
    }

    @Test
    @DisplayName("Deve escrever estante no arquivo CSV")
    void testEscreveEstante() throws IOException {
        Estante e = new Estante(5, "Ficção");

        Estante.escreveEstante(e, pathComBarra);

        Path arquivo = tempDir.resolve("estantes.csv");
        assertTrue(Files.exists(arquivo), "O arquivo estantes.csv deve ser criado");

        String conteudo = Files.readString(arquivo);
        assertTrue(conteudo.contains("5,Ficção"));
    }

    @Test
    @DisplayName("Deve ler estantes tratando TODOS os casos de borda (Complexidade do Leitor)")
    void testLeitorEstantes() throws IOException {
        Path arquivo = tempDir.resolve("estantes.csv");

        String csvMaldito =
                "Cabecalho,Ignorado\n" +
                        "1,Aventura\n" +
                        "\n" +
                        "LinhaSemVirgula\n" +
                        "XYZ,Terror\n" +
                        "2,\n" +
                        "1,Duplicado\n" +
                        "-10,Negativo\n" +
                        "10001,MuitoAlto\n" +
                        "3,GeneroMuitoGrandeQueDeveSerCortadoPeloSistemaParaTrintaCaracteresExatos\n"; // Truncar

        Files.writeString(arquivo, csvMaldito);

        Acervo acervo = Estante.leitorEstantes(pathComBarra);
        assertNotNull(acervo);
        assertEquals(2, acervo.getEstantes().size());

        Estante e1 = acervo.getEstantes().get(0);
        assertEquals(1, e1.getIdEstante());
        assertEquals("Aventura", e1.getGenero());

        Estante e3 = acervo.getEstantes().get(1);
        assertEquals(3, e3.getIdEstante());
        assertEquals(30, e3.getGenero().length());
        assertTrue(e3.getGenero().startsWith("GeneroMuitoGrande"));
    }
}