package biblioteca.arquivo;

import biblioteca.livros.Estante;
import biblioteca.livros.Livro;
import biblioteca.pessoas.Autor;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
    public static void escreveLivro(Livro livro) {
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
}
