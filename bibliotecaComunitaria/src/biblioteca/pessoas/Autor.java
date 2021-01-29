package biblioteca.pessoas;

import java.util.ArrayList;
import biblioteca.livros.Livro;
import biblioteca.biblioteca.*;
import biblioteca.livros.*;
import biblioteca.pessoas.*;
import java.io.*;


/* @author victoria */

public class Autor extends Pessoa{
    protected String pais;
    protected ArrayList<Livro> livrosAutor = new ArrayList<>();

    public Autor() {    
    }

    public Autor(String nome, String pais) {
        super.cadastro(nome);
        this.pais = pais;
    }

    public ArrayList<Livro> getLivrosAutor() {
        return livrosAutor;
    }

    public void setLivrosAutor(ArrayList<Livro> livrosAutor) {
        this.livrosAutor = livrosAutor;
    }
    
    
    public void addLivro(Livro livro){
        this.livrosAutor.add(livro);
    }
    
    public Autor (String nome){
        super.cadastro(nome);
    }
    
    public void cadastro(String nome, String pais){
        super.cadastro(nome);
        this.pais = pais;
    
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
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

    public static ArrayList<Autor> leitorAutores(String path) {
       BufferedReader br = null;
       String linha = "";
       ArrayList<Autor> Autores = new ArrayList<>();
       try {
           br = new BufferedReader(new FileReader(path+"autores.csv"));
           br.readLine();

           while ((linha = br.readLine()) != null) {
               String[] autor = linha.split(",");
               Autor novo = new Autor();
               novo.cadastro(autor[0],autor[1]);
               Autores.add(novo);

           }
           return Autores;

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
