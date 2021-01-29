package biblioteca.livros;

import biblioteca.arquivo.Editor;
import biblioteca.biblioteca.Unidade;
import biblioteca.pessoas.Autor;
import biblioteca.biblioteca.*;
import biblioteca.livros.*;
import biblioteca.pessoas.*;
import java.io.*;
import java.util.ArrayList;


public class Livro {
   protected String titulo;
   protected Autor autor;
   protected int numPaginas;
   protected String ISBN;
   protected String genero;
   protected String editora;
   protected boolean estaEmprestado;
   
    public Livro( Autor autor, String titulo, int numPaginas, String ISBN,
            String genero,  String editora, Boolean estaEmprestado) {
        this.autor = autor;
        this.titulo = titulo;
        this.ISBN = ISBN;
        this.numPaginas = numPaginas;
        this.genero = genero;
        this.editora = editora;
        this.estaEmprestado = estaEmprestado;

    }
    
    public void imprimirLivro(){
        if (this.estaEmprestado){
            System.out.printf("\nCód: %s [Emprestado]| %s \n%s - %s  \n",
                    this.ISBN, this.titulo, this.autor.getNome(), this.editora);    
        }
        else{
            System.out.printf("\nCód: %s | %s \n%s - %s  \n",
            this.ISBN, this.titulo, this.autor.getNome(), this.editora);
        }       
    }

    public void emprestar(Unidade unidade,Acervo acervo){
        this.estaEmprestado = true;
        Emprestimo.modificarEmprestimo(acervo, this, unidade.getPath(), "true");
        System.out.println(this.titulo);
    }
    
    public void devolver(Unidade unidade,Acervo acervo){
       this.estaEmprestado = false;
       Emprestimo.modificarEmprestimo(acervo, this, unidade.getPath(), "false");
       System.out.println(this.titulo);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public int getNumPaginas() {
        return numPaginas;
    }

    public void setNumPaginas(int numPaginas) {
        this.numPaginas = numPaginas;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public boolean isEstaEmprestado() {
        return estaEmprestado;
    }

    public void setEstaEmprestado(boolean estaEmprestado) {
        this.estaEmprestado = estaEmprestado;
    }
    
    public static void escreverLivro(Livro livro, String path) {
        BufferedWriter bw = null;
        String linha = livro.getAutor().getNome() + "," + livro.getTitulo() + "," + livro.getNumPaginas() + "," +
                livro.getISBN() + "," + livro.getGenero()  + "," + livro.getEditora() + "," + 
                livro.isEstaEmprestado()+ "," + livro.getAutor().getPais();
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

    public static void leitorLivros(Acervo acervo, String path) {
       BufferedReader br = null;
       String linha = "";

       try {
           br = new BufferedReader(new FileReader(path+"livros.csv"));
           br.readLine();

           while ((linha = br.readLine()) != null) {

               String[] livro = linha.split(",");
               Autor autor = new Autor(livro[0],livro[7]);
               Livro novo = new Livro(autor,livro[1], 
                   Integer.parseInt(livro[2]), livro[3], livro[4],livro[5],Boolean.parseBoolean(livro[6]));
               acervo.addLivro(novo);

           }

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

}
