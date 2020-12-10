package biblioteca.arquivo;

import biblioteca.biblioteca.*;
import biblioteca.livros.*;
import biblioteca.pessoas.*;
import java.io.*;

/* @author Luam */

// MÉTODOS QUE REMOVEM UNIDADE, LIVRO, EMPRESTIMO, CLIENTE E FUNCIONÁRIO

public class Removedor {
    public static void removerUnidade(Sistema sistema, Integer num) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        File antigo = new File("src\\unidades\\unidades.csv");
        File novo = new File ("src\\unidades\\temp.csv");
        
        try {
            br = new BufferedReader(new FileReader(antigo));
            bw = new BufferedWriter(new FileWriter(novo, true));
            PrintWriter pw= new PrintWriter(bw);
            String linha = "";
            
            sistema.addContadorUnidades();
            
            while ((linha = br.readLine()) != null) {
    
                String[] un = linha.split(",");
                if (!un[0].equals(String.valueOf(num))){
                    pw.println(linha);
                }
            }
            pw.flush();  
            pw.close();
            br.close();
            antigo.delete();
            
            File aux = new File ("src\\unidades\\unidades.csv");
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
     public static void removerLivro(Livro livroRemovido, String path) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        File antigo = new File(path+"livros.csv");
        File novo = new File (path+"temp.csv");
        
        try {
            br = new BufferedReader(new FileReader(antigo));
            bw = new BufferedWriter(new FileWriter(novo, true));
            PrintWriter pw= new PrintWriter(bw);
            String linha = "";
            
            while ((linha = br.readLine()) != null) {
    
                String[] livro = linha.split(",");
                if (!livro[1].equals(livroRemovido.getTitulo())){
                    pw.println(linha);
                }
            }
            pw.flush();  
            pw.close();
            br.close();
            antigo.delete();
            
            File aux = new File (path+"livros.csv");
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
     public static void removerCliente(Cliente clienteDeletado, String path) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        File antigo = new File(path+"clientes.csv");
        File novo = new File (path+"temp.csv");
        
        try {
            br = new BufferedReader(new FileReader(antigo));
            bw = new BufferedWriter(new FileWriter(novo, true));
            PrintWriter pw= new PrintWriter(bw);
            String linha = "";
            
            while ((linha = br.readLine()) != null) {
    
                String[] cliente = linha.split(",");
                if (!cliente[1].equals(clienteDeletado.getCPF())){
                    pw.println(linha);
                }
            }
            pw.flush();  
            pw.close();
            br.close();
            antigo.delete();
            
            File aux = new File (path+"clientes.csv");
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
     public static void removerFuncionario(Funcionario funcionarioDeletado, String path) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        File antigo = new File(path+"funcionarios.csv");
        File novo = new File (path+"temp.csv");
        
        try {
            br = new BufferedReader(new FileReader(antigo));
            bw = new BufferedWriter(new FileWriter(novo, true));
            PrintWriter pw= new PrintWriter(bw);
            String linha = "";
            
            while ((linha = br.readLine()) != null) {
    
                String[] funcionario = linha.split(",");
                if (!funcionario[1].equals(funcionarioDeletado.getCPF())){
                    pw.println(linha);
                }
            }
            pw.flush();  
            pw.close();
            br.close();
            antigo.delete();
            
            File aux = new File (path+"funcionarios.csv");
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