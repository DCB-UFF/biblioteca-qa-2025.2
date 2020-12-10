package biblioteca.livros;

import java.util.ArrayList;

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
        //Adicionar exceção
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
        
        //Exceção aqui dps
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
    
   
}
