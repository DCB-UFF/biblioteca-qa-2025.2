package biblioteca.arquivo;

import biblioteca.biblioteca.*;
import biblioteca.livros.*;
import biblioteca.pessoas.*;
import java.io.*;

/* @author Luam */

public class Escritor {
    public static void escreverUnidade(Sistema sistema,Unidade unidade) {
        BufferedWriter bw = null;
        
        String linha = String.valueOf(sistema.addContadorUnidades())+ "," +unidade.getNome()  + "," +unidade.getEnd().getRua()
                + ","  + unidade.getEnd().getBairro()+ "," + unidade.getEnd().getCep()+ "," + unidade.getEnd().getCidade() 
               + "," + unidade.getEnd().getEstado();
        
                
        try {
            bw = new BufferedWriter(new FileWriter("src\\unidades\\unidades.csv", true));
            PrintWriter pw= new PrintWriter(bw);
            pw.println(linha);
            unidade.setPath(String.valueOf(sistema.getUnidades()));
            
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
    
    public static void escreverAutor(Autor autor, String path) {
        BufferedWriter bw = null;
        String linha = autor.getNome()+","+autor.getPais();
        try {
            bw = new BufferedWriter(new FileWriter(path+"autores.csv", true));
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
    public static void escreveEstante(Estante estante, String path) {
        BufferedWriter bw = null;
        String linha = estante.getIdEstante()+","+estante.getGenero();
        try {
            bw = new BufferedWriter(new FileWriter(path+"estantes.csv", true));
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
    public static void escreverLivro(Livro livro, String path) {
        BufferedWriter bw = null;
        String linha = livro.getAutor().getNome() + "," + livro.getTitulo() + "," + livro.getNumPaginas() + "," +
                livro.getISBN() + "," + livro.getGenero()  + "," + livro.getEditora() + "," + 
                livro.getEstaEmprestado() + "," + livro.getAutor().getPais();
        try {
            bw = new BufferedWriter(new FileWriter(path+"livros.csv", true));
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
    
    /* @author victoria */
    
    public static void escreverCliente(Cliente cliente, String path) {
        BufferedWriter bw = null;
        String linha = cliente.getNome() + "," + cliente.getCPF() + "," + cliente.getNascimento() 
                + "," + cliente.getTelefone() + "," + cliente.getEnd().getRua() 
                + "," + cliente.getEnd().getBairro() + "," + cliente.getEnd().getCep() 
                + "," + cliente.getEnd().getCidade() + "," + cliente.getEnd().getEstado();
        try { 
            bw = new BufferedWriter(new FileWriter(path+"clientes.csv", true));
            PrintWriter pw = new PrintWriter(bw);
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
    
    public static void escreverFuncionario(Funcionario funcionario, String path) {
        BufferedWriter bw = null;
        String linha = funcionario.getNome() + "," + funcionario.getNascimento() + "," + funcionario.getTelefone() + ","+ funcionario.getSalario() + "," + funcionario.getCargo() + "," + funcionario.getEnd().getRua() + "," + funcionario.getEnd().getBairro() + "," + funcionario.getEnd().getCep() + "," + funcionario.getEnd().getCidade() + "," + funcionario.getEnd().getEstado();
        try {
            bw = new BufferedWriter(new FileWriter(path+"funcionarios.csv", true));
            PrintWriter pw = new PrintWriter(bw);
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
    
    
}
