package biblioteca.livros;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Emprestimo {

    // [Correção 6] Constante para string duplicada
    private static final String NOME_ARQUIVO = "emprestimos.csv";
    private static final String TEMP_FILE = "temp.csv";

    // [Correções 1 e 2] Campos em camelCase
    private String cpf;
    private String isnb;
    private String dataEmprestimo;
    private String dataDevolucao;

    // [Correções 3 e 4] Parâmetros em camelCase
    public Emprestimo(String cpf, String isnb, String dataEmprestimo, String dataDevolucao) {
        this.cpf = cpf;
        this.isnb = isnb;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    public String getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(String dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getIsnb() { return isnb; }
    public void setIsnb(String isnb) { this.isnb = isnb; }
    public String getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(String dataDevolucao) { this.dataDevolucao = dataDevolucao; }

    @Override
    public String toString() {
        return "IdLivro: " + this.getIsnb() + " - " + "CPF: " + this.getCpf()
                + " - " + "Data de Emprestimo: " + this.getDataEmprestimo() +
                " - " + "Data de Devolução: " + this.getDataDevolucao();
    }

    public static void escreverEmprestimo(Emprestimo emprestimo, String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path + NOME_ARQUIVO, true));
             PrintWriter pw = new PrintWriter(bw)) {

            String linha = emprestimo.getCpf() + "," + emprestimo.getIsnb() + "," +
                    emprestimo.getDataEmprestimo() + "," + emprestimo.getDataDevolucao();
            pw.println(linha);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // [Correção 5] Retorna List (interface)
    public static List<Emprestimo> leitorEmprestimos(String path) {
        List<Emprestimo> emprestimos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path + NOME_ARQUIVO))) {
            // [Correção 7] Armazena/Usa o retorno do readLine
            String header = br.readLine();
            if (header == null) return emprestimos;

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 4) {
                    Emprestimo novo = new Emprestimo(dados[0], dados[1], dados[2], dados[3]);
                    emprestimos.add(novo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // [Correção 17] Retorna lista vazia ao invés de null
            return new ArrayList<>();
        }
        return emprestimos;
    }

    public static void removerEmprestimo(Emprestimo emprestimoRemovido, String path) {
        Path arquivoAntigo = Paths.get(path + NOME_ARQUIVO);
        Path arquivoNovo = Paths.get(path + TEMP_FILE);

        try (BufferedReader br = new BufferedReader(new FileReader(arquivoAntigo.toFile()));
             BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoNovo.toFile(), true));
             PrintWriter pw = new PrintWriter(bw)) {

            String linha;
            // [Correção 8] Redução de break/continue simplificando a lógica
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                boolean manterLinha = true;

                if (dados.length >= 2) {
                    boolean mesmoCpf = safeEquals(dados, 0, emprestimoRemovido.getCpf());
                    boolean mesmoIsbn = safeEquals(dados, 1, emprestimoRemovido.getIsnb());

                    if (mesmoCpf && mesmoIsbn) {
                        manterLinha = false;
                    }
                }

                if (manterLinha) {
                    pw.println(linha);
                }
            }
            pw.flush();

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // [Correções 9, 10, 11] Uso de Files.move/delete resolve o problema de ignorar booleanos
        atualizarArquivos(arquivoAntigo, arquivoNovo);
    }

    // [Correção 18] Removido parâmetro 'acervo'
    // [Correção 12] Complexidade Cognitiva reduzida extraindo lógica
    public static void modificarEmprestimo(Livro livroEmprestado, String path, String booleano) {
        // [Correção 16] Removido código comentado (S125) que existia aqui nas versões anteriores
        Path arquivoAntigo = Paths.get(path + "/livros.csv");
        Path arquivoNovo = Paths.get(path + "/temp.csv");

        try (BufferedReader br = new BufferedReader(new FileReader(arquivoAntigo.toFile()));
             BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoNovo.toFile(), true));
             PrintWriter pw = new PrintWriter(bw)) {

            String linha;
            while ((linha = br.readLine()) != null) {
                // Lógica delegada para método auxiliar para reduzir complexidade
                String linhaProcessada = processarLinhaModificacao(linha, livroEmprestado, booleano);
                pw.println(linhaProcessada);
            }
            pw.flush();

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // [Correções 13, 14, 15] Uso da API Files
        atualizarArquivos(arquivoAntigo, arquivoNovo);
    }

    // Método auxiliar para reduzir Complexidade Cognitiva (S3776)
    private static String processarLinhaModificacao(String linha, Livro livroEmprestado, String booleano) {
        if (linha.trim().isEmpty() || isHeaderLine(linha)) {
            return linha;
        }

        String[] livro = linha.split(",");
        if (livro.length < 2) return linha;

        if (deveAtualizar(livro, livroEmprestado, booleano)) {
            livro = ensureFields(livro, 8);
            livro[6] = booleano;
            return joinFields(livro);
        }

        return linha;
    }

    private static boolean deveAtualizar(String[] livro, Livro livroEmprestado, String booleano) {
        boolean titleEquals = safeEquals(livro, 1, livroEmprestado.getTitulo());
        if (titleEquals) return true;

        boolean titleEqualsIgnore = safeEqualsIgnoreCase(livro, 1, livroEmprestado.getTitulo());
        boolean alreadyFlagged = (livro.length >= 8 && "true".equalsIgnoreCase(livro[7]));
        if (titleEqualsIgnore && !alreadyFlagged) return true;

        boolean titleContains = safeContains(livro, 1, livroEmprestado.getTitulo());
        return titleContains && "true".equalsIgnoreCase(booleano);
    }

    // Helper para as correções de arquivos (S899 e S4042)
    private static void atualizarArquivos(Path antigo, Path novo) {
        try {
            Files.move(novo, antigo, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            try { Files.deleteIfExists(novo); } catch (IOException ignored) {}
        }
    }

    private static boolean isHeaderLine(String linha) {
        return linha != null && linha.toLowerCase().startsWith("titulo");
    }

    private static boolean safeEquals(String[] arr, int idx, String value) {
        return arr.length > idx && arr[idx] != null && arr[idx].equals(value);
    }

    private static boolean safeEqualsIgnoreCase(String[] arr, int idx, String value) {
        return arr.length > idx && arr[idx] != null && arr[idx].equalsIgnoreCase(value);
    }

    private static boolean safeContains(String[] arr, int idx, String value) {
        return arr.length > idx && arr[idx] != null && arr[idx].contains(value);
    }

    private static String[] ensureFields(String[] arr, int size) {
        if (arr.length >= size) return arr;
        String[] n = new String[size];
        System.arraycopy(arr, 0, n, 0, arr.length);
        for (int i = arr.length; i < size; i++) {
            n[i] = "";
        }
        return n;
    }

    private static String joinFields(String[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(arr[i] == null ? "" : arr[i]);
        }
        return sb.toString();
    }
}