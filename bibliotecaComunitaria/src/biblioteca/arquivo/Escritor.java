package biblioteca.arquivo;

import biblioteca.biblioteca.*;
import biblioteca.livros.*;
import biblioteca.pessoas.*;
import java.io.*;

/* @author Luam */

public class Escritor {
    public static void escreverUnidade(Unidade unidade) {
        BufferedWriter bw = null;
        BufferedReader br = null;
        int contador = 0;
        try {
            contador = Integer.parseInt(br.readLine());
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
        
        String linha = String.valueOf(contador)+ "," +unidade.getNome()  + "," +unidade.getEnd().getRua()
                + "," + unidade.getEnd().getBairro()+ "," +unidade.getEnd().getCidade() 
                + "," + unidade.getEnd().getEstado();
        try {
            bw = new BufferedWriter(new FileWriter("src\\unidades\\unidades.csv", true));
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
        String linha = cliente.getNome() + "," + cliente.getNascimento() + "," + cliente.getTelefone() + "," + cliente.getEnd().getRua() + "," + cliente.getEnd().getBairro() + "," + cliente.getEnd().getCep() + "," + cliente.getEnd().getCidade() + "," + cliente.getEnd().getEstado();
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
