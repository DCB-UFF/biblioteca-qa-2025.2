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
    // checar devolução
    
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

    public static ArrayList<Emprestimo> leitorEmprestimos(String path){
        BufferedReader br = null;
        String linha = "";
        ArrayList<Emprestimo> emprestimos = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(path+"emprestimos.csv"));
            br.readLine();
            
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
                if ((!emprestimo[0].equals(emprestimoRemovido.getCPF()))
                        && (!emprestimo[1].equals(emprestimoRemovido.getISNB()))){
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
    
}
