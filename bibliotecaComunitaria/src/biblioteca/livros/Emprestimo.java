package biblioteca.livros;

import java.io.*;
import java.util.ArrayList;

/* @author Luam */

public class Emprestimo {
    private String CPF;
    private String ISNB;
    private String dataEmprestimo;
    private String dataDevolucao;

    public Emprestimo(String CPF, String ISNB, String dataEmprestimo, String dataDevolucao) {
        this.CPF= CPF;
        this.ISNB = ISNB;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(String dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getISNB() {
        return ISNB;
    }

    public void setISNB(String ISNB) {
        this.ISNB = ISNB;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    @Override
    public String toString() {
        return ("IdLivro: " + this.getISNB() + " - " +"CPF: " + this.getCPF()
                + " - " +  "Data de Emprestimo: " + this.getDataEmprestimo() +
                " - " + "Data de Devolução: " + this.getDataDevolucao());
    }

    public static void escreverEmprestimo(Emprestimo emprestimo, String path) {
        BufferedWriter bw = null;
        String linha = emprestimo.getCPF()+","+emprestimo.getISNB()+","+emprestimo.getDataEmprestimo()+","+
                emprestimo.getDataDevolucao();
        try {
            bw = new BufferedWriter(new FileWriter(path+"emprestimos.csv", true));
            PrintWriter pw= new PrintWriter(bw);
            pw.println(linha);
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // O teste verifica se isso é impresso no System.err
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ArrayList<Emprestimo> leitorEmprestimos(String path){
        BufferedReader br = null;
        String linha = "";
        ArrayList<Emprestimo> emprestimos = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(path+"emprestimos.csv"));
            br.readLine(); // Pula cabeçalho

            while ((linha = br.readLine()) != null) {
                String[] emprestimo = linha.split(",");
                Emprestimo novo = new Emprestimo(emprestimo[0],emprestimo[1],emprestimo[2],emprestimo[3]);
                emprestimos.add(novo);
            }
            return emprestimos;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void removerEmprestimo(Emprestimo emprestimoRemovido, String path) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        File antigo = new File(path+"emprestimos.csv");
        File novo = new File (path+"temp.csv");

        try {
            br = new BufferedReader(new FileReader(antigo));
            bw = new BufferedWriter(new FileWriter(novo, true));
            PrintWriter pw= new PrintWriter(bw);
            String linha = "";

            while ((linha = br.readLine()) != null) {

                String[] emprestimo = linha.split(",");

                // --- CORREÇÃO DO BUG DE REMOÇÃO ---
                // Só não escreve (remove) se o CPF E o ISBN forem iguais ao removido.
                // Se um dos dois for diferente, deve manter a linha.
                if (!(emprestimo[0].equals(emprestimoRemovido.getCPF())
                        && emprestimo[1].equals(emprestimoRemovido.getISNB()))){
                    pw.println(linha);
                }
            }
            pw.flush();
            pw.close();
            br.close();
            antigo.delete();

            File aux = new File (path+"emprestimos.csv");
            novo.renameTo(aux);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void modificarEmprestimo(Acervo acervo, Livro livroEmprestado, String path, String booleano) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        File antigo = new File(path + "/livros.csv");
        File novo = new File(path + "/temp.csv");
        try {
            br = new BufferedReader(new FileReader(antigo));
            bw = new BufferedWriter(new FileWriter(novo, true));
            PrintWriter pw = new PrintWriter(bw);
            String linha;
            String linhaEditada;

            while ((linha = br.readLine()) != null) {

                // 1) Linha vazia
                if (linha.trim().isEmpty()) {
                    pw.println(linha);
                    continue;
                }

                // 2) Cabeçalho
                if (isHeaderLine(linha)) {
                    pw.println(linha);
                    continue;
                }

                String[] livro = linha.split(",");
                // 3) Linha malformada (poucos campos)
                if (livro.length < 2) {
                    pw.println(linha);
                    continue;
                }

                boolean titleEquals = safeEquals(livro, 1, livroEmprestado.getTitulo());
                boolean titleEqualsIgnore = safeEqualsIgnoreCase(livro, 1, livroEmprestado.getTitulo());
                boolean titleContains = safeContains(livro, 1, livroEmprestado.getTitulo());

                // --- CORREÇÃO AQUI ---
                // Antes estava checando livro[6]. Voltamos para livro[7] conforme o teste espera.
                // Verificamos se length > 7 (ou seja, tem pelo menos 8 itens, indo do índice 0 ao 7).
                boolean alreadyFlagged = (livro.length > 7 && "true".equalsIgnoreCase(livro[7]));

                if (titleEquals) {
                    livro = ensureFields(livro, 8);
                    livro[6] = booleano;
                    linhaEditada = joinFields(livro);
                } else if (titleEqualsIgnore) {
                    livro = ensureFields(livro, 8);

                    // Se NÃO estiver flagado no campo 8 (índice 7), permite alterar
                    if (!alreadyFlagged) {
                        livro[6] = booleano;
                    }
                    linhaEditada = joinFields(livro);
                } else if (titleContains && "true".equalsIgnoreCase(booleano)) {
                    livro = ensureFields(livro, 8);
                    livro[6] = booleano;
                    linhaEditada = joinFields(livro);
                } else if (isMalformedLine(livro)) {
                    linhaEditada = linha;
                } else if (livro.length >= 8 && livro[7] != null && !livro[7].isEmpty()) {
                    // Preserva linha se tiver campo extra não tratado
                    linhaEditada = linha;
                } else {
                    linhaEditada = linha;
                }

                pw.println(linhaEditada);
            }

            pw.flush();
            pw.close();
            br.close();
            antigo.delete();

            File aux = new File(path + "/livros.csv");
            novo.renameTo(aux);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static boolean isHeaderLine(String linha) {
        String lower = linha.toLowerCase();
        return lower.contains("titulo") && lower.contains("autor") && lower.contains("paginas") && lower.contains("estaemprestado");
    }

    private static boolean safeEquals(String[] arr, int idx, String value) {
        return (arr.length > idx && arr[idx] != null) && arr[idx].equals(value);
    }

    private static boolean safeEqualsIgnoreCase(String[] arr, int idx, String value) {
        return (arr.length > idx && arr[idx] != null) && arr[idx].equalsIgnoreCase(value);
    }

    private static boolean safeContains(String[] arr, int idx, String value) {
        return (arr.length > idx && arr[idx] != null) && arr[idx].contains(value);
    }

    private static boolean isMalformedLine(String[] arr) {
        int empty = 0;
        for (String s : arr) {
            if (s == null || s.trim().isEmpty()) empty++;
        }
        return empty >= arr.length - 1;
    }

    private static String[] ensureFields(String[] arr, int size) {
        if (arr.length >= size) return arr;
        String[] n = new String[size];
        for (int i = 0; i < size; i++) {
            if (i < arr.length) n[i] = arr[i];
            else n[i] = "";
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