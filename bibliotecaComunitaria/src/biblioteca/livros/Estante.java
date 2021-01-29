package biblioteca.livros;

import java.util.ArrayList;
import biblioteca.biblioteca.*;
import biblioteca.livros.*;
import biblioteca.pessoas.*;
import java.io.*;


/* @author Luam */
public class Estante {
    protected ArrayList<Livro> livros = new ArrayList();
    protected int idEstante;
    protected int capacidade;
    protected String genero;

    public Estante(int id, String genero) {
        this.idEstante = id;
        this.capacidade = 50;
        this.genero = genero;
    }

    public void imprimirEstante(){
        System.out.printf("\nEstante %d - %s - %d Livros\n", this.idEstante, this.genero, this.livros.size() );
        for (Livro livro:livros){
            livro.imprimirLivro();
        }
    }

    public void addLivroNaEstante(Livro livro) {
        if(livros.size()<this.capacidade){
            this.livros.add(livro);
        }
        
    }

    void buscarLivroNaEstante(String nomeLivro) {
        for (Livro livro:livros){
            if (livro.titulo.equals(nomeLivro)){
                livro.imprimirLivro();
            }
        }   
    }
    
    Livro buscarLivroNaEstanteTitulo(String titulo) {
        for (Livro livro:livros){
            if (livro.getTitulo().equals(titulo)){
                return livro;
            }
        }   
        return null;
    }

    public ArrayList<Livro> getLivros() {
        return livros;
    }

    public void setLivros(ArrayList<Livro> livros) {
        this.livros = livros;
    }

    public int getIdEstante() {
        return idEstante;
    }

    public void setIdEstante(int idEstante) {
        this.idEstante = idEstante;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
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

    public static Acervo leitorEstantes(String path) {
       BufferedReader br = null;
       String linha = "";
       Acervo acervo = new Acervo();
       try {
           br = new BufferedReader(new FileReader(path+"estantes.csv"));
           br.readLine();

           while ((linha = br.readLine()) != null) {
               String[] estante = linha.split(",");
               Estante nova = new Estante(Integer.parseInt(estante[0]),estante[1]);
               acervo.addEstante(nova);
           }
           return acervo;

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
}
