package biblioteca.arquivo;

import biblioteca.livros.Estante;
import biblioteca.livros.Livro;
import biblioteca.pessoas.Autor;
import biblioteca.pessoas.Cliente;
import biblioteca.pessoas.Funcionario;
import java.io.*;
import java.util.ArrayList;

/* @author Luam */

public class Escritor {
    public static void escreverAutor(Autor autor) {
        BufferedWriter bw = null;
        String linha = autor.getNome()+","+autor.getPais();
        try {
            bw = new BufferedWriter(new FileWriter("autores.csv", true));
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
    public static void escreveEstante(Estante estante) {
        BufferedWriter bw = null;
        String linha = estante.getIdEstante()+","+estante.getGenero();
        try {
            bw = new BufferedWriter(new FileWriter("estantes.csv", true));
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
    public static void escreverLivro(Livro livro) {
        BufferedWriter bw = null;
        String linha = livro.getAutor().getNome() + "," + livro.getTitulo() + "," + livro.getNumPaginas() + "," +
                livro.getISBN() + "," + livro.getGenero()  + "," + livro.getEditora() + "," + 
                livro.getEstaEmprestado() + "," + livro.getAutor().getPais();
        try {
            bw = new BufferedWriter(new FileWriter("livros.csv", true));
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
    
    public static void escreverCliente(Cliente cliente) {
        BufferedWriter bw = null;
        String linha = cliente.getNome() + "," + cliente.getNascimento() + "," + cliente.getTelefone() + "," + cliente.getEnd().getRua() + "," + cliente.getEnd().getBairro() + "," + cliente.getEnd().getCep() + "," + cliente.getEnd().getCidade() + "," + cliente.getEnd().getEstado();
        try {
            bw = new BufferedWriter(new FileWriter("clientes.csv", true));
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
    
    public static void escreverFuncionario(Funcionario funcionario) {
        BufferedWriter bw = null;
        String linha = funcionario.getNome() + "," + funcionario.getNascimento() + "," + funcionario.getTelefone() + ","+ funcionario.getSalario() + "," + funcionario.getCargo() + "," + funcionario.getEnd().getRua() + "," + funcionario.getEnd().getBairro() + "," + funcionario.getEnd().getCep() + "," + funcionario.getEnd().getCidade() + "," + funcionario.getEnd().getEstado();
        try {
            bw = new BufferedWriter(new FileWriter("funcionarios.csv", true));
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
