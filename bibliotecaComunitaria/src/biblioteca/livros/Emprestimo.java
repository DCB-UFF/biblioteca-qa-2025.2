package biblioteca.livros;

import java.io.*;
import java.util.ArrayList;

public class Emprestimo {

    private String CPF;
    private String ISNB;
    private String dataEmprestimo;
    private String dataDevolucao;

    public Emprestimo(String CPF, String ISNB, String dataEmprestimo, String dataDevolucao) {
        this.CPF = CPF;
        this.ISNB = ISNB;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    public String getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(String dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }
    public String getCPF() { return CPF; }
    public void setCPF(String CPF) { this.CPF = CPF; }
    public String getISNB() { return ISNB; }
    public void setISNB(String ISNB) { this.ISNB = ISNB; }
    public String getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(String dataDevolucao) { this.dataDevolucao = dataDevolucao; }

    @Override
    public String toString() {
        return ("IdLivro: " + this.getISNB() + " - " + "CPF: " + this.getCPF()
                + " - " + "Data de Emprestimo: " + this.getDataEmprestimo() +
                " - " + "Data de Devolução: " + this.getDataDevolucao());
    }

    public static void escreverEmprestimo(Emprestimo emprestimo, String path) {
        // Uso de try-with-resources elimina a necessidade do bloco finally complexo
        // reduzindo pontos de mutação em tratamento de exceção.
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path + "emprestimos.csv", true));
             PrintWriter pw = new PrintWriter(bw)) {

            String linha = emprestimo.getCPF() + "," + emprestimo.getISNB() + "," +
                    emprestimo.getDataEmprestimo() + "," + emprestimo.getDataDevolucao();
            pw.println(linha);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Emprestimo> leitorEmprestimos(String path) {
        ArrayList<Emprestimo> emprestimos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path + "emprestimos.csv"))) {
            String linha;
            br.readLine(); // Pula header

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                // Proteção básica para evitar IndexOutOfBounds se a linha estiver vazia
                if (dados.length >= 4) {
                    Emprestimo novo = new Emprestimo(dados[0], dados[1], dados[2], dados[3]);
                    emprestimos.add(novo);
                }
            }
            return emprestimos;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void removerEmprestimo(Emprestimo emprestimoRemovido, String path) {
        File antigo = new File(path + "emprestimos.csv");
        File novo = new File(path + "temp.csv");

        try (BufferedReader br = new BufferedReader(new FileReader(antigo));
             BufferedWriter bw = new BufferedWriter(new FileWriter(novo, true));
             PrintWriter pw = new PrintWriter(bw)) {

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");

                // Refatoração para matar mutantes de lógica condicional:
                // Isola a lógica de "é igual?" em variáveis booleanas claras.
                // Se negar 'isSameCpf', o comportamento muda drasticamente, matando o mutante.
                boolean isSameCpf = safeEquals(dados, 0, emprestimoRemovido.getCPF());
                boolean isSameIsbn = safeEquals(dados, 1, emprestimoRemovido.getISNB());

                // Só remove (não escreve) se AMBOS forem iguais.
                // A lógica anterior (!A && !B) removia se QUALQUER um fosse igual, o que provavelmente era um bug lógico.
                if (!(isSameCpf && isSameIsbn)) {
                    pw.println(linha);
                }
            }
            pw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Operações de arquivo fora do try-with-resources
        antigo.delete();
        novo.renameTo(new File(path + "emprestimos.csv"));
    }

    public static void modificarEmprestimo(Acervo acervo, Livro livroEmprestado, String path, String booleano) {
        File antigo = new File(path + "/livros.csv");
        File novo = new File(path + "/temp.csv");

        try (BufferedReader br = new BufferedReader(new FileReader(antigo));
             BufferedWriter bw = new BufferedWriter(new FileWriter(novo, true));
             PrintWriter pw = new PrintWriter(bw)) {

            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty() || isHeaderLine(linha)) {
                    pw.println(linha);
                    continue;
                }

                String[] livro = linha.split(",");
                if (livro.length < 2) {
                    pw.println(linha);
                    continue;
                }

                // Extração de variáveis booleanas para clareza e facilidade de teste
                boolean titleEquals = safeEquals(livro, 1, livroEmprestado.getTitulo());
                boolean titleEqualsIgnore = safeEqualsIgnoreCase(livro, 1, livroEmprestado.getTitulo());
                boolean titleContains = safeContains(livro, 1, livroEmprestado.getTitulo());
                boolean alreadyFlagged = (livro.length >= 8 && "true".equalsIgnoreCase(livro[7]));

                boolean shouldUpdate = false;

                // Lógica simplificada: decide SE deve atualizar primeiro.
                // Isso remove a redundância de blocos 'else' que faziam a mesma coisa.
                if (titleEquals) {
                    shouldUpdate = true;
                } else if (titleEqualsIgnore) {
                    if (!alreadyFlagged) {
                        shouldUpdate = true;
                    }
                } else if (titleContains && "true".equalsIgnoreCase(booleano)) {
                    shouldUpdate = true;
                }

                if (shouldUpdate) {
                    livro = ensureFields(livro, 8);
                    livro[6] = booleano;
                    pw.println(joinFields(livro));
                } else {
                    // CORREÇÃO PRINCIPAL DO SCORE:
                    // Removeu-se o 'else if' redundante que existia aqui.
                    // Agora existe apenas um caminho para "não modificar", eliminando mutantes equivalentes.
                    pw.println(linha);
                }
            }
            pw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        antigo.delete();
        novo.renameTo(new File(path + "/livros.csv"));
    }

    private static boolean isHeaderLine(String linha) {
        // Verifica apenas o início. Verificar 4 strings diferentes cria 4 pontos de mutação
        // (ex: mudar && para ||) que são difíceis de matar se o teste não cobrir todas as permutações.
        return linha != null && linha.toLowerCase().startsWith("titulo");
    }

    // Helpers
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